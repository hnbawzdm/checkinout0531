package io.zjl.checkinout0531.service.impl;

import com.alibaba.fastjson.JSONObject;
import io.zjl.checkinout0531.api.WechatApi;
import io.zjl.checkinout0531.api.WechatMPSNSApi;
import io.zjl.checkinout0531.constant.WechatConstant;
import io.zjl.checkinout0531.service.WechatMPService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class WechatMPServiceImpl implements WechatMPService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${wechatmp.appId}")
    private String appId;

    @Value("${wechatmp.appSecret}")
    private String appSecret;

    @Autowired
    private WechatApi wechatMPApi;

    @Autowired
    private WechatMPSNSApi wechatMPSNSApi;

    @Override
    public String getAccessToken() {
        logger.info("ready to renew wechatmp access token");
        JSONObject jsonObject = wechatMPApi.getAccessToken("client_credential", appId, appSecret);
        String access_token = jsonObject.getString("access_token");
        return access_token;
    }

    @Override
    public JSONObject getUserAccessToken(String code) {
        String userAccessToken = wechatMPSNSApi.getUserAccessToken(appId, appSecret, code, WechatConstant.AUTHORIZATION_CODE);
        JSONObject jsonObject = JSONObject.parseObject(userAccessToken);
        return jsonObject;
    }
}
