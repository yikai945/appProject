package com.gdkjxy.mapper;

import com.gdkjxy.pojo.DataDictionary;
import org.apache.ibatis.annotations.Select;

public interface DataDictionaryMapper {

    /**
     * 根据id查找TypeName
     * @param id
     * @return
     */
    @Select("select valueName from data_dictionary where id = #{id}")
    String findValueNameById(Long id);

    int deleteByPrimaryKey(Long id);

    int insert(DataDictionary record);

    int insertSelective(DataDictionary record);

    DataDictionary selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DataDictionary record);

    int updateByPrimaryKey(DataDictionary record);
}