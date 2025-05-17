package com.sudobang.healtharchive.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * (CheckupItem)表实体类
 *
 * @author makejava
 * @since 2025-05-13 15:36:02
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckupItem implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private String mainCategory;

}

