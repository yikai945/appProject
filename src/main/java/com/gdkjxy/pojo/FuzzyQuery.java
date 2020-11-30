package com.gdkjxy.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @Description 前端查询所需属性
 * @Author wuyikai
 * @Date 2020/11/28
 * @Version 1.0
 */
@Getter
@Setter
@ToString
public class FuzzyQuery {
    private int pageIndex;//当前页码
    private String querySoftwareName;//查询的软件名
    private Integer queryStatus;//状态
    private Integer queryFlatformId;//平台valueId
    private Integer queryCategoryLevel1;//1级菜单
    private Integer queryCategoryLevel2;//2级菜单
    private Integer queryCategoryLevel3;//3级菜单

    private Integer categorylevel2list;//不知道是啥玩意
}
