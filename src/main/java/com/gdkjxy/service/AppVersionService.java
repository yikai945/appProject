package com.gdkjxy.service;


import com.gdkjxy.pojo.AppVersion;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface AppVersionService {
    /**
     * 根据appId查找发布过的版本,并进行降序排序
     * @param id
     * @return
     */
    List<AppVersion> findByAppId(Long id);

    /**
     * 添加版本信息
     *
     * @param request
     * @param model
     * @param appVersion
     * @param a_downloadLink
     * @return
     */
    boolean addAppVersion(HttpServletRequest request, Model model, AppVersion appVersion, MultipartFile a_downloadLink);

    /**
     * 根据id查找出appVersion
     * @param id
     * @return
     */
    AppVersion findAppVersionById(Long id);

    /**
     * 修改appVersion
     * @param model
     * @param request
     * @param appVersion
     * @param apk
     * @return
     */
    boolean modifyAppVersion(Model model, HttpServletRequest request, AppVersion appVersion, MultipartFile apk);

    /**
     * 根据appid删除版本号
     * @param id
     * @return
     */
    boolean deleteByAppId(Long id);
}
