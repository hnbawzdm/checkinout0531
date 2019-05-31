package io.zjl.checkinout0531.dto;

import com.alibaba.fastjson.JSONObject;

import javax.validation.constraints.NotBlank;

/**
 * @Author:meng
 * @Date:2019/5/241919
 * @description
 */

/**
 * 接收到的消息
 */
public class WechatMPReqMsg extends JSONObject {

    @NotBlank
    public String getFromUserName() {
        return this.getString("FromUserName");
    }

    @NotBlank
    public Integer getCreateTime() {
        return this.getInteger("CreateTime");
    }

    @NotBlank
    public String getMsgType() {
        return this.getString("MsgType");
    }

    @NotBlank
    public String getContant(){return this.getString("Content");}
}
