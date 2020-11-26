package com.gdkjxy.mapper;

import com.gdkjxy.pojo.DevUser;
import org.apache.ibatis.annotations.Select;

public interface DevUserMapper {

    /**
     * 根据账号密码查找devUser
     * @param devUser
     * @return
     */
    @Select("select * from dev_user where devCode = #{devCode} and devPassword = #{devPassword}")
    DevUser findByNameAndPwd(DevUser devUser);

    int deleteByPrimaryKey(Long id);

    int insert(DevUser record);

    int insertSelective(DevUser record);

    DevUser selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DevUser record);

    int updateByPrimaryKey(DevUser record);
}