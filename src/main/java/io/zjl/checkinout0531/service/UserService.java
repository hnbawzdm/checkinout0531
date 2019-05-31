package io.zjl.checkinout0531.service;

import io.zjl.checkinout0531.exception.WechatClientException;
import io.zjl.checkinout0531.po.User;
import io.zjl.checkinout0531.po.UserDetail;
import io.zjl.checkinout0531.vo.Position;

import javax.validation.constraints.NotBlank;

/**
 * @Author:meng
 * @Date:2019/5/261905
 * @description
 */
public interface UserService {
    void checkIn(String openID) throws WechatClientException;

    void create(User user, UserDetail userDetail);

    User getUserFromWechatMP(String openid) throws WechatClientException;

    void delete(String openid);

    void savePostion(@NotBlank String openID, Position position);

    Position loadPosition(String openID);

    void checkPosotion(String openID) throws WechatClientException;

    void checkOut(String openID) throws WechatClientException;
}
