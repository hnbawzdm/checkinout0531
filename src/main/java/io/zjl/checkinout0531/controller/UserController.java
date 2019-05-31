package io.zjl.checkinout0531.controller;

import com.alibaba.fastjson.JSONObject;
import com.grum.geocalc.Coordinate;
import com.grum.geocalc.EarthCalc;
import com.grum.geocalc.Point;
import io.zjl.checkinout0531.api.WechatApi;
import io.zjl.checkinout0531.component.Ticket;
import io.zjl.checkinout0531.dao.CheckRecordMapper;
import io.zjl.checkinout0531.dao.UserMapper;
import io.zjl.checkinout0531.enumeration.CheckType;
import io.zjl.checkinout0531.enumeration.UserStatus;
import io.zjl.checkinout0531.exception.WebClientException;
import io.zjl.checkinout0531.po.CheckRecord;
import io.zjl.checkinout0531.po.User;
import io.zjl.checkinout0531.service.WechatMPService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * @Author:meng
 * @Date:2019/5/261835
 * @description
 */
@RestController
@CrossOrigin
@EnableAutoConfiguration
@RequestMapping("/user")
public class UserController {

    @Value("${check.latitude}")
    private Double checkLatitude;

    @Value("${check.longitude}")
    private Double checkLongitude;

    @Value("${check.distance}")
    private Double checkDistance;

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private Ticket ticket;

    @Autowired
    private CheckRecordMapper checkRecordMapper;

    @Autowired
    private WechatApi wechatApi;

    @Autowired
    private WechatMPService wechatMPService;

    private Logger logger= LoggerFactory.getLogger(this.getClass());

    @GetMapping("/canCheck")
    public void canCheck(@RequestParam Double latitude,
                         @RequestParam Double longitude) throws WebClientException {
        Coordinate lat = Coordinate.fromDegrees(checkLatitude);
        Coordinate lng = Coordinate.fromDegrees(checkLongitude);
        Point checkPosition = Point.at(lat, lng);

        lat = Coordinate.fromDegrees(latitude);
        lng = Coordinate.fromDegrees(longitude);
        Point userPosition = Point.at(lat, lng);

        double distance = EarthCalc.harvesineDistance(checkPosition, userPosition); //in meters
        if (distance > checkDistance) {
            throw new WebClientException("不在打卡范围");
        }
    }

    @GetMapping("/getCurrentStatus")
    public Byte getCurrentStatus(String openid){
        User user = userMapper.selectByPrimaryKey(openid);
        //Byte status = user.getStatus();
        return 1;
    }

    @PostMapping("/check")
    public Integer check(@RequestParam String openid,
                         @RequestParam Byte type) throws WebClientException {
        User user = userMapper.selectByPrimaryKey(openid);
        Byte status = user.getStatus();
        if (type == CheckType.Check_In.ordinal() && status == UserStatus.OnWorking.ordinal()){
            throw new WebClientException("已签到");
        }
        if (type == CheckType.Check_Out.ordinal() && status == UserStatus.OffWorking.ordinal()){
            throw new WebClientException("已签退");
        }
        CheckRecord checkRecord = new CheckRecord();
        checkRecord.setOpenid(openid);
        checkRecord.setType(type);
        checkRecord.setTime(new Date());
        checkRecordMapper.insert(checkRecord);

        if (type == CheckType.Check_In.ordinal()){
            user.setStatus((byte) UserStatus.OnWorking.ordinal());
        }
        if (type == CheckType.Check_Out.ordinal()){
             user.setStatus((byte) UserStatus.OffWorking.ordinal());
        }
        userMapper.updateByPrimaryKey(user);
        Integer id = checkRecord.getId();
        return id;
    }

    @GetMapping(value = "/getToken")
    public Object getToken(@RequestParam String code){
        JSONObject jsonObject = wechatMPService.getUserAccessToken(code);
        return jsonObject;
    }

    @GetMapping("/getTicket")
    public String getTicket(){
        return ticket.getTicket();
    }
}
