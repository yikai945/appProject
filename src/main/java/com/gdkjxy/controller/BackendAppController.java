package com.gdkjxy.controller;

import com.gdkjxy.mapper.AppInfoMapper;
import com.gdkjxy.pojo.*;
import com.gdkjxy.service.AppCategoryService;
import com.gdkjxy.service.AppInfoService;
import com.gdkjxy.service.AppVersionService;
import com.gdkjxy.service.DataDictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description TODO
 * @Author wuyikai
 * @Date 2020/11/27
 * @Version 1.0
 */
@Controller
@RequestMapping("manage/backend/app")
public class BackendAppController {

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
        model.addAttribute("appInfoList", appInfos);
        model.addAttribute("categoryLevel1List", level.get(0));
        model.addAttribute("categoryLevel2List", level.get(1));
        model.addAttribute("categoryLevel3List", level.get(2));
        model.addAttribute("flatFormList", flatform);
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

        return "backend/applist";
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
     * 审核appInfo
     * @param aid
     * @param vid
     * @return
     */
    @RequestMapping("check")
    public String appCheck(Long aid,Long vid,Model model) {
        AppInfo appInfo = appInfoService.findById(aid);
        model.addAttribute("appInfo",appInfo);
        AppVersion appVersion = appVersionService.findAppVersionById(vid);
        model.addAttribute("appVersion",appVersion);
        return "backend/appcheck";
    }

    @RequestMapping("checksave")
    public String appCheckSave(Long id,Integer status){
        System.out.println("id:"+id+"  status:"+status);
        if(appInfoService.modifyStatusById(id,status)){
            return "redirect:list";
        }
        return "backend/appcheck";
    }
}
