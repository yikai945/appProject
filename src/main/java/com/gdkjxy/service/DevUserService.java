package com.gdkjxy.service;

import com.gdkjxy.pojo.DevUser;

public interface DevUserService {

    /**
     * 开发者登陆检查
     * @param devUser
     * @return 0:账号密码错误,1:登陆成功,2:账号密码为空
     */
    int doLogin(DevUser devUser);

    /**
     * 根据账号密码查找开发者
     * @param devUser
     * @return
     */
    DevUser getLoginDevUser(DevUser devUser);
}
