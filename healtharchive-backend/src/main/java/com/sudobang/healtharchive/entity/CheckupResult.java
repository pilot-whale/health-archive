package com.sudobang.healtharchive.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * (CheckupResult)表实体类
 *
 * @author makejava
 * @since 2025-05-13 15:36:56
 */


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckupResult implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private Integer reportId;

    private Integer checkupIndicatorId;

    private String value;

    private String doctorName;


}

