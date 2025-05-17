package com.sudobang.healtharchive.controller;


import com.sudobang.healtharchive.result.Result;
import com.sudobang.healtharchive.service.CheckupIndicatorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.sudobang.healtharchive.entity.CheckupIndicator;

import java.util.List;

@RestController
@RequestMapping("/checkupIndicator")
@Tag(name = "体检指标相关接口") // 用@Tag替换@Api
@Slf4j
public class CheckupIndicatorController {

    @Autowired
    private CheckupIndicatorService checkupIndicatorService;


    @GetMapping("/{id}")
    @Operation(summary = "体检指标查询")
    public Result<List<CheckupIndicator>> delete(@PathVariable Integer id) {
        log.info("查询体检项目: id-{}的指标", id);
        List<CheckupIndicator> cilist = checkupIndicatorService.list(id);
        return Result.success(cilist);
    }
}
