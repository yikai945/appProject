package com.gdkjxy.mapper;

import com.gdkjxy.mapper.provider.AppInfoDynaSqlProvider;
import com.gdkjxy.pojo.AppInfo;
import org.apache.ibatis.annotations.*;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;

public interface AppInfoMapper {

    /**
     * 查找所有AppInfo
     * @return
     */
    @Select("select * from app_info")
    List<AppInfo> findAll();

    /**
     * 根据视图查询出appinfo_detail
     * @return
     */
    @SelectProvider(type = AppInfoDynaSqlProvider.class,method = "findWithParam")
    List<AppInfo> findAppInfoDetails(Map<String,Object> params);

    /**
     * 检查ApkName是否已经存在
     * @param apkName
     * @return
     */
    @Select("select * from app_info where APKName = #{apkName} limit 0,1")
    AppInfo isApkNameExist(String apkName);

    /**
     * 添加AppInfo
     * @return
     */
    @Insert("insert into " +
            "app_info(softwareName,APKName,supportROM,interfaceLanguage,softwareSize,updateDate,devId,appInfo,status,onSaleDate,offSaleDate,flatformId,categoryLevel3,downloads,createdBy,creationDate,modifyBy,modifyDate,categoryLevel1,categoryLevel2,logoPicPath,logoLocPath,versionId) " +
            "values(#{softwareName},#{APKName},#{supportROM},#{interfaceLanguage},#{softwareSize},#{updateDate},#{devId},#{appInfo},#{status},#{onSaleDate},#{offSaleDate},#{flatformId},#{categoryLevel3},#{downloads},#{createdBy},#{creationDate},#{modifyBy},#{modifyDate},#{categoryLevel1},#{categoryLevel2},#{logoPicPath},#{logoLocPath},#{versionId})")
    int addAppInfo(AppInfo appInfo);

    /**
     * 根据id查找appinfo
     * @param id
     * @return
     */
    @Select("select * from app_detail_info_view where id = #{id}")
    AppInfo findById(Long id);

    /**
     * 根据id查找logo路径
     * @param id
     * @return
     */
    @Select("select id,logoPicPath,logoLocPath from app_info where id = #{id}")
    AppInfo findPathById(Long id);

    /**
     * 根据id修改appInfo
     * @param appInfo
     * @return
     */
    @Update("<script>" +
            "update app_info set " +
            " <if test=\"status != null\">status=#{status},</if> " +
            " softwareName=#{softwareName},supportROM=#{supportROM},interfaceLanguage=#{interfaceLanguage},softwareSize=#{softwareSize},updateDate=#{updateDate},devId=#{devId},appInfo=#{appInfo},onSaleDate=#{onSaleDate},offSaleDate=#{offSaleDate},flatformId=#{flatformId},categoryLevel3=#{categoryLevel3},downloads=#{downloads},createdBy=#{createdBy},creationDate=#{creationDate},modifyBy=#{modifyBy},modifyDate=#{modifyDate},categoryLevel1=#{categoryLevel1},categoryLevel2=#{categoryLevel2},logoPicPath=#{logoPicPath},logoLocPath=#{logoLocPath},versionId=#{versionId}" +
            " where id = #{id}" +
            "</script>")
    int modifyAppInfo(AppInfo appInfo);

    /**
     * 更改版本
     * @param id
     * @param versionId
     * @return
     */
    @Update("update app_info set versionId = #{versionId} where id = #{id}")
    int modifyAppVersionIdById(@Param("id") Long id,@Param("versionId") Long versionId);

    /**
     * 根据id删除appInfo
     * @param id
     * @return
     */
    @Delete("delete from app_info where id = #{id}")
    int deleteById(Long id);

    /**
     * 根据id修改状态
     * @param id
     * @param status
     * @return
     */
    @Update("update app_info set status = #{status} where id = #{id}")
    int modifyStatusById(@Param("id") Long id,@Param("status") Integer status);

    /**
     * 上下架app
     * @param id
     * @return
     */
    @Update("update app_info set status = (select valueId from data_dictionary where typeCode = 'APP_STATUS' and valueName = #{status}) where id = #{id}")
    int modifyStatusOnSale(@Param("id") Long id,@Param("status") String status);

    /**
     * 查找status
     * @param id
     * @return
     */
    @Select("select d.valueName from app_info a,data_dictionary d where a.status = d.valueId and d.typeCode = 'APP_STATUS' and a.id = #{id}")
    String findStatusById(Long id);

}