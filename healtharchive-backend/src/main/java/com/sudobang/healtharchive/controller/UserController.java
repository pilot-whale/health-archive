package com.sudobang.healtharchive.controller;

import com.sudobang.healtharchive.constant.JwtClaimsConstant;
import com.sudobang.healtharchive.context.BaseContext;
import com.sudobang.healtharchive.dto.UserDTO;
import com.sudobang.healtharchive.dto.UserLoginDTO;
import com.sudobang.healtharchive.entity.PageBean;
import com.sudobang.healtharchive.entity.User;
import com.sudobang.healtharchive.properties.JwtProperties;
import com.sudobang.healtharchive.result.Result;
import com.sudobang.healtharchive.service.UserService;
import com.sudobang.healtharchive.utils.JwtUtil;
import com.sudobang.healtharchive.vo.CheckupReportVO;
import com.sudobang.healtharchive.vo.UserLoginVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Tag(name = "用户相关接口") // 用@Tag替换@Api
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private JwtProperties jwtProperties;


    /**
     * 登录
     * @param userLoginDTO
     * @return
     */
    @PostMapping("/login")
    @Operation(summary = "用户登录",  // 替换@ApiOperation
            description = "同时通过用户名密码创建JWT令牌"
    )
    public Result<String> login(@RequestBody UserLoginDTO userLoginDTO) {
        log.info("用户登录： {}", userLoginDTO);

        User user = userService.login(userLoginDTO);

        //登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.ID, user.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getUserSecretKey(),
                jwtProperties.getUserTtl(),
                claims);

        UserLoginVO userLoginVO = UserLoginVO.builder()
                .Id(user.getId())
                .userName(user.getUserName())
                .residentIdentityCardNumber(user.getResidentIdentityCardNumber())
                .token(token)
                .build();

        return Result.success(token);
    }

    @PostMapping("/tempLogin/{id}")
    @Operation(summary = "用户登录",  // 替换@ApiOperation
            description = "同时通过用户名密码创建JWT令牌"
    )
    public Result<String> tempLogin(@PathVariable Integer id) {
        log.info("用户临时登录： {}", id);

        //登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.ID, id);
        String token = JwtUtil.createJWT(
                jwtProperties.getUserSecretKey(),
                jwtProperties.getUserTtl(),
                claims);

//        UserLoginVO userLoginVO = UserLoginVO.builder()
//                .Id(user.getId())
//                .userName(user.getUserName())
//                .residentIdentityCardNumber(user.getResidentIdentityCardNumber())
//                .token(token)
//                .build();

        return Result.success(token);
    }


    /**
     * 退出
     *
     * @return
     */
    @PostMapping("/logout")
    @Operation(summary = "用户退出")
    public Result<String> logout() {
        return Result.success();
    }


    /**
     * 根据id查询用户
     * @param
     * @return
     */
    @GetMapping("/userInfo")
    @Operation(summary = "根据id查询用户")
    public Result<User> getUser() {
        Long id = BaseContext.getCurrentId();
        User user = userService.getById(id);
        return Result.success(user);
    }

    @GetMapping("/userLogInfo/{cardNumber}")
    @Operation(summary = "根据身份证号查询用户")
    public Result<User> getUserByCardNumber(@PathVariable String cardNumber) {
        User user = userService.getUserByCardNumber(cardNumber);
        return Result.success(user);
    }

    /**
     * 校验密码是否正确
     * @param
     * @return
     */
    @GetMapping("/userCheck/{originalPassword}")
    @Operation(summary = "校验密码是否正确")
    public Result<Boolean> checkUserPassword(@PathVariable String originalPassword) {
        Long id = BaseContext.getCurrentId();
        Boolean aBoolean = userService.checkUserPassword(id, originalPassword);
        return Result.success(aBoolean);
    }


    /**
     * 用户注册
     * @param userDTO
     * @return
     */
    @PostMapping("/register")
    @Operation(summary = "新增用户",
            description = "注册新用户接口")
    public Result add(@RequestBody UserDTO userDTO) {
        log.info("新增用户: {}", userDTO);
        userService.save(userDTO);
        return Result.success();
    }


    /**
     * 编辑用户信息
     * @param userDTO
     * @return
     */
    @PutMapping("/update")
    @Operation(summary = "编辑用户")
    public Result update(@RequestBody UserDTO userDTO) {
        log.info("编辑用户信息{}", userDTO);
        userService.update(userDTO);
        return Result.success();
    }

    @GetMapping("/listAll")
    @Operation(summary = "管理端用户总览")
    public Result<PageBean<User>> listAll(
            Integer pageNum,
            Integer pageSize
    ) {
        log.info("用户总览: 第{}页, 每页{}条记录", pageNum, pageSize);
        PageBean<User> pb = userService.listAll(pageNum, pageSize);
        return Result.success(pb);
    }

}
