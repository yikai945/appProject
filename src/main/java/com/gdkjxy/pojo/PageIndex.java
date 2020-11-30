package com.gdkjxy.pojo;

import lombok.*;

/**
 * @Description 分页类
 * @Author wuyikai
 * @Date 2020/11/27
 * @Version 1.0
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PageIndex {
    private int currentPageNo;//当前页码
    private int totalCount;//总记录
    private int totalPageCount;//总页数
}
