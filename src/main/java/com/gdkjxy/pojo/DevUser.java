package com.gdkjxy.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * @author wuyikai
 * @version 1.0
 * @date 2020/11/20 10:08
 */
@Getter
@Setter
@ToString
public class DevUser {
    private Long id;
    private String devCode;
    private String devName;
    private String devPassword;
    private String devEmail;
    private String devInfo;
    private Long createBy;
    private Date creationDate;
    private Long modifyBy;
    private Date modifyDate;
}
