package com.gdkjxy.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gdkjxy.pojo.*;
import com.gdkjxy.service.AppCategoryService;
import com.gdkjxy.service.AppInfoService;
import com.gdkjxy.service.AppVersionService;
import com.gdkjxy.service.DataDictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description TODO
 * @Author wuyikai
 * @Date 2020/11/27
 * @Version 1.0
 */
@Controller
@RequestMapping("dev/flatform/app")
public class DevAppController {

    @Autowired
    AppInfoService appInfoService;

    @Autowired
    AppCategoryService appCategoryService;

    @Autowired
    DataDictionaryService dataDictionaryService;

    @Autowired
    AppVersionService appVersionService;


    /**
     * 动态分页获取appinfo列表
     *
     * @param query
     * @param model
     * @return
     */
    @RequestMapping("list")
    public String appList(FuzzyQuery query, Model model) {
        System.out.println(query);
        //分页条件动态获取信息
        List<AppInfo> appInfos = appInfoService.findAllInfoDetails(model, query);
        //获取菜单信息
        List<Object> level = appCategoryService.getCategoryLevel();
        //获取所属平台
        List<DataDictionary> flatform = dataDictionaryService.findAllAppFlatform();
        //获取app状态
        List<DataDictionary> status = dataDictionaryService.findAllAppStatus();
        model.addAttribute("appInfoList", appInfos);
        model.addAttribute("categoryLevel1List", level.get(0));
        model.addAttribute("categoryLevel2List", level.get(1));
        model.addAttribute("categoryLevel3List", level.get(2));
        model.addAttribute("flatFormList", flatform);
        model.addAttribute("statusList", status);
        //存储查询条件
        if (query.getQueryFlatformId() != null) {
            model.addAttribute("queryFlatformId", query.getQueryFlatformId());
        }
        if (query.getQuerySoftwareName() != null && !"".equals(query.getQuerySoftwareName())) {
            model.addAttribute("querySoftwareName", query.getQuerySoftwareName());
        }
        if (query.getQueryCategoryLevel1() != null) {
            model.addAttribute("queryCategoryLevel1", query.getQueryCategoryLevel1());
        }
        if (query.getQueryCategoryLevel2() != null) {
            model.addAttribute("queryCategoryLevel2", query.getQueryCategoryLevel2());
        }
        if (query.getQueryCategoryLevel3() != null) {
            model.addAttribute("queryCategoryLevel3", query.getQueryCategoryLevel3());
        }
        if (query.getQueryStatus() != null) {
            model.addAttribute("queryStatus", query.getQueryStatus());
        }
        return "developer/appinfolist";
    }

    /**
     * 根据id查找菜单,如果id为""则为一级菜单
     *
     * @param pid
     * @return
     */
    @RequestMapping("categorylevellist.json")
    @ResponseBody
    public List<AppCategory> categorylevellist(String pid) {
        List<AppCategory> cat = new ArrayList<>();
        List<Object> level = appCategoryService.getCategoryLevel();
        if ("".equals(pid)) {
            cat = (List<AppCategory>) level.get(0);
        } else if (pid != null) {
            cat = appCategoryService.getCategoryLevel(Long.parseLong(pid));
        }
        return cat;
    }

    /**
     * 跳转添加app页面
     *
     * @param model
     * @return
     */
    @RequestMapping("appinfoadd")
    public String appinfoAdd(Model model) {
        return "developer/appinfoadd";
    }

    /**
     * 获取所有平台类别并返回
     *
     * @param model
     * @param tcode
     * @return
     */
    @RequestMapping("datadictionarylist.json")
    @ResponseBody
    public List<DataDictionary> getFlatformList(Model model, String tcode) {
        //获取所属平台
        List<DataDictionary> flatform = dataDictionaryService.findAllAppFlatform();
        return flatform;
    }

    /**
     * 检查APKName是否已经被使用
     * @param APKName
     * @return
     * @throws JsonProcessingException
     */
    @RequestMapping("apkexist.json")
    @ResponseBody
    public String apkCheck(String APKName) throws JsonProcessingException {
        Map<String,String> map = new HashMap<>();
        if(APKName==null||"".equals(APKName)){
            map.put("APKName","empty");
        }else{
            if(appInfoService.isApkNameExist(APKName)){
                map.put("APKName","exist");
            }else {
                map.put("APKName","noexist");
            }
        }
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(map);
        System.out.println(json);
        return json;
    }

    /**
     * 添加appinfo保存
     * @param appInfo
     * @param pic
     * @param request
     * @return
     */
    @RequestMapping("appinfoaddsave")
    public String appInfoAddSave(AppInfo appInfo, @RequestParam("a_logoPicPath") MultipartFile pic, HttpServletRequest request,Model model){
        System.out.println("appInfo:"+appInfo);
        System.out.println("pic:"+pic);
        boolean flag = appInfoService.addAppInfo(model,request, appInfo, pic);
        if (flag){
            return "redirect:list";
        }
        return "developer/appinfoadd";
    }

