package com.gdkjxy.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * @author wuyikai
 * @version 1.0
 * @date 2020/11/20 10:01
 */
@Getter
@Setter
@ToString
public class BackendUser {
    private Long id;
    private String userCode;
    private String userName;
    private Long userType;
    private Long createBy;
    private Date creationDate;
    private Long modifyBy;
    private Date modifyDate;
    private String userPassword;

    private String userTypeName;
}
