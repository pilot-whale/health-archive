package com.sudobang.healtharchive.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
@Schema(name = "UserLoginDTO", description = "用户登录时传递的数据模型")
public class UserLoginDTO implements Serializable {

    @Schema(description = "身份证号")
    private String residentIdentityCardNumber;

    @Schema(description = "密码")
    private String password;
}