    /**
     * appinfo查看詳情頁面
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("appview/{id}")
    public String appView(@PathVariable("id")Long id,Model model){
        System.out.println(id);
        //获取appinfo
        AppInfo appInfo = appInfoService.findById(id);
        model.addAttribute("appInfo",appInfo);
        //获取appversion
        List<AppVersion> appVersions = appVersionService.findByAppId(id);
        for (AppVersion app : appVersions) {
            app.setAppName(appInfo.getSoftwareName());
            app.setPublishStatusName(appInfo.getStatusName());
        }
        model.addAttribute("appVersionList",appVersions);
        return "developer/appinfoview";
    }


    /**
     * 跳转修改页面
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("appinfomodify")
    public String appInfoModify(Long id,Model model){
        //获取appinfo
        AppInfo appInfo = appInfoService.findById(id);
        model.addAttribute("appInfo",appInfo);

        return "developer/appinfomodify";
    }

    /**
     * 返回前端图片路径为空,但不删除文件,点击保存时再执行删除
     * @param id
     * @param flag
     * @param model
     * @return
     */
    @RequestMapping("delfile.json")
    @ResponseBody
    public String delFile(Long id,String flag,Model model){
        System.out.println("id"+id+"flag:"+flag);
        if (flag.equals("logo")){
            //获取appinfo
            AppInfo appInfo = appInfoService.findById(id);
            appInfo.setLogoPicPath(null);
            appInfo.setLogoPicPath(null);
            model.addAttribute("appInfo",appInfo);
            return "{\"result\":\"success\"}";
        }
        if(flag.equals("apk")){
            //获取version
            AppVersion appVersion = appVersionService.findAppVersionById(id);
            appVersion.setApkLocPath(null);
            appVersion.setDownloadLink(null);
            model.addAttribute("appVersion",appVersion);
            return "{\"result\":\"success\"}";
        }
        return "{\"result\":\"failed\"}";
    }


    /**
     * 添加appinfo保存
     * @param appInfo
     * @param pic
     * @param request
     * @return
     */
    @RequestMapping("appinfomodifysave")
    public String appInfoModifySave(AppInfo appInfo, @RequestParam(value = "attach",required = false) MultipartFile pic, HttpServletRequest request,Model model){
        System.out.println("appInfo:"+appInfo);
        System.out.println("pic:"+pic);
        System.out.println("picSize:"+pic.getSize());
        boolean flag = appInfoService.modifyAppInfo(model,request, appInfo, pic);
        if (flag){
            return "redirect:list";
        }
        return "developer/appinfomodify";
    }

    /**
     * app版本添加页跳转
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("appversionadd")
    public String appVersionAdd(Long id,Model model,HttpServletRequest request){
        System.out.println(id);
        //获取appinfo
        AppInfo appInfo = appInfoService.findById(id);
        //获取appversion
        List<AppVersion> appVersions = appVersionService.findByAppId(id);
        for (AppVersion app : appVersions) {
            app.setAppName(appInfo.getSoftwareName());
            app.setPublishStatusName(appInfo.getStatusName());
        }
        model.addAttribute("appVersionList",appVersions);
        request.getSession().setAttribute("appVersionAdd", appInfo);
        return "developer/appversionadd";
    }

    /**
     * app版本添加保存
     * @param appVersion
     * @return
     */
    @RequestMapping("addversionsave")
    public String addVersionSave(AppVersion appVersion,@RequestParam(value = "a_downloadLink",required = false) MultipartFile file,HttpServletRequest request,Model model){
        System.out.println(appVersion);
        System.out.println(file);
        if(appVersionService.addAppVersion(request,model,appVersion,file)){
            return "redirect:list";
        }
        return "redirect:appversionadd";
    }

    /**
     * 跳转至version修改页面
     * @param vid
     * @param aid
     * @param model
     * @return
     */
    @RequestMapping("appversionmodify")
    public String appVersionModify(Long vid,Long aid,Model model){
        System.out.println(vid+":"+aid);
        //根据vid查找appVersion
        AppVersion version = appVersionService.findAppVersionById(vid);
        //根据aid查找过往的version
        List<AppVersion> versionList = appVersionService.findByAppId(aid);
        //根据aid查找appInfo
        AppInfo appInfo = appInfoService.findById(aid);
        for (AppVersion v : versionList) {
            v.setAppName(appInfo.getSoftwareName());
            v.setPublishStatusName(appInfo.getStatusName());
        }
        model.addAttribute("appVersion",version);
        model.addAttribute("appVersionList",versionList);
        return "developer/appversionmodify";
    }

    /**
     * 版本更新保存
     * @param appVersion
     * @param apk
     * @param request
     * @param model
     * @return
     */
    @RequestMapping("appversionmodifysave")
    public String appVersionModifySave(AppVersion appVersion, @RequestParam(value = "attach",required = false) MultipartFile apk, HttpServletRequest request,Model model){
        System.out.println("appVersion:"+appVersion);
        System.out.println("apk:"+apk);
        boolean flag = appVersionService.modifyAppVersion(model,request, appVersion, apk);
        if (flag){
            return "redirect:list";
        }
        return "developer/appversionmodify";
    }

    @RequestMapping("delapp.json")
    @ResponseBody
    @Transactional
    public String appDel(Long id,Model model){
        System.out.println("id:"+id);
        if (appInfoService.findById(id)==null){
            return "{\"delResult\":\"notexist\"}";
        }
        if(appVersionService.deleteByAppId(id)&&appInfoService.deleteById(id)){
            return "{\"delResult\":\"true\"}";
        }
        return "{\"delResult\":\"false\"}";
    }

    @RequestMapping("{id}/sale.json")
    @ResponseBody
    public String sale(@PathVariable("id") Long id) throws JsonProcessingException {
        Map<String,String> map = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        System.out.println(id);
        if(id==null){
            map.put("errorCode","param000001");
            map.put("resultMsg","failed");
            return mapper.writeValueAsString(map);
        }
        if(appInfoService.modifyStatusOn_OffSale(id)){
            map.put("errorCode","0");
            map.put("resultMsg","success");
            return mapper.writeValueAsString(map);
        }
        map.put("errorCode","exception000001");
        map.put("resultMsg","failed");
        return mapper.writeValueAsString(map);
    }
}
