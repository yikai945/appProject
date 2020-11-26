package com.gdkjxy.service.manager;

import com.gdkjxy.pojo.BackendUser;

public interface BackendUserService {
    /**
     * 开发者登陆检查
     * @param backendUser
     * @return 0:账号密码错误,1:登陆成功,2:账号密码为空
     */
    int doLogin(BackendUser backendUser);

    /**
     * 根据账号密码查找开发者
     * @param backendUser
     * @return
     */
    BackendUser getLoginBackendUser(BackendUser backendUser);
}
