package com.gdkjxy.service.impl;

import com.gdkjxy.mapper.AppInfoMapper;
import com.gdkjxy.mapper.AppVersionMapper;
import com.gdkjxy.pojo.AppInfo;
import com.gdkjxy.pojo.AppVersion;
import com.gdkjxy.pojo.DevUser;
import com.gdkjxy.service.AppVersionService;
import com.gdkjxy.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileReader;
import java.util.Date;
import java.util.List;

/**
 * @Description TODO
 * @Author wuyikai
 * @Date 2020/11/29
 * @Version 1.0
 */
@Service
public class AppVersionServiceImpl implements AppVersionService {

    @Autowired
    AppVersionMapper appVersionMapper;

    @Autowired
    AppInfoMapper appInfoMapper;

    @Override
    public List<AppVersion> findByAppId(Long id) {
        return appVersionMapper.findByAppId(id);
    }

    @Override
    @Transactional
    public boolean addAppVersion(HttpServletRequest request, Model model, AppVersion appVersion, MultipartFile file) {
        HttpSession session = request.getSession();
        if (file.getSize() == 0) {
            model.addAttribute("fileUploadError", "文件不能为空");
            return false;
        }
        //获取源名字
        String oldName = file.getOriginalFilename();
        System.out.println(oldName);
        //获取后缀
        int lastFlag = oldName.lastIndexOf(".");
        String flag = oldName.substring(lastFlag, oldName.length());
        //检查文件格式是否正确
        if (!flag.equals(".apk")) {
            model.addAttribute("fileUploadError", "上传文件格式错误");
            return false;
        }
        //创建新文件名
        AppInfo appInfo = (AppInfo) request.getSession().getAttribute("appVersionAdd");
        System.out.println("appInfo:" + appInfo);
        String newName = appInfo.getAPKName() + "-" + appVersion.getVersionNo() + flag;
        //获取存放路径
        String path = session.getServletContext().getRealPath("statics" + File.separator + "uploadfiles");
        String servletPath = File.separator + "statics" + File.separator + "uploadfiles";
        System.out.println("path:" + path);
        //存储apk文件
        FileUtil.saveFile(file, path, newName);
        //修改添加版本的用户
        DevUser user = (DevUser) session.getAttribute("devUserSession");
        appVersion.setCreatedBy(user.getId());
        appVersion.setCreationDate(new Date());
        appVersion.setApkLocPath(path + File.separator + newName);
        appVersion.setDownloadLink(servletPath + File.separator + newName);
        appVersion.setApkFileName(newName);
        appVersion.setAppId(appInfo.getId());
        if (appVersionMapper.addAppVersion(appVersion) > 0) {
            System.out.println(appVersion.getId());
            appInfoMapper.modifyAppVersionIdById(appInfo.getId(), appVersion.getId());
            return true;
        }
        return false;
    }

    @Override
    public AppVersion findAppVersionById(Long id) {
        return appVersionMapper.findById(id);
    }

    @Override
    public boolean modifyAppVersion(Model model, HttpServletRequest request, AppVersion appVersion, MultipartFile apk) {
        HttpSession session = request.getSession();
        //上传的修改apk为空则不用处理apk文件,直接修改
        if (apk.getSize() == 0) {
            //修改用户
            DevUser user = (DevUser) session.getAttribute("devUserSession");
            appVersion.setModifyBy(user.getId());
            appVersion.setModifyDate(new Date());
            return appVersionMapper.modifyAppVersion(appVersion) > 0;
        }
        //获取源名字
        String oldName = apk.getOriginalFilename();
        System.out.println(oldName);
        //获取后缀
        int lastFlag = oldName.lastIndexOf(".");
        String flag = oldName.substring(lastFlag, oldName.length());
        //检查文件格式是否正确
        if (!flag.equals(".apk")) {
            model.addAttribute("fileUploadError", "上传文件格式错误");
            return false;
        }
        //创建新文件名
        AppInfo appInfo = appInfoMapper.findById(appVersion.getAppId());
        System.out.println("appInfo:" + appInfo);
        String newName = appInfo.getAPKName() + "-" + appVersion.getVersionNo() + flag;
        //获取存放路径
        String path = session.getServletContext().getRealPath("statics" + File.separator + "uploadfiles");
        String servletPath = File.separator + "statics" + File.separator + "uploadfiles";
        System.out.println("path:" + path);
        String apkLocPath = appVersion.getApkLocPath();
        DevUser user = (DevUser) session.getAttribute("devUserSession");
        appVersion.setModifyBy(user.getId());
        appVersion.setModifyDate(new Date());
        appVersion.setDownloadLink(servletPath + File.separator + newName);
        appVersion.setApkLocPath(path + File.separator + newName);
        int idMod = appVersionMapper.modifyAppVersion(appVersion);
        if (idMod > 0) {
            try {
                //重命名旧文件名,防止新文件冲突
                FileUtil.renameFile(apkLocPath, path + File.separator + "(waitDel)" + newName);
                //存储新文件
                FileUtil.saveFile(apk, path, newName);
                //删除旧文件
                FileUtil.delFile(path, "(waitDel)" + newName);
                return true;
            } catch (Exception e) {
                System.out.println("文件处理出错");
                e.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public boolean deleteByAppId(Long id) {
        List<AppVersion> appVersions = appVersionMapper.findByAppId(id);
        //如果没有version则直接返回true
        if (appVersions == null || appVersions.size() == 0) {
            return true;
        }
        for (AppVersion appVersion : appVersions) {
            FileUtil.delFile(appVersion.getApkLocPath());
        }
        return appVersionMapper.deleteByAppId(id) > 0;
    }
}
