package com.sudobang.healtharchive.dto;

import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.Data;

import java.io.Serializable;

@Data
public class HospitalPageQueryDTO implements Serializable {

    //页码
    private Integer pageNum;

    //每页显示记录数
    private Integer pageSize;

    private Integer state;

    private Integer rank;

    private Integer level;

    private Integer jci;

    private Integer cap;
}
