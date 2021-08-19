package com.csun.mall.controller;

import com.csun.mall.domain.SysPermission;
import com.csun.mall.pojo.dto.SysPermissionNode;
import com.csun.mall.service.SysPermissionService;
import com.csun.mall.web.response.ResponseData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(tags = "后台用户权限管理")
@RequestMapping("/permission")
public class PermissionController {
    @Autowired
    private SysPermissionService sysPermissionService;

    @ApiOperation("添加权限")
    @PutMapping(value = "/create")
    @PreAuthorize("hasAnyAuthority('USER','USER_ADD')")
    public ResponseData create(SysPermission permission) {
        int count = sysPermissionService.create(permission);
        if (count > 0) {
            return ResponseData.success("添加成功");
        }
        return ResponseData.failure();
    }

    @ApiOperation("修改权限")
    @PostMapping(value = "/update")
    @PreAuthorize("hasAnyAuthority('USER','USER_EDIT')")
    public ResponseData update(@RequestParam Long id, SysPermission permission) {
        int count = sysPermissionService.update(id, permission);
        if (count > 0) {
            return ResponseData.success("修改成功");
        }
        return ResponseData.failure();
    }

    @ApiOperation("根据id批量删除权限")
    @DeleteMapping(value = "/delete")
    @PreAuthorize("hasAnyAuthority('USER','USER_DELETE')")
    @ApiImplicitParam(name = "ids",value = "ids",dataTypeClass = List.class, paramType = "query")
    public ResponseData delete(@RequestParam("ids") List<Long> ids) {
        int count = sysPermissionService.delete(ids);
        if (count > 0) {
            return ResponseData.success("删除成功");
        }
        return ResponseData.failure();
    }

    @ApiOperation("以层级结构返回所有权限")
    @GetMapping(value = "/treeList")
    @PreAuthorize("hasAnyAuthority('USER','USER_LIST')")
    public ResponseData<List<SysPermissionNode>> treeList() {
        List<SysPermissionNode> permissionNodeList = sysPermissionService.treeList();
        return ResponseData.success(permissionNodeList);
    }

    @ApiOperation("获取所有权限列表")
    @GetMapping(value = "/list")
    @PreAuthorize("hasAnyAuthority('USER','USER_LIST')")
    public ResponseData<List<SysPermission>> list() {
        List<SysPermission> permissionList = sysPermissionService.list();
        return ResponseData.success(permissionList);
    }
}
