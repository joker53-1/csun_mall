package com.csun.mall.controller;

import com.csun.mall.domain.SysRole;
import com.csun.mall.pojo.dto.SysUserDTO;
import com.csun.mall.domain.SysUser;
import com.csun.mall.service.SysUserService;
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
@Api(tags = "后台用户管理")
@RestController
@Slf4j
public class AdminController {

    @Autowired
    private SysUserService sysUserService;


//    @GetMapping("hello")
//    public ResponseData hello(){
//        return ResponseData.success("hello");
//    }
    @ApiOperation(value = "用户注册")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseData<SysUser> register(@RequestBody SysUserDTO userDTO, BindingResult result) {
        SysUser sysUser = sysUserService.register(userDTO);
        if (sysUser == null) {
            ResponseData.failure();
        }
        return ResponseData.success(sysUser);
    }

    @ApiOperation("根据用户名或姓名分页获取用户列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseData<PagedGridResult> list(@RequestParam(value = "keyword", required = false) String keyword,
                                              @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                              @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        PagedGridResult adminList = sysUserService.list(keyword, pageNum, pageSize);
        return ResponseData.success(adminList);
    }

    @ApiOperation("获取指定用户信息")
    @GetMapping(value = "/getUserInfo")
    public ResponseData<SysUser> getItem(@RequestParam("adminId") Long id) {
        SysUser admin = sysUserService.getItem(id);
        return ResponseData.success(admin);
    }

    @ApiOperation("修改指定用户信息")
    @PostMapping(value = "/update")
    public ResponseData update(@RequestParam("adminId") Long id, @RequestBody SysUser admin) {
        int count = sysUserService.update(id, admin);
        if (count > 0) {
            return ResponseData.success(sysUserService.getAdminByUserId(id));
        }
        return ResponseData.failure();
    }

    @ApiOperation("给用户分配角色")
    @PostMapping(value = "/role/update")
    public ResponseData updateRole(@RequestParam("adminId") Long adminId,
                                   @RequestParam("roleIds") List<Long> roleIds) {
        int count = sysUserService.updateRole(adminId, roleIds);
        if (count >= 0) {
            return ResponseData.success();
        }
        return ResponseData.failure();
    }

    @ApiOperation("获取指定用户的角色")
    @GetMapping(value = "/role")
    public ResponseData<List<SysRole>> getRoleList(@RequestParam("adminId") Long adminId) {
        List<SysRole> roleList = sysUserService.getRoleList(adminId);
        return ResponseData.success(roleList);
    }
}
