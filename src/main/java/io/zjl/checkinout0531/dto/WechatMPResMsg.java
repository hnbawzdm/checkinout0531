package io.zjl.checkinout0531.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlCData;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import io.zjl.checkinout0531.constant.WechatConstant;

import java.util.Date;

/**
 * @Author:meng
 * @Date:2019/5/241905
 * @description
 */

/**
 * 该类返回xml 类型的数据
 */
@JacksonXmlRootElement(localName = "xml")
public class WechatMPResMsg {

    protected String ToUserName;
    protected String FromUserName;
    protected String MsgType;
    protected Long CreateTime;

    public WechatMPResMsg(String toUserName, String MsgType) {
        ToUserName = toUserName;
        this.MsgType = MsgType;
        CreateTime= new Date().getTime();
    }

    @JacksonXmlProperty(localName = "ToUserName")
    @JacksonXmlCData
    public String getToUserName() {
        return ToUserName;
    }

    @JacksonXmlProperty(localName = "FromUserName")
    @JacksonXmlCData
    public String getFromUserName() {
        return WechatConstant.MPID;
    }

    @JacksonXmlProperty(localName = "MsgType")
    @JacksonXmlCData
    public String getMsgType() {
        return MsgType;
    }

    @JacksonXmlProperty(localName = "CreateTime")
    @JacksonXmlCData
    public Long getCreateTime() {
        return CreateTime;
    }
}
