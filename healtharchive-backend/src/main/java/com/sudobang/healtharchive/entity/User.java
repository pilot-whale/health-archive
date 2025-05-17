package com.sudobang.healtharchive.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;


/**
 * (User)表实体类
 *
 * @author makejava
 * @since 2025-03-26 23:02:21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String userName;

    private String residentIdentityCardNumber;

    private String password;

    private Long familyGroup;

    private String name;

    private Integer permission;

}

