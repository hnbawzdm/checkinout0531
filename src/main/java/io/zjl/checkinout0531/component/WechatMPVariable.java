package io.zjl.checkinout0531.component;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Author:meng
 * @Date:2019/5/271917
 * @description
 */
@Component
public class WechatMPVariable {

    private String accessToken;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
