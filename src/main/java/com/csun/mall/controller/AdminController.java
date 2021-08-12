package com.csun.mall.controller;

import com.csun.mall.common.vo.CommonResult;
import com.csun.mall.dto.SysUserDTO;
import com.csun.mall.dto.SysUserLoginDTO;
import com.csun.mall.pojo.SysUser;
import com.csun.mall.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RequestMapping("admin")
@Api(tags = "AdminController", value = "后台用户管理")
@RestController
@Slf4j
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping("hello")
    public CommonResult hello(){
        return CommonResult.success("hello");
    }
    @ApiOperation(value = "用户注册")
    @PostMapping(value = "/register")
    public CommonResult<SysUser> register(@RequestBody SysUserDTO userDTO, BindingResult result) {
        SysUser sysUser = userService.register(userDTO);
        if (sysUser == null) {
            CommonResult.failed();
        }
        return CommonResult.success(sysUser);
    }

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
