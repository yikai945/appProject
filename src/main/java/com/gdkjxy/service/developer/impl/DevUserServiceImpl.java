package com.gdkjxy.service.developer.impl;

import com.gdkjxy.mapper.DevUserMapper;
import com.gdkjxy.pojo.DevUser;
import com.gdkjxy.service.developer.DevUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description TODO
 * @Author wuyikai
 * @Date 2020/11/26
 * @Version 1.0
 */
@Service
public class DevUserServiceImpl implements DevUserService {

    @Autowired
    DevUserMapper devUserMapper;

    @Override
    public int doLogin(DevUser devUser) {
        if (devUser==null||devUser.getDevCode()==null||devUser.getDevPassword()==null) {
            return 2;
        }
        DevUser user = devUserMapper.findByNameAndPwd(devUser);
        if (user!=null){
            return 1;
        }
        return 0;
    }

    @Override
    public DevUser getLoginDevUser(DevUser devUser) {
        return  devUserMapper.findByNameAndPwd(devUser);
    }
}
