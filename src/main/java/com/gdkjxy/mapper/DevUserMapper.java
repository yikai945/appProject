package com.gdkjxy.mapper;

import com.gdkjxy.pojo.DevUser;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

public interface DevUserMapper {

    /**
     * 根据账号密码查找devUser
     * @param devUser
     * @return
     */
    @Select("select * from dev_user where devCode = #{devCode} and devPassword = #{devPassword}")
    DevUser findByNameAndPwd(DevUser devUser);

    /**
     * 根据id查找devName
     * @param id
     * @return
     */
    @Select("select devName from dev_user where id = #{id}")
    String findDevNameById(Long id);
}