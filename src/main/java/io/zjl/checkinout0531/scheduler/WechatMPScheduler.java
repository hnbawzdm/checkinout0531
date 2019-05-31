package io.zjl.checkinout0531.scheduler;

import com.alibaba.fastjson.JSONObject;
import io.zjl.checkinout0531.api.WechatApi;
import io.zjl.checkinout0531.component.Ticket;
import io.zjl.checkinout0531.component.WechatMPVariable;
import io.zjl.checkinout0531.service.WechatMPService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @Author:meng
 * @Date:2019/5/281049
 * @description
 */
@Component
public class WechatMPScheduler {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private WechatMPService wechatMPService;

    @Autowired
    private WechatApi wechatApi;

    @Autowired
    private Ticket ticket;
    @Autowired
    private WechatMPVariable wechatMPVariable;

    @Value("${wechatmp.refresh.accesstoken.enabled}")
    private boolean refreshAccessTokenEnabled;

    @Scheduled(fixedRate = 60*1000*60*2)
    public void refreshAccessToken(){

            logger.info("begin to refresh wechatmp access token");
            String accessToken = wechatMPService.getAccessToken();
            wechatMPVariable.setAccessToken(accessToken);
            JSONObject jsapi = wechatApi.getTicket(accessToken, "jsapi");
            ticket.setTicket(jsapi.getString("ticket"));
            logger.info("wechatmp access token has been refreshed: {}", accessToken);
    }
}
