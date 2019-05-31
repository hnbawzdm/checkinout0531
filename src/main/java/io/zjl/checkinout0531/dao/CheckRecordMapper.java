package io.zjl.checkinout0531.dao;

import io.zjl.checkinout0531.po.CheckRecord;
import org.apache.ibatis.annotations.Param;

public interface CheckRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(CheckRecord record);

    int insertSelective(CheckRecord record);

    CheckRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CheckRecord record);

    int updateByPrimaryKey(CheckRecord record);

    void deleteByOpenid(@Param("openid") String openid);
}