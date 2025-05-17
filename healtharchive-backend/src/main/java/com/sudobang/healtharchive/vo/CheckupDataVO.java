package com.sudobang.healtharchive.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Tag(name = "体检数据显示在页面中的数据格式")
public class CheckupDataVO {

    @Schema(description = "项目名称")
    private String name;

    @Schema(description = "检查结果")
    private String result;

    @Schema(description = "参考值")
    private String reference;

    @Schema(description = "单位")
    private String unit;

    @Schema(description = "是否异常")
    private String exception;
}
