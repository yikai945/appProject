package com.gdkjxy.mapper;

import com.gdkjxy.pojo.AppCategory;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface AppCategoryMapper {

    /**
     * 根据ids查找一二三级菜单
     * @param ids
     * @return
     */
    @Select({"<script>",
            " select id,categoryName from app_category where id in" +
                    " <foreach collection = 'ids' item='item' index='index' open='(' separator=',' close=')'>" +
                    " #{item}"+
                    " </foreach>"+
                    " order by id asc"+
                    " </script>"})
    List<AppCategory> findCategoryNameByIds(@Param("ids") List<Integer> ids);


    /**
     * 根据id查找CategoryName
     * @param id
     * @return
     */
    @Select("select categoryName from app_category where id = #{id}")
    String findCategoryNameById(Integer id);

    /**
     * 查找一级菜单
     * @return
     */
    @Select("select * from app_category where parentId is null")
    List<AppCategory> findCategoryLevel1();

    /**
     * 根据父菜单id集合查找所有子菜单
     * @param ids
     * @return
     */
    @Select({"<script>",
            " select * from app_category where parentId in" +
                    " <foreach collection = 'ids' item='item' index='index' open='(' separator=',' close=')'>" +
                    " #{item}"+
                    " </foreach>"+
                    " </script>"})
    List<AppCategory> findCategoryLevel(@Param("ids") List<Long> ids);

    /**
     * 根据指定父菜单id查找子菜单
     * @param pid
     * @return
     */
    @Select("select * from app_category where parentId = #{pid}")
    List<AppCategory> findCategoryLevelByPid(Long pid);

}