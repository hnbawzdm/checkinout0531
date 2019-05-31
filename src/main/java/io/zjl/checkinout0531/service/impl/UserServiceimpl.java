package io.zjl.checkinout0531.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.grum.geocalc.Coordinate;
import com.grum.geocalc.EarthCalc;
import com.grum.geocalc.Point;
import io.zjl.checkinout0531.api.WechatApi;
import io.zjl.checkinout0531.component.UserPosition;
import io.zjl.checkinout0531.component.WechatMPVariable;
import io.zjl.checkinout0531.constant.ErrMsgConstant;
import io.zjl.checkinout0531.constant.WechatConstant;
import io.zjl.checkinout0531.dao.CheckRecordMapper;
import io.zjl.checkinout0531.dao.UserDetailMapper;
import io.zjl.checkinout0531.dao.UserMapper;
import io.zjl.checkinout0531.enumeration.CheckType;
import io.zjl.checkinout0531.enumeration.UserStatus;
import io.zjl.checkinout0531.exception.WechatClientException;
import io.zjl.checkinout0531.po.CheckRecord;
import io.zjl.checkinout0531.po.User;
import io.zjl.checkinout0531.po.UserDetail;
import io.zjl.checkinout0531.service.UserService;
import io.zjl.checkinout0531.vo.Position;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * @Author:meng
 * @Date:2019/5/261905
 * @description
 */
@Service
public class UserServiceimpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CheckRecordMapper checkRecordMapper;

    @Autowired
    private UserDetailMapper userDetailMapper;

    @Autowired
    private WechatApi wechatApi;

    @Autowired
    private WechatMPVariable wechatMPVariable;

    @Autowired
    private UserPosition userPosition;

    @Value("${check.latitude}")
    private Double checkLatitude;

    @Value("${check.longitude}")
    private Double checkLongitude;

    @Value("${check.distance}")
    private Double checkDistance;

    @Override
    @Transactional
    public void checkIn(String openID) throws WechatClientException {
        checkPosotion(openID);
        //获取user
        User user = userMapper.selectByPrimaryKey(openID);
        if (user==null){
            throw new WechatClientException(openID, ErrMsgConstant.USER_NOT_EXIST,ErrMsgConstant.USER_NOT_EXIST_TEXT);
        }
        //获取user的状态  是否在上班中
        Byte status=user.getStatus();
        //如果不在上班  抛出异常
        if (status== UserStatus.OnWorking.ordinal()){
            throw new WechatClientException(openID,ErrMsgConstant.ALREADY_CHECK_IN,ErrMsgConstant.ALREADY_CHECK_IN_TEXT);
        }
        //添加记录
        CheckRecord checkRecord = new CheckRecord();
        checkRecord.setOpenid(openID);
        checkRecord.setType((byte) CheckType.Check_In.ordinal());
        checkRecord.setTime(new Date());
        checkRecordMapper.insert(checkRecord);
        //更新当前的状态
        user.setStatus((byte) UserStatus.OnWorking.ordinal());
        userMapper.updateByPrimaryKey(user);
    }

    @Override
    @Transactional
    public void create(User user, UserDetail userDetail) {
        userMapper.insert(user);
        userDetailMapper.insert(userDetail);
    }

    @Override
    @Transactional
    public User getUserFromWechatMP(String openid) throws WechatClientException {
        JSONObject userInfo = wechatApi.getUserInfo(wechatMPVariable.getAccessToken(),openid,WechatConstant.ZH_CN_LANG);
        openid = userInfo.getString("openid");
        if (openid==null){
            throw new WechatClientException(openid,ErrMsgConstant.CANNOT_GET_USER_FROM_WECHATMP,ErrMsgConstant.CANNOT_GET_USER_FROM_WECHATMP_TEXT);
        }
        User user = new User();
        user.setOpenid(openid);
        user.setNickname(userInfo.getString("nickname"));
        user.setGender(userInfo.getByte("sex"));
        user.setAvatarUrl(userInfo.getString("headimgurl"));
        user.setStatus(((byte) UserStatus.OffWorking.ordinal()));
        return user;
    }

    @Override
    @Transactional
    public void delete(String openid) {
        checkRecordMapper.deleteByOpenid(openid);
        userDetailMapper.deleteByPrimaryKey(openid);
        userMapper.deleteByPrimaryKey(openid);
    }

    /**
     * 保存 位置信息
     * @param openID
     * @param position
     */
    @Override
    public void savePostion(@NotBlank String openID, Position position) {
        // todo  redis
        userPosition.put(openID,position);
    }

    @Override
    public Position loadPosition(String openID) {
        return userPosition.get(openID);
    }

    @Override
    public void checkPosotion(String openID) throws WechatClientException {
        Position position = loadPosition(openID);

        if (position==null){
            throw new WechatClientException(openID,ErrMsgConstant.CANNOT_GET_POSITION,ErrMsgConstant.CANNOT_GET_POSITION_TEXT);
        }

        Coordinate lat = Coordinate.fromDegrees(checkLatitude);
        Coordinate lng = Coordinate.fromDegrees(checkLongitude);
        Point point = Point.at(lat, lng);

        lat = Coordinate.fromDegrees(position.getLatitude());
        lng = Coordinate.fromDegrees(position.getLongitude());
        Point userPosition = Point.at(lat, lng);

        double distance = EarthCalc.harvesineDistance(point, userPosition); //in meters
        if (distance > checkDistance) {
            throw new WechatClientException(openID,ErrMsgConstant.EXCEED_DISTANCE,ErrMsgConstant.CANNOT_GET_POSITION_TEXT);
        }
    }

    @Override
    public void checkOut(String openID) throws WechatClientException {
        checkPosotion(openID);
        //获取user
        User user = userMapper.selectByPrimaryKey(openID);
        if (user==null){
            throw new WechatClientException(openID, ErrMsgConstant.USER_NOT_EXIST,ErrMsgConstant.USER_NOT_EXIST_TEXT);
        }
        //获取user的状态  是否在上班中
        Byte status=user.getStatus();
        //如果不在上班  抛出异常
        if (status== UserStatus.OffWorking.ordinal()){
            throw new WechatClientException(openID,ErrMsgConstant.ALREADY_CHECK_OUT,ErrMsgConstant.ALREADY_CHECK_OUT_TEXT);
        }
        //添加记录
        CheckRecord checkRecord = new CheckRecord();
        checkRecord.setOpenid(openID);
        checkRecord.setType((byte) CheckType.Check_Out.ordinal());
        checkRecord.setTime(new Date());
        checkRecordMapper.insert(checkRecord);
        //更新当前的状态
        user.setStatus((byte) UserStatus.OffWorking.ordinal());
        userMapper.updateByPrimaryKey(user);
    }

}
