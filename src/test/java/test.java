import com.gdkjxy.mapper.AppCategoryMapper;
import com.gdkjxy.mapper.AppInfoMapper;
import com.gdkjxy.mapper.AppVersionMapper;
import com.gdkjxy.mapper.DataDictionaryMapper;
import com.gdkjxy.pojo.AppCategory;
import com.gdkjxy.pojo.AppInfo;
import com.gdkjxy.service.AppCategoryService;
import com.gdkjxy.service.AppInfoService;
import com.gdkjxy.service.DataDictionaryService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description TODO
 * @Author wuyikai
 * @Date 2020/11/27
 * @Version 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class test {
    @Autowired
    AppInfoService appInfoService;

    @Autowired
    AppInfoMapper appInfoMapper;

    @Autowired
    DataDictionaryMapper dataDictionaryMapper;

    @Autowired
    AppVersionMapper appVersionMapper;

    @Autowired
    AppCategoryMapper appCategoryMapper;
    @Test
    public void test(){
//        PageHelper.startPage(2, 5);
//        List<AppInfo> all = appInfoMapper.findAppInfoDetails();
//        for (AppInfo appInfo : all) {
//            System.out.println(appInfo.getId());
//        }
//        PageInfo<AppInfo> page = new PageInfo<>(all);
//        System.out.println("总数量：" + page.getTotal());
//        System.out.println("当前页查询记录：" + page.getList().size());
//        System.out.println("当前页码：" + page.getPageNum());
//        System.out.println("每页显示数量：" + page.getPageSize());
//        System.out.println("总页：" + page.getPages());
    }

    public void backup(){
//        List<AppInfo> appInfos = appInfoMapper.findAll();
//        for (AppInfo appInfo : appInfos) {
//            String flatformName = dataDictionaryMapper.findAppFlatformByValueId(appInfo.getFlatformId());
//            String statusName = dataDictionaryMapper.findAppStatusByValueId(appInfo.getStatus());
//            String versionNo;
//            try{
//                versionNo = appVersionMapper.findVersionNoByAppId(appInfo.getVersionId());
//            }catch (Exception e){
//                versionNo = "V1.0.0(默认)";
//            }
//            List<Integer> list = new ArrayList<>();
//            list.add(appInfo.getCategoryLevel1());
//            list.add(appInfo.getCategoryLevel2());
//            list.add(appInfo.getCategoryLevel3());
//            List<AppCategory> appCategories = appCategoryMapper.findCategoryNameByIds(list);
//            appInfo.setFlatformName(flatformName);
//            appInfo.setStatusName(statusName);
//            appInfo.setVersionNo(versionNo);
//            appInfo.setCategoryLevel1Name(appCategories.get(0).getCategoryName());
//            appInfo.setCategoryLevel2Name(appCategories.get(1).getCategoryName());
//            appInfo.setCategoryLevel3Name(appCategories.get(2).getCategoryName());
//        }
    }

    @Autowired
    AppCategoryService appCategoryService;

    @Test
    public void testlevel(){
        System.out.println(appCategoryService.getCategoryLevel(1L));
    }


    @Autowired
    DataDictionaryService dataDictionaryService;

    @Test
    public void dataDictionary(){

        String classpath =  this .getClass().getResource( "/" ).getPath().replaceFirst( "/" ,  "" );
        String webappRoot = classpath.replaceAll( "WEB-INF/classes/" ,  "" );
        System.out.println(webappRoot);
    }
}