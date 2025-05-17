package com.sudobang.healtharchive.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Tag(name = "体检数据显示在页面中的数据格式")
public class CheckupEchartBean implements Serializable {

    @Schema(description = "体检结果")
    private String checkupResult;

    @Schema(description = "医院名称")
    private String hospitalName;

    @Schema(description = "体检时间")
    private Date createDate;
}
