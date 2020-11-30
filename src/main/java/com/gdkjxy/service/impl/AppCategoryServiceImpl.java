package com.gdkjxy.service.impl;

import com.gdkjxy.mapper.AppCategoryMapper;
import com.gdkjxy.pojo.AppCategory;
import com.gdkjxy.service.AppCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description TODO
 * @Author wuyikai
 * @Date 2020/11/28
 * @Version 1.0
 */
@Service
public class AppCategoryServiceImpl implements AppCategoryService {

    @Autowired
    AppCategoryMapper appCategoryMapper;

    @Override
    public List<Object> getCategoryLevel() {
        List<Object> objects = new ArrayList<>();
        //查找一级菜单
        List<AppCategory> level1 = appCategoryMapper.findCategoryLevel1();
        objects.add(level1);
        //存放父级菜单ids
        List<Long> ids = new ArrayList<>();
        for (AppCategory cat : level1) {
            ids.add(cat.getId());
        }
        //根据一级菜单id集合查找二级菜单
        List<AppCategory> level2 = appCategoryMapper.findCategoryLevel(ids);
        objects.add(level2);
        //清空ids
        ids.clear();
        for (AppCategory cat : level2) {
            ids.add(cat.getId());
        }
        //根据二级菜单id集合查找三级菜单
        List<AppCategory> level3 = appCategoryMapper.findCategoryLevel(ids);
        objects.add(level3);
        return objects;
    }

    @Override
    public List<AppCategory> getCategoryLevel(Long id) {
        return appCategoryMapper.findCategoryLevelByPid(id);
    }

}
