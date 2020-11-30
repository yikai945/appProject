package com.gdkjxy.mapper;

import com.gdkjxy.pojo.AppVersion;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface AppVersionMapper {

    /**
     * 根据appId查找发布过的版本,并进行降序排序
     * @param id
     * @return
     */
    @Select("select * from app_version where appId = #{id} order by versionNo desc")
    List<AppVersion> findByAppId(Long id);

    /**
     * 添加版本信息
     * @param appVersion
     * @return
     */
    @Insert("insert into app_version(appId,versionNo,versionInfo,publishStatus,downloadLink,versionSize,createdBy,creationDate,modifyBy,modifyDate,apkLocPath,apkFileName)" +
            " values(#{appId},#{versionNo},#{versionInfo},#{publishStatus},#{downloadLink},#{versionSize},#{createdBy},#{creationDate},#{modifyBy},#{modifyDate},#{apkLocPath},#{apkFileName})")
    @Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
    int addAppVersion(AppVersion appVersion);

    /**
     * 根据id查找出appVersion
     * @param id
     * @return
     */
    @Select("select * from app_version where id = #{id}")
    AppVersion findById(Long id);

    /**
     * 根据id跟新版本
     * @param appVersion
     * @return
     */
    @Update("update app_version set appId=#{appId},versionNo=#{versionNo},versionInfo=#{versionInfo},publishStatus=#{publishStatus},downloadLink=#{downloadLink},versionSize=#{versionSize},createdBy=#{createdBy},creationDate=#{creationDate},modifyBy=#{modifyBy},modifyDate=#{modifyDate},apkLocPath=#{apkLocPath},apkFileName=#{apkFileName} where id=#{id}")
    int modifyAppVersion(AppVersion appVersion);

    /**
     * 根据appid删除版本号
     * @param id
     * @return
     */
    @Delete("delete from app_version where appId = #{id}")
    int deleteByAppId(Long id);
}