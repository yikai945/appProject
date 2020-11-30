package com.gdkjxy.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author wuyikai
 * @version 1.0
 * @date 2020/11/20 9:46
 */
@Getter
@Setter
@ToString
public class AppInfo {
    private Long id;
    private String softwareName;
    private String APKName;
    private String supportROM;
    private String interfaceLanguage;
    private Date updateDate;
    private BigDecimal softwareSize;
    private Long devId;
    private String appInfo;
    private Long status;
    private Date onSaleDate;
    private Date offSaleDate;
    private Integer categoryLevel3;
    private Integer downloads;
    private Integer flatformId;
    private Long createdBy;
    private Date creationDate;
    private Long modifyBy;
    private Date modifyDate;
    private Integer categoryLevel1;
    private Integer categoryLevel2;
    private String logoPicPath;
    private String logoLocPath;
    private Integer versionId;

    /**
     * 前端所需属性
     */
    private String flatformName;
    private String statusName;
    private String categoryLevel1Name;
    private String categoryLevel2Name;
    private String categoryLevel3Name;
    private String devName;
    private String versionNo;
}
