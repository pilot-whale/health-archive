package com.sudobang.healtharchive.entity;

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
@Tag(name = "体检数据显示在页面中的数据格式")
public class CheckupDataBean implements Serializable {

    @Schema(description = "项目名称")
    private String name;

    @Schema(description = "检查结果")
    private String value;

    @Schema(description = "参考值-低")
    private Double referenceLow;

    @Schema(description = "参考值-高")
    private Double referenceHigh;

    @Schema(description = "单位")
    private String unit;
}
