package com.gdkjxy.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * @author wuyikai
 * @version 1.0
 * @date 2020/11/20 10:04
 */
@Getter
@Setter
@ToString
public class DataDictionary {
    private Long id;
    private String typeCode;
    private String typeName;
    private Long valueId;
    private String valueName;
    private Long createBy;
    private Date creationDate;
    private Long modifyBy;
    private Date modifyDate;
}
