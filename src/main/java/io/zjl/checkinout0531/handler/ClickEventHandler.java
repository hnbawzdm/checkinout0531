package io.zjl.checkinout0531.handler;


import io.zjl.checkinout0531.constant.WechatEventKeyConstant;
import io.zjl.checkinout0531.dto.TextMsg;
import io.zjl.checkinout0531.dto.WechatMPClickEventReqMsg;
import io.zjl.checkinout0531.dto.WechatMPEventReqMsg;
import io.zjl.checkinout0531.dto.WechatMPResMsg;
import io.zjl.checkinout0531.exception.WechatClientException;
import io.zjl.checkinout0531.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotBlank;


/**
 * @Author:meng
 * @Date:2019/5/241857
 * @description
 */
@Service
public class ClickEventHandler {

    private Logger logger= LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService userService;

    public WechatMPResMsg handler(WechatMPClickEventReqMsg reqMsg
    ) throws WechatClientException {

        WechatMPResMsg resMsg=null;
        String eventKey=reqMsg.getEventKey();

        switch (eventKey){
            case WechatEventKeyConstant.CHECK_IN:
                logger.info(" receive {}",WechatEventKeyConstant.CHECK_IN);
                resMsg= handleCheckIn(reqMsg);
                break;
            case WechatEventKeyConstant.CHECK_OUT:
                logger.info(" receive {}",WechatEventKeyConstant.CHECK_IN);
                resMsg=handleCheckOut(reqMsg);
                break;
            case WechatEventKeyConstant.TextMsg:
                logger.info(" receive {}",WechatEventKeyConstant.TextMsg);
                resMsg=handleTextMsg(reqMsg);
                break;
            default:
                logger.info("it don't match any event key");
        }
        return resMsg;
    }

    private TextMsg handleTextMsg(WechatMPClickEventReqMsg reqMsg) {
        @NotBlank String openID = reqMsg.getFromUserName();
        TextMsg textMsg = new TextMsg(openID, "用你的小手输个数字试试");
        return textMsg;
    }

    /**
     * 签到
     * @param reqMsg
     * @return
     */
    private TextMsg handleCheckIn(WechatMPEventReqMsg reqMsg) throws WechatClientException {
        @NotBlank String openID = reqMsg.getFromUserName();
        userService.checkIn(openID);
        TextMsg textMsg = new TextMsg(openID, "ckeck in success");
        return textMsg;
    }

    // TODO 签退
    private TextMsg handleCheckOut(WechatMPEventReqMsg reqMsg) throws WechatClientException {
        @NotBlank String openID = reqMsg.getFromUserName();
        userService.checkOut(openID);
        TextMsg textMsg = new TextMsg(openID, "ckeck out success");
        return textMsg;
    }




}
