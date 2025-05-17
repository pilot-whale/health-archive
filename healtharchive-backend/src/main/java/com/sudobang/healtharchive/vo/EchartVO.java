package com.sudobang.healtharchive.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Tag(name = "echart显示在页面中的数据格式")
public class EchartVO {

    @Schema(description = "参考值上界")
    private Double referenceHigh;

    @Schema(description = "参考值下界")
    private Double referenceLow;

    @Schema(description = "单位")
    private String unit;

    @Schema(description = "体检结果列表")
    private List<String> checkupResultList;

    @Schema(description = "医院名称列表")
    private List<String> hospitalNameList;

    @Schema(description = "体检时间列表")
    private List<String> createDateList;
}
