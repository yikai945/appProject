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
    private String apkName;
    private String supportRom;
    private String interfaceLanguage;
    private BigDecimal softwareSize;
    private Date updateDate;
    private Long devId;
    private String appInfo;
    private Boolean status;
    private Date onSaleDate;
    private Date offSaleDate;
    private Long platformId;
    private Long categoryLevel3;
    private Long downloads;
    private Long createBy;
    private Date creationDate;
    private Long modifyBy;
}
