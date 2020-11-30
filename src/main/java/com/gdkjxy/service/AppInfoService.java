package com.gdkjxy.service;

import com.gdkjxy.pojo.AppInfo;
import com.gdkjxy.pojo.FuzzyQuery;
import org.apache.ibatis.annotations.Update;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface AppInfoService {
    /**
     * 查找所有AppInfoDetails
     * @return
     */
    List<AppInfo> findAllInfoDetails(Model model, FuzzyQuery pageIndex);

    /**
     * 检查ApkName是否已经存在
     * @param apkName
     * @return
     */
    boolean isApkNameExist(String apkName);

    /**
     * 插入appInfo信息
     *
     * @param model
     * @param appInfo
     * @param pic
     * @return
     */
    boolean addAppInfo(Model model,HttpServletRequest request, AppInfo appInfo, MultipartFile pic);

    /**
     * 根据id查找appinfo
     * @param id
     * @return
     */
    AppInfo findById(Long id);


    /**
     * 修改appinfo
     * @param model
     * @param request
     * @param appInfo
     * @param pic
     * @return
     */
    boolean modifyAppInfo(Model model, HttpServletRequest request, AppInfo appInfo, MultipartFile pic);

    /**
     * 根据id删除appInfo
     * @param id
     * @return
     */
    boolean deleteById(Long id);

    /**
     * 根据id修改状态
     * @param id
     * @param status
     * @return
     */
    boolean modifyStatusById(Long id,Integer status);

    /**
     * 上架app
     * @param id
     * @return
     */
    boolean modifyStatusOn_OffSale(Long id);
}
