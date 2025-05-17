package com.sudobang.healtharchive.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * (Family)表实体类
 *
 * @author makejava
 * @since 2025-03-26 23:02:21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Family implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String familyName;

    private String password;

    private String shareCode;

}
