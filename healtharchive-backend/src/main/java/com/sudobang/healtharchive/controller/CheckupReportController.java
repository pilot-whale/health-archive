package com.sudobang.healtharchive.controller;

import com.sudobang.healtharchive.entity.PageBean;
import com.sudobang.healtharchive.result.Result;
import com.sudobang.healtharchive.service.CheckupReportService;
import com.sudobang.healtharchive.vo.CheckupReportVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/checkupReport")
@Tag(name = "体检报告相关接口") // 用@Tag替换@Api
@Slf4j
public class CheckupReportController {

    @Autowired
    private CheckupReportService checkupReportService;

    @GetMapping
    @Operation(summary = "体检报告分页查询")
    public Result<PageBean<CheckupReportVO>> list(
            Integer pageNum,
            Integer pageSize
    ) {
        log.info("分页查询: 第{}页, 每页{}条记录", pageNum, pageSize);
        PageBean<CheckupReportVO> pb = checkupReportService.list(pageNum, pageSize);
        return Result.success(pb);
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "体检报告删除")
    public Result<String> delete(@PathVariable Integer id) {
        log.info("删除体检报告: id-{}", id);
        checkupReportService.delete(id);
        return Result.success("操作成功");
    }

    @GetMapping("/admin")
    @Operation(summary = "IDcard查询体检报告")
    public Result<PageBean<CheckupReportVO>> listByNumber(
            Integer pageNum,
            Integer pageSize,
            @RequestParam() String residentIdentityCardNumber
    ) {
        log.info("分页查询: 第{}页, 每页{}条记录, id-{}", pageNum, pageSize, residentIdentityCardNumber);
        PageBean<CheckupReportVO> pb = checkupReportService.listByNumber(pageNum, pageSize, residentIdentityCardNumber);
        return Result.success(pb);
    }
}
