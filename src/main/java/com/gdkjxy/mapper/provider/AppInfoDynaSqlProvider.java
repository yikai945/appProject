package com.gdkjxy.mapper.provider;

import com.gdkjxy.pojo.FuzzyQuery;
import org.apache.ibatis.jdbc.SQL;

import java.util.Map;

/**
 * @Description TODO
 * @Author wuyikai
 * @Date 2020/11/28
 * @Version 1.0
 */
public class AppInfoDynaSqlProvider {
    //AppInfo动态查询
    public String  findWithParam(Map<String,Object> params){
        final FuzzyQuery fuzzyQuery = (FuzzyQuery) params.get("fuzzyQuery");
        String sql = new SQL(){
            {
                SELECT("*");
                FROM("app_detail_info_view");
                if (fuzzyQuery != null){
                    if(fuzzyQuery.getQuerySoftwareName()!=null && !"".equals(fuzzyQuery.getQuerySoftwareName())){
                        WHERE(" binary softwareName like CONCAT('%',#{fuzzyQuery.querySoftwareName},'%')");
                    }
                    if(fuzzyQuery.getQueryStatus()!=null){
                        WHERE(" status = #{fuzzyQuery.queryStatus}");
                    }
                    if(fuzzyQuery.getQueryFlatformId()!=null){
                        WHERE(" flatformId = #{fuzzyQuery.queryFlatformId}");
                    }
                    if(fuzzyQuery.getQueryCategoryLevel1()!=null){
                        WHERE(" categoryLevel1 = #{fuzzyQuery.queryCategoryLevel1}");
                    }
                    if(fuzzyQuery.getQueryCategoryLevel2()!=null){
                        WHERE(" categoryLevel2 = #{fuzzyQuery.queryCategoryLevel2}");
                    }
                    if(fuzzyQuery.getQueryCategoryLevel3()!=null){
                        WHERE(" categoryLevel3 = #{fuzzyQuery.queryCategoryLevel3}");
                    }
                }
            }
        }.toString();
        System.out.println("dynaSql:"+sql);
        return sql;
    }

}
