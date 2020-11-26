package com.gdkjxy.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * @author wuyikai
 * @version 1.0
 * @date 2020/11/20 9:43
 */
@Getter
@Setter
@ToString
public class AppCategory {
    private Long id;
    private String categoryCode;
    private String categoryName;
    private Long parentId;
    private Long createBy;
    private Date creationTime;
    private Long modifyBy;
    private Date modifyDate;
}
