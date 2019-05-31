package io.zjl.checkinout0531.handler;

import com.alibaba.fastjson.JSON;
import io.zjl.checkinout0531.constant.WechatConstant;
import io.zjl.checkinout0531.constant.WechatEventConstant;
import io.zjl.checkinout0531.dto.TextMsg;
import io.zjl.checkinout0531.dto.WechatMPClickEventReqMsg;
import io.zjl.checkinout0531.dto.WechatMPEventReqMsg;
import io.zjl.checkinout0531.exception.WechatClientException;
import io.zjl.checkinout0531.po.User;
import io.zjl.checkinout0531.po.UserDetail;
import io.zjl.checkinout0531.service.UserService;
import io.zjl.checkinout0531.vo.Position;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotBlank;

/**
 * @Author:meng
 * @Date:2019/5/272022
 * @description
 */
@Service
public class EventTypeClient {

    private Logger logger= LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService userService;

    @Autowired
    private ClickEventHandler clickEventHandler;

    public Object handle(WechatMPEventReqMsg reqMsg) throws WechatClientException {
        Object resMsg = WechatConstant.SUCCESS_RESPONSE;

        String event = reqMsg.getEvent();

        switch (event) {
            case WechatEventConstant.SUBSCRIBE:
                logger.info("receive {}", WechatEventConstant.SUBSCRIBE);
                resMsg = handleSubscribe(reqMsg);
                break;
            case WechatEventConstant.UNSUBSCRIBE:
                logger.info("receive {}", WechatEventConstant.UNSUBSCRIBE);
                handleUnsubscribe(reqMsg);
                break;
            case WechatEventConstant.SCAN:
                logger.info("receive {}", WechatEventConstant.SCAN);
                break;
            case WechatEventConstant.LOCATION:
                logger.info("receive {}", WechatEventConstant.LOCATION);
                handleLocation(reqMsg);
                break;
            case WechatEventConstant.CLICK:
                logger.info("receive {}", WechatEventConstant.CLICK);
                String reqMsgJsonStr = reqMsg.toJSONString();
                WechatMPClickEventReqMsg clickEventReqMsg = JSON.parseObject(reqMsgJsonStr, WechatMPClickEventReqMsg.class);
                resMsg = clickEventHandler.handler(clickEventReqMsg);
                break;
            case WechatEventConstant.VIEW:
                logger.info("receive {}", WechatEventConstant.VIEW);
                break;
            default:
                logger.info("it doesn't match any event");
        }
        return resMsg;
    }
    //关注
    private TextMsg handleSubscribe(WechatMPEventReqMsg reqMsg) throws WechatClientException {
        @NotBlank String openid = reqMsg.getFromUserName();
        User user = userService.getUserFromWechatMP(openid);
        UserDetail userDetail = new UserDetail(openid);
        userService.create(user,userDetail);
        String text = String.format("你好，%s，欢迎订阅", user.getNickname());
        TextMsg textResMsg = new TextMsg(openid, text);
        return textResMsg;
    }

    // 取关
    public void handleUnsubscribe(WechatMPEventReqMsg reqMsg){
        @NotBlank String openid = reqMsg.getFromUserName();
        userService.delete(openid);
    }

    // todo 获取 位置信息
    public void handleLocation(WechatMPEventReqMsg reqMsg){
        @NotBlank String openID = reqMsg.getFromUserName();
        Double latitude = reqMsg.getDouble("Latitude");
        Double longitude = reqMsg.getDouble("Longitude");
        Position position = new Position(latitude, longitude);
        userService.savePostion(openID,position);
    }

}
