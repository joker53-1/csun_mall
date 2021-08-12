package com.csun.mall.controller;

import com.csun.mall.pojo.dto.SysUserDTO;
import com.csun.mall.domain.SysUser;
import com.csun.mall.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RequestMapping("admin")
@Api(tags = "AdminController", value = "后台用户管理")
@RestController
@Slf4j
public class AdminController {

    @Autowired
    private UserService userService;


//    @ApiOperation(value = "登录以后返回token")
//    @PostMapping(value = "/login")
//    public CommonResult login(@RequestBody SysUserLoginDTO sysUserLoginDTO, BindingResult result) {
//        String token = userService.login(sysUserLoginDTO.getUsername(), sysUserLoginDTO.getPassword());
//        if (token == null) {
//            return CommonResult.validateFailed("用户名或密码错误");
//        }
//        Map<String, String> tokenMap = new HashMap<>();
//        tokenMap.put("Authorization", token);
//        return CommonResult.success(tokenMap);
//    }
}
