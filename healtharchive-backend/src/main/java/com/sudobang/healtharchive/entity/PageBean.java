package com.sudobang.healtharchive.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 封装分页查询结果
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageBean<T>{

    private Long total; //总记录数

    private List<T> items; //当前页数据集合

}
