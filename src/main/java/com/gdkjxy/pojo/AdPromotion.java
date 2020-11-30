package com.gdkjxy.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * @author wuyikai
 * @version 1.0
 * @date 2020/11/20 9:31
 */
@Getter
@Setter
@ToString
public class AdPromotion {
    private Long id;
    private Long appId;
    private String adPicPath;
    private Long adPv;
    private Integer carouselPosition;
    private Date startTime;
    private Date endTime;
    private Long createdBy;
    private Date creationDate;
    private Long modifyBy;
    private Date modifyDate;
}
