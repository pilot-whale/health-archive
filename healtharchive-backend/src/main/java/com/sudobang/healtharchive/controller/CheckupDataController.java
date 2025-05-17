package com.sudobang.healtharchive.controller;

import com.sudobang.healtharchive.result.Result;
import com.sudobang.healtharchive.service.CheckupDataService;
import com.sudobang.healtharchive.vo.CheckupDataVO;
import com.sudobang.healtharchive.vo.EchartVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/checkupData")
@Tag(name = "体检数据相关接口") // 用@Tag替换@Api
@Slf4j
public class CheckupDataController {

    @Autowired
    private CheckupDataService checkupDataService;

    @GetMapping
    @Operation(summary = "体检数据按类别查询")
    public Result<List<CheckupDataVO>> list(
            @RequestParam("itemId") Integer itemId,
            @RequestParam("reportId") Integer reportId
    ) {
        log.info("按类别查询: 体检大类id-{}， 体检报告id-{}", itemId, reportId);
        List<CheckupDataVO> li = checkupDataService.list(itemId, reportId);
        return Result.success(li);
    }

    @GetMapping("/visual")
    @Operation(summary = "可视化数据按指标查询")
    public Result<EchartVO> listVisual(
            @RequestParam("selectedIndicator") Integer selectedIndicator,
            @RequestParam("selectedPeriod") String selectedPeriod
    ) {
        log.info("按指标id查询历史数据: {}", selectedIndicator);
        EchartVO echartVO = checkupDataService.listVisual(selectedIndicator, selectedPeriod);
        return Result.success(echartVO);
    }
}
