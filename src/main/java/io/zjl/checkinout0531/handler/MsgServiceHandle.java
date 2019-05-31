package io.zjl.checkinout0531.handler;

import io.zjl.checkinout0531.constant.TestMsgConstant;
import io.zjl.checkinout0531.dto.TextMsg;
import io.zjl.checkinout0531.dto.WechatMPReqMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotBlank;

/**
 * @Author:meng
 * @Date:2019/5/301855
 * @description
 */
@Service
public class MsgServiceHandle {

    private Logger logger= LoggerFactory.getLogger(this.getClass());

    public TextMsg handler(WechatMPReqMsg reqMsg){
        @NotBlank String contant = reqMsg.getContant();
        @NotBlank String openID = reqMsg.getFromUserName();
        switch (contant){
            case TestMsgConstant.ONE:
                TextMsg textMsg = new TextMsg(openID, "就是个1");
                return textMsg;
            case TestMsgConstant.TWO:
                return new TextMsg(openID,"2也没东西");
            case TestMsgConstant.THREE:
                return new TextMsg(openID,"3,EMMMMM！");
            default:
                return new TextMsg(openID,"彩蛋吗？不是懒得写了");
        }
    }

}
