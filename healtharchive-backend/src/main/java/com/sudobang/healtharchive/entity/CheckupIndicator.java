package com.sudobang.healtharchive.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * (CheckupSubitem)表实体类
 *
 * @author makejava
 * @since 2025-05-13 15:37:06
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckupIndicator implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private String name;

    private Double referenceLow;

    private Double referenceHigh;

    private String unit;

    private Integer checkupItemId;

}

