package com.gdkjxy.mapper;

import com.gdkjxy.pojo.DataDictionary;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface DataDictionaryMapper {

    /**
     * 根据id查找TypeName
     * @param id
     * @return
     */
    @Select("select valueName from data_dictionary where id = #{id}")
    String findValueNameById(Long id);

    /**
     * 根据appInfo.flatformId查找所属平台
     * @param id
     * @return
     */
    @Select("select valueName from data_dictionary where typeCode = 'APP_FLATFORM' and valueId = #{id}")
    String findAppFlatformByValueId(Integer id);

    /**
     * 根据appInfo.flatformId查找App状态
     * @param id
     * @return
     */
    @Select("select valueName from data_dictionary where typeCode = 'APP_STATUS' and valueId = #{id}")
    String findAppStatusByValueId (Integer id);

    /**
     * 查找所有APP_STATUS
     * @return
     */
    @Select("select * from data_dictionary where typeCode = 'APP_STATUS'")
    List<DataDictionary> findAllAppStatus();

    /**
     * 查找所有APP_FLATFORM
     * @return
     */
    @Select("select * from data_dictionary where typeCode = 'APP_FLATFORM'")
    List<DataDictionary> findAllAppFlatform();
}