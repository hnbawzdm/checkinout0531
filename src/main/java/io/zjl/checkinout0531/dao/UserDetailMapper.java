package io.zjl.checkinout0531.dao;

import io.zjl.checkinout0531.po.UserDetail;

public interface UserDetailMapper {
    int deleteByPrimaryKey(String openid);

    int insert(UserDetail record);

    int insertSelective(UserDetail record);

    UserDetail selectByPrimaryKey(String openid);

    int updateByPrimaryKeySelective(UserDetail record);

    int updateByPrimaryKey(UserDetail record);
}