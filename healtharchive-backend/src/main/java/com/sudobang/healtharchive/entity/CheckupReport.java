package com.sudobang.healtharchive.entity;

import java.util.Date;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * (CheckupReport)表实体类
 *
 * @author makejava
 * @since 2025-05-13 15:36:39
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckupReport implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private Integer userId;

    private Integer hospitalId;

    private Date createDate;

}

