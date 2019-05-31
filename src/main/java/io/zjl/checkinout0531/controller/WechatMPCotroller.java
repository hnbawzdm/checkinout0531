package io.zjl.checkinout0531.controller;

import io.zjl.checkinout0531.component.WechatMPVariable;
import io.zjl.checkinout0531.dto.WechatMPReqMsg;
import io.zjl.checkinout0531.exception.WechatClientException;
import io.zjl.checkinout0531.handler.MsgTypeHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * @Author:meng
 * @Date:2019/5/271907
 * @description
 */
@RestController
@RequestMapping("/wechatmp")
@EnableAutoConfiguration
public class WechatMPCotroller {

    private Logger logger= LoggerFactory.getLogger(this.getClass());

    @Autowired
    private WechatMPVariable wechatMPVariable;

    @Autowired
    private MsgTypeHandler msgTypeHandler;



    @GetMapping("/receive")
    public String recevice(
                            @RequestParam String signature,//微信加密签名，signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数
                            @RequestParam Integer timestamp,//时间戳
                            @RequestParam String nonce,//	随机数
                            @RequestParam String echostr//	随机字符串
    ){

        logger.info("GET Request!!!");
        logger.info("signature: {}", signature);
        logger.info("timestamp: {}", timestamp);
        logger.info("nonce: {}", nonce);
        logger.info("echostr: {}", echostr);
        //todo 验证是否是来自微信的消息
        return echostr;
    }

    @PostMapping(value = "/receive",produces = {MediaType.APPLICATION_ATOM_XML_VALUE, MediaType.TEXT_PLAIN_VALUE})
    public Object receive(@RequestParam(required = false) String signature,
                          @RequestParam(required = false) Integer timestamp,
                          @RequestParam(required = false) String nonce,
                          @RequestBody WechatMPReqMsg reqMsg) throws WechatClientException {
        //todo 验证是否来自微信的消息

        // TODO  重放攻击

        // TODO 异步

        Object resMsg = msgTypeHandler.handle(reqMsg);
        return resMsg;
    }

}
