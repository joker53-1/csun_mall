package com.csun.mall.controller;

import com.csun.mall.domain.SysRole;
import com.csun.mall.domain.SysUser;
import com.csun.mall.pojo.dto.SysUserDTO;
import com.csun.mall.service.SysUserService;
import com.csun.mall.web.response.PageParam;
import com.csun.mall.web.response.PageResult;
import com.csun.mall.web.response.ResponseData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RequestMapping("user")
@Api(tags = "后台用户管理")
@RestController
@Slf4j
public class UserController {

    @Autowired
    private SysUserService sysUserService;


    //    @GetMapping("hello")
//    public ResponseData hello(){
//        return ResponseData.success("hello");
//    }
    @ApiOperation(value = "用户注册")
    @PostMapping(value = "/register")
    public ResponseData<SysUser> register(SysUserDTO userDTO, BindingResult result) {
        SysUser sysUser = sysUserService.register(userDTO);
        if (sysUser == null) {
            ResponseData.failure();
        }
        return ResponseData.success(sysUser);
    }

    @ApiOperation("根据用户名、昵称或姓名分页和手机号码获取用户列表")
    @GetMapping(value = "/page")
    public ResponseData<PageResult<SysUserDTO>> page(@RequestParam(value = "keyword", required = false)
                                                                  @ApiParam(value = "用户名、昵称或姓名") String keyword,
                                                     @RequestParam(value = "mobile", required = false)
                                                     @ApiParam(value = "手机号码")
                                                             String mobile,
                                                     PageParam pageParam) {
        PageResult<SysUserDTO> adminList = sysUserService.page(keyword,mobile,pageParam);
        return ResponseData.success(adminList);
    }


    @ApiOperation("获取指定用户信息")
    @GetMapping(value = "/info")
    public ResponseData<SysUser> getItem(@RequestParam Long id) {
        SysUser user = sysUserService.getItem(id);
        return ResponseData.success(user);
    }

    @ApiOperation("修改指定用户信息")
    @PutMapping(value = "/update")
    public ResponseData update(@RequestParam Long id, SysUser user) {
        int count = sysUserService.update(id, user);
        if (count > 0) {
            return ResponseData.success(sysUserService.getUserByUserId(id));
        }
        return ResponseData.failure();
    }

    @ApiOperation("给用户分配角色")
    @PutMapping(value = "/role/update")
    public ResponseData updateRole(@RequestParam Long id,
                                   @RequestParam("roleIds")
                                   @ApiParam(value = "角色id列表")
                                           List<Long> roleIds) {
        int count = sysUserService.updateRole(id, roleIds);
        if (count >= 0) {
            return ResponseData.success();
        }
        return ResponseData.failure();
    }

    @ApiOperation("获取指定用户的角色")
    @GetMapping(value = "/role")
    public ResponseData<List<SysRole>> getRoleList(@RequestParam Long id) {
//        List<SysRole> roleList = sysUserService.getRoleList(adminId);
//        return ResponseData.success(roleList);
        return ResponseData.success();
    }
}
