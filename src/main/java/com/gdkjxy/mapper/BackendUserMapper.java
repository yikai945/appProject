package com.gdkjxy.mapper;

import com.gdkjxy.pojo.BackendUser;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.FetchType;

public interface BackendUserMapper {

    /**
     * 根据账号密码查找devUser
     * @param backendUser
     * @return
     */
    @Select("select * from backend_user where userCode = #{userCode} and userPassword = #{userPassword}")
    BackendUser findByNameAndPwd(BackendUser backendUser);

    int deleteByPrimaryKey(Long id);

    int insert(BackendUser record);

    int insertSelective(BackendUser record);

    BackendUser selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BackendUser record);

    int updateByPrimaryKey(BackendUser record);
}