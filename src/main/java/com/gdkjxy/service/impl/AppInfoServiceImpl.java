package com.gdkjxy.service.impl;

import com.gdkjxy.mapper.AppInfoMapper;
import com.gdkjxy.pojo.AppInfo;
import com.gdkjxy.pojo.DevUser;
import com.gdkjxy.pojo.FuzzyQuery;
import com.gdkjxy.pojo.PageIndex;
import com.gdkjxy.service.AppInfoService;
import com.gdkjxy.util.FileUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description AppInfoService实现类
 * @Author wuyikai
 * @Date 2020/11/27
 * @Version 1.0
 */
@Service
public class AppInfoServiceImpl implements AppInfoService {
    @Autowired
    AppInfoMapper appInfoMapper;

    @Override
    public List<AppInfo> findAllInfoDetails(Model model, FuzzyQuery fuzzyQuery) {
        Map map = new HashMap();
        map.put("fuzzyQuery", fuzzyQuery);
        if (fuzzyQuery.getPageIndex() == 0) {
            fuzzyQuery.setPageIndex(1);
        }
        PageHelper.startPage(fuzzyQuery.getPageIndex(), 5);
        List<AppInfo> appInfos = appInfoMapper.findAppInfoDetails(map);
        PageInfo<AppInfo> page = new PageInfo<>(appInfos);
        PageIndex pg = new PageIndex(fuzzyQuery.getPageIndex(), (int) page.getTotal(), page.getPages());
        model.addAttribute("pages", pg);
        return appInfos;
    }

    @Override
    public boolean isApkNameExist(String apkName) {
        AppInfo apk = appInfoMapper.isApkNameExist(apkName);
        if (apk != null) {
            return true;
        }
        return false;
    }

    @Override
    public boolean addAppInfo(Model model, HttpServletRequest request, AppInfo appInfo, MultipartFile pic) {
        System.out.println(appInfo);
        //检查大小
        if(pic.getSize()>5000000){
            model.addAttribute("fileUploadError","上传文件太大");
            return false;
        }
        //获取存放路径
        HttpSession session = request.getSession();
        String path = session.getServletContext().getRealPath("statics" + File.separator + "uploadfiles");
        String servletPath = File.separator+"statics"+File.separator+"uploadfiles";
        System.out.println("path:" + path);
        //获取源名字
        String oldName = pic.getOriginalFilename();
        System.out.println(oldName);
        //获取后缀
        int lastFlag = oldName.lastIndexOf(".");
        String flag = oldName.substring(lastFlag, oldName.length());
        //检查文件格式是否正确
        if (!flag.equals(".jpg")&&!flag.equals(".png")&&!flag.equals(".jpeg")&&!flag.equals(".pneg")){
            model.addAttribute("fileUploadError","上传文件格式错误");
            return false;
        }
        //创建新文件名
        String newName = appInfo.getAPKName() + flag;
        try {
            //将文件保存至指定路径
            FileUtil.saveFile(pic, path, newName);
            System.out.println("存储成功");
            //添加appInfo
            DevUser user = (DevUser) session.getAttribute("devUserSession");
            Long id = user.getId();
            appInfo.setDevId(id);
            appInfo.setCreationDate(new Date());
            appInfo.setCreatedBy(id);
            appInfo.setLogoLocPath(path + File.separator + newName);
            appInfo.setLogoPicPath(servletPath+File.separator+newName);
            appInfoMapper.addAppInfo(appInfo);
            return true;
        } catch (Exception e) {
            model.addAttribute("fileUploadError","上传文件失败");
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public AppInfo findById(Long id) {
        return appInfoMapper.findById(id);
    }


    @Override
    public boolean modifyAppInfo(Model model, HttpServletRequest request, AppInfo appInfo, MultipartFile pic) {
        HttpSession session = request.getSession();
        //上传的修改logo为空则不用处理图片文件,直接修改
        if (pic.getSize()==0){
            //修改用户
            DevUser user = (DevUser) session.getAttribute("devUserSession");
            appInfo.setModifyBy(user.getId());
            appInfo.setModifyDate(new Date());
            appInfo.setVersionId(appInfoMapper.findById(appInfo.getId()).getVersionId());
            return appInfoMapper.modifyAppInfo(appInfo) > 0;
        }
        //检查上传文件大小
        if(pic.getSize()>5000000){
            model.addAttribute("fileUploadError","上传文件太大");
            return false;
        }
        //获取源名字
        String oldName = pic.getOriginalFilename();
        System.out.println(oldName);
        //获取后缀
        int lastFlag = oldName.lastIndexOf(".");
        String flag = oldName.substring(lastFlag, oldName.length());
        //检查文件格式是否正确
        if (!flag.equals(".jpg")&&!flag.equals(".png")&&!flag.equals(".jpeg")&&!flag.equals(".pneg")){
            model.addAttribute("fileUploadError","上传文件格式错误");
            return false;
        }
        //创建新文件名
        String newName = appInfo.getAPKName() + flag;
        //获取存放路径
        String path = session.getServletContext().getRealPath("statics" + File.separator + "uploadfiles");
        String servletPath = File.separator+"statics"+File.separator+"uploadfiles";
        System.out.println("path:" + path);
        //修改用户
        DevUser user = (DevUser) session.getAttribute("devUserSession");
        appInfo.setCreatedBy(user.getId());
        appInfo.setCreationDate(new Date());
        appInfo.setLogoLocPath(path+File.separator+newName);
        appInfo.setLogoPicPath(servletPath+File.separator+newName);
        appInfo.setDevId(user.getId());
        appInfo.setVersionId(appInfoMapper.findById(appInfo.getId()).getVersionId());
        //获取数据库appInfologo路径
        AppInfo appPath = appInfoMapper.findPathById(appInfo.getId());
        String localPath = appPath.getLogoLocPath();
        int isMod = appInfoMapper.modifyAppInfo(appInfo);
        if(isMod>0){
            try{
                //重命名旧文件名,防止新文件冲突
                FileUtil.renameFile(localPath,path+File.separator+"(waitDel)"+newName);
                //存储新文件
                FileUtil.saveFile(pic,path,newName);
                //删除旧文件
                FileUtil.delFile(path,"(waitDel)"+newName);
                return true;
            }catch (Exception e){
                System.out.println("文件处理出错");
                e.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public boolean deleteById(Long id) {
        AppInfo appInfo = appInfoMapper.findById(id);
        FileUtil.delFile(appInfo.getLogoLocPath());
        return appInfoMapper.deleteById(id)>0;
    }

    @Override
    public boolean modifyStatusById(Long id, Integer status) {
        return appInfoMapper.modifyStatusById(id,status)>0;
    }

    @Override
    public boolean modifyStatusOn_OffSale(Long id) {
        String status = appInfoMapper.findStatusById(id);
        System.out.println(status);
        if(status.equals("审核通过")){
            System.out.println("进来上架");
            return appInfoMapper.modifyStatusOnSale(id,"已上架")>0;
        }
        if(status.equals("已上架")){
            System.out.println("进来下架");
            return appInfoMapper.modifyStatusOnSale(id,"已下架")>0;
        }
        if(status.equals("已下架")){
            System.out.println("进来上架");
            return appInfoMapper.modifyStatusOnSale(id,"已上架")>0;
        }
        return false;
    }
}
