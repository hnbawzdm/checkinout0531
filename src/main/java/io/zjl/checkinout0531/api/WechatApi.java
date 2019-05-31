package io.zjl.checkinout0531.api;

import com.alibaba.fastjson.JSONObject;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author:meng
 * @Date:2019/5/202104
 * @description
 */
@FeignClient(name ="wechatmp",url = "https://api.weixin.qq.com/cgi-bin")
public interface WechatApi {

    @GetMapping("/token")
    JSONObject getAccessToken(@RequestParam String grant_type,
                              @RequestParam String appid,
                              @RequestParam String secret);

    @GetMapping("/user/info")
    JSONObject getUserInfo(@RequestParam String access_token,
                           @RequestParam String openid,
                           @RequestParam String lang);

    @GetMapping("/ticket/getticket")
    JSONObject getTicket(@RequestParam String access_token,
                         @RequestParam String type);
}
