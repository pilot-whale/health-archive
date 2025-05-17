package com.sudobang.healtharchive.controller;

import com.sudobang.healtharchive.dto.FamilyDTO;
import com.sudobang.healtharchive.entity.Family;
import com.sudobang.healtharchive.entity.PageBean;
import com.sudobang.healtharchive.entity.User;
import com.sudobang.healtharchive.result.Result;
import com.sudobang.healtharchive.service.FamilyService;
import com.sudobang.healtharchive.vo.CheckupReportVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/family")
@Tag(name = "家庭组相关接口") // 用@Tag替换@Api
@Slf4j
public class FamilyController {

    @Autowired
    private FamilyService familyService;

    /**
     * 根据id查询家庭组
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @Operation(summary = "根据id查询家庭组")
    public Result<Family> getFamily(@PathVariable Long id) {
        log.info("根据id查询家庭： {}", id);
        Family family = familyService.getById(id);
        return Result.success(family);
    }


    /**
     * 创建新的家庭组
     * @param familyDTO
     * @return
     */
    @PostMapping
    @Operation(summary = "创建新的家庭组")
    public Result createFamily(@RequestBody FamilyDTO familyDTO) {
        log.info("创建新的家庭组{}", familyDTO);
        familyService.save(familyDTO);
        return Result.success();
    }

    @GetMapping
    @Operation(summary = "根据密码和分享码查询用户组")
    public Result<List<Integer>> joinGroup(
            @RequestParam("shareCode") String shareCode,
            @RequestParam("password") String password
    ) {
        log.info("按类别查询: 分享码-{}，密码-{}", shareCode, password);
        List<Integer> familyGroup = familyService.joinGroup(shareCode, password);
        return Result.success(familyGroup);
    }

    @GetMapping("/member")
    @Operation(summary = "查看用户组的所有成员")
    public Result<PageBean<User>> list(
            @Param("pageNum") Integer pageNum,
            @Param("pageSize") Integer pageSize
    ) {
        log.info("查看用户组的所有成员");
        PageBean<User> pb = familyService.list(pageNum, pageSize);
        return Result.success(pb);
    }

    @GetMapping("/listAll")
    @Operation(summary = "管理端用户总览")
    public Result<PageBean<Family>> listAll(
            Integer pageNum,
            Integer pageSize
    ) {
        log.info("用户组总览: 第{}页, 每页{}条记录", pageNum, pageSize);
        PageBean<Family> pb = familyService.listAll(pageNum, pageSize);
        return Result.success(pb);
    }

}
