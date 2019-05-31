package io.zjl.checkinout0531.dto;

/**
 * @Author:meng
 * @Date:2019/5/241923
 * @description
 */
public class WechatMPEventReqMsg extends WechatMPReqMsg {

    public String getEvent(){return this.getString("Event");}
}
