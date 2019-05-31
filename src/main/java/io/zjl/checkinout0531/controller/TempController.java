package io.zjl.checkinout0531.controller;

import io.zjl.checkinout0531.api.WechatApi;
import io.zjl.checkinout0531.api.WechatMPSNSApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author:meng
 * @Date:2019/5/300851
 * @description
 */
@RestController
@RequestMapping("/temp")
public class TempController {

    @Autowired
    private WechatMPSNSApi wechatMPSNSApi;

    @Autowired
    private WechatApi wechatApi;
    @GetMapping("/token")
    public void test_token(){
        //wechatApi.getTicket("22_akhV5itjD1zwmXFuS0ODidmbv03qfFVx62D-UzwfsVpqz5RSFZgk7V9SdRw3GNXGucLMBCjk_fwTFjxVDXWbyrErq2S2RIQdBTSzKNG4sibGHBJcMLuL0cWrrG_3kqUP6wLipXczZ6_oCJ5iUXOeAFAYBS","jsapi");
        wechatApi.getUserInfo("aaa","vvvv","zh");
        //JSONObject userAccessToken = wechatMPSNSApi.getUserAccessToken(appId, appSecret, code, WechatConstant.AUTHORIZATION_CODE);
    }


}
