package io.zjl.checkinout0531.dto;

import javax.validation.constraints.NotBlank;

/**
 * @Author:meng
 * @Date:2019/5/261849
 * @description
 */
public class WechatMPCommonReqMsg extends WechatMPEventReqMsg {

    @NotBlank
    private Long getMsgId(){
        return  this.getLong("MsgId");
    }
}
