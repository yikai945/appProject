package com.gdkjxy.service;

import com.gdkjxy.pojo.AppCategory;

import java.util.List;

public interface AppCategoryService {
    /**
     * 获取菜单
     * @return 存储三个List<AppCategory>,分别为一,二,三级菜单
     */
    List<Object> getCategoryLevel();

    /**
     * 根据父id查找下一级菜单
     * @param id
     * @return
     */
    List<AppCategory> getCategoryLevel(Long id);
}
