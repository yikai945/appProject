package com.gdkjxy.service.manager.impl;

import com.gdkjxy.mapper.BackendUserMapper;
import com.gdkjxy.mapper.DataDictionaryMapper;
import com.gdkjxy.pojo.BackendUser;
import com.gdkjxy.service.manager.BackendUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description TODO
 * @Author wuyikai
 * @Date 2020/11/26
 * @Version 1.0
 */
@Service
public class BackendUserServcieImpl implements BackendUserService {

    @Autowired
    BackendUserMapper backendUserMapper;
    @Autowired
    DataDictionaryMapper dataDictionaryMapper;

    @Override
    public int doLogin(BackendUser backendUser) {
        if (backendUser ==null|| backendUser.getUserCode()==null|| backendUser.getUserPassword()==null) {
            return 2;
        }
        BackendUser user = backendUserMapper.findByNameAndPwd(backendUser);
        if (user!=null){
            return 1;
        }
        return 0;
    }

    @Override
    public BackendUser getLoginBackendUser(BackendUser backendUser) {
        BackendUser user = backendUserMapper.findByNameAndPwd(backendUser);
        user.setUserTypeName(dataDictionaryMapper.findValueNameById(user.getUserType()));
        return user;
    }
}
