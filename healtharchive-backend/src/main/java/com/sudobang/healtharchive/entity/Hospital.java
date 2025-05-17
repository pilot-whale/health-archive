package com.sudobang.healtharchive.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * (Hospital)表实体类
 *
 * @author makejava
 * @since 2025-04-08 22:58:15
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Hospital implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;

    private String address;

    private String postalCode;

    private Long id;

    private String phone;

    private String officialWebsite;

    private String state;

    private String level;

    private Integer jci;

    private Integer cap;

    private String grade;

}

