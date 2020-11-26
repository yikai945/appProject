package com.gdkjxy.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author wuyikai
 * @version 1.0
 * @date 2020/11/20 9:54
 */
@Getter
@Setter
@ToString
public class AppVersion {
    private Long id;
    private Long appId;
    private String versionNo;
    private Long publishStatus;
    private String downloadLink;
    private BigDecimal versionSize;
    private Long createBy;
    private Date createDate;
    private Long modifyBy;
    private Date modifyDate;
    private String apkLocPath;
    private String apkFileName;
}
