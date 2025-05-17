package com.sudobang.healtharchive.controller;

import com.sudobang.healtharchive.entity.CheckupItem;
import com.sudobang.healtharchive.result.Result;
import com.sudobang.healtharchive.service.CheckupItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/checkupItem")
@Tag(name = "体检大类相关接口") // 用@Tag替换@Api
@Slf4j
public class CheckupItemController {

    @Autowired
    private CheckupItemService checkupItemService;

    @GetMapping
    @Operation(summary = "体检大类查询")
    public Result<List<CheckupItem>> list() {
        log.info("查询体检大类");
        List<CheckupItem> li = checkupItemService.list();
        return Result.success(li);
    }
}
