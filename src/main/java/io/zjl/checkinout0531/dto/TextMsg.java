package io.zjl.checkinout0531.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlCData;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

/**
 * @Author:meng
 * @Date:2019/5/262007
 * @description
 */
public class TextMsg extends WechatMPResMsg{
    private String Content;

    public TextMsg(String toUserName, String content){
        super(toUserName,"text");
        Content=content;
    }
    

    @JacksonXmlProperty(localName = "Content")
    @JacksonXmlCData
    public String getContent() {
        return Content;
    }
}
