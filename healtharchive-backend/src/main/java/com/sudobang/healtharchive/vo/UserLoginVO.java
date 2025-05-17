package com.sudobang.healtharchive.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Tag(name = "用户登录返回的数据格式")
public class UserLoginVO implements Serializable {

    @Schema(description = "主键值")
    private Long Id;

    @Schema(description = "用户名")
    private String userName;

    @Schema(description = "身份证号")
    private String residentIdentityCardNumber;

    @Schema(description = "jwt令牌")
    private String token;
}
