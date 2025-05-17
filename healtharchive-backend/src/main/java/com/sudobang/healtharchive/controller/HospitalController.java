package com.sudobang.healtharchive.controller;


import com.sudobang.healtharchive.entity.Hospital;
import com.sudobang.healtharchive.entity.PageBean;
import com.sudobang.healtharchive.result.Result;
import com.sudobang.healtharchive.service.HospitalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hospital")
@Tag(name = "医院相关接口") // 用@Tag替换@Api
@Slf4j
public class HospitalController {

    @Autowired
    private HospitalService hospitalService;


    /**
     * 医院分页查询
     *
     * @param
     * @return
     */
    @GetMapping
    @Operation(summary = "医院分页查询")
    public Result<PageBean<Hospital>> list(
            Integer pageNum,
            Integer pageSize,
            @RequestParam(required = false) String state,
            @RequestParam(required = false) String level,
            @RequestParam(required = false) Boolean jci,
            @RequestParam(required = false) Boolean cap,
            @RequestParam(required = false) String grade
    ) {
        log.info("分页查询: 第{}页, 每页{}条记录, state:{}, level:{} jci:{}, cap:{}, grade:{}", pageNum, pageSize, state, level, jci, cap, grade);
        PageBean<Hospital> pb = hospitalService.list(pageNum, pageSize, state, level, jci, cap, grade);
        return Result.success(pb);
    }
}
