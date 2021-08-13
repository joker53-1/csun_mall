package com.csun.mall.controller;

import com.csun.mall.pojo.dto.SysUserDTO;
import com.csun.mall.domain.SysUser;
import com.csun.mall.service.UserService;
import com.csun.mall.web.response.PagedGridResult;
import com.csun.mall.web.response.ResponseData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("admin")
@Api(tags = "AdminController", value = "后台用户管理")
@RestController
@Slf4j
public class AdminController {

    @Autowired
    private UserService userService;


    @GetMapping("hello")
    public ResponseData hello(){
        return ResponseData.success("hello");
    }
    @ApiOperation(value = "用户注册")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseData<SysUser> register(@RequestBody SysUserDTO userDTO, BindingResult result) {
        SysUser sysUser = userService.register(userDTO);
        if (sysUser == null) {
            ResponseData.failure();
        }
        return ResponseData.success(sysUser);
    }

    @ApiOperation("根据用户名或姓名分页获取用户列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public ResponseData<PagedGridResult> list(@RequestParam(value = "keyword", required = false) String keyword,
                                                        @RequestParam(value = "page", defaultValue = "1") Integer page,
                                                        @RequestParam(value = "pageSize", defaultValue = "1") Integer pageSize) {
        PagedGridResult adminList = userService.list(keyword, page, pageSize);
        return ResponseData.success(adminList);
    }

    @ApiOperation("获取指定用户信息")
    @GetMapping(value = "/{id}")
    @ResponseBody
    public ResponseData<SysUser> getItem(@PathVariable Long id) {
        SysUser admin = userService.getItem(id);
        return ResponseData.success(admin);
    }

    @ApiOperation("修改指定用户信息")
    @PostMapping(value = "/update/{id}")
    @ResponseBody
    public ResponseData update(@PathVariable Long id, @RequestBody SysUser admin) {
        int count = userService.update(id, admin);
        if (count > 0) {
            return ResponseData.success(count);
        }
        return ResponseData.failure();
    }

//    @ApiOperation(value = "登出功能")
//    @PostMapping(value = "/logout")
//    @ResponseBody
//    public ResponseData logout() {
//        return ResponseData.success(null);
//    }
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
