package com.sudobang.healtharchive.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 家庭DTO类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FamilyDTO {
    private Long id;

    private String familyName;

    private String password;
}
