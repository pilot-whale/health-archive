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
@Tag(name = "体检报告显示在页面中的数据格式")
public class CheckupReportVO {

    @Schema(description = "体检报告编号")
    private String id;

    @Schema(description = "医院名")
    private String hospitalName;

    @Schema(description = "体检日期")
    private String createDate;
}
