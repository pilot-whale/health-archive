package com.sudobang.healtharchive.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * 用户DTO类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private Long id;

    private String userName;

    private String residentIdentityCardNumber;

    private String password;

    private String familyGroup;

    private String name;

}
