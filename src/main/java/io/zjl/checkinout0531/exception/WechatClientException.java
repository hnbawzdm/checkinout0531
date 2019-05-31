package io.zjl.checkinout0531.exception;

/**
 * @Author:meng
 * @Date:2019/5/271836
 * @description
 */
public class WechatClientException extends Exception {
    private String openID;

    private String errCode;

    public WechatClientException(String openID,String errCode,String errMsg) {
        super(errMsg);
        this.openID=openID;
        this.errCode=errCode;
    }

    public String getOpenID() {
        return openID;
    }

    public String getErrCode() {
        return errCode;
    }
}
