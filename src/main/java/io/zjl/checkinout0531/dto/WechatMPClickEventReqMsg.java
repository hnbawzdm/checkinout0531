package io.zjl.checkinout0531.dto;

import javax.validation.constraints.NotBlank;

/**
 * @Author:meng
 * @Date:2019/5/261851
 * @description
 */
public class WechatMPClickEventReqMsg extends WechatMPEventReqMsg {

    @NotBlank
    public String getEventKey(){
        return this.getString("EventKey");
    }
}
