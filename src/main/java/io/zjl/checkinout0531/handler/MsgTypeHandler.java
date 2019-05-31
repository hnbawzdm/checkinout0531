package io.zjl.checkinout0531.handler;

import com.alibaba.fastjson.JSONObject;
import io.zjl.checkinout0531.constant.WechatConstant;
import io.zjl.checkinout0531.constant.WechatMsgTypeConstant;
import io.zjl.checkinout0531.dto.WechatMPEventReqMsg;
import io.zjl.checkinout0531.dto.WechatMPReqMsg;
import io.zjl.checkinout0531.exception.WechatClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotBlank;

/**
 * @Author:meng
 * @Date:2019/5/272000
 * @description
 */
@Service
public class MsgTypeHandler {

    private Logger logger= LoggerFactory.getLogger(this.getClass());

    @Autowired
    private EventTypeClient eventTypeClient;

    @Autowired
    private MsgServiceHandle msgServiceHandle;

    public Object handle(WechatMPReqMsg reqMsg) throws WechatClientException {
        Object response = WechatConstant.SUCCESS_RESPONSE;

        @NotBlank String msgType = reqMsg.getMsgType();
        switch (msgType){
            case WechatMsgTypeConstant.TEXT:
                logger.info("receive {}", WechatMsgTypeConstant.TEXT);
                response=msgServiceHandle.handler(reqMsg);
            case WechatMsgTypeConstant.IMAGE:
                logger.info("receive {}", WechatMsgTypeConstant.IMAGE);
                break;
            case WechatMsgTypeConstant.VOICE:
                logger.info("receive {}", WechatMsgTypeConstant.VOICE);
                break;
            case WechatMsgTypeConstant.VIDEO:
                logger.info("receive {}", WechatMsgTypeConstant.VIDEO);
                break;
            case WechatMsgTypeConstant.SHORT_VIDEO:
                logger.info("receive {}", WechatMsgTypeConstant.SHORT_VIDEO);
                break;
            case WechatMsgTypeConstant.LOCATION:
                logger.info("receive {}", WechatMsgTypeConstant.LOCATION);
                break;
            case WechatMsgTypeConstant.LINK:
                logger.info("receive {}", WechatMsgTypeConstant.LINK);
                break;
            case WechatMsgTypeConstant.EVENT:
                logger.info("receive {}", WechatMsgTypeConstant.EVENT);
                String reqMsgStr = reqMsg.toJSONString();
                WechatMPEventReqMsg eventReqMsg = JSONObject.parseObject(reqMsgStr, WechatMPEventReqMsg.class);
                response=eventTypeClient.handle(eventReqMsg);
        }
        return response;
    }
}
