package com.csun.mall.controller;

import com.csun.mall.domain.SysMenu;
import com.csun.mall.domain.SysRole;
import com.csun.mall.service.SysRoleService;
import com.csun.mall.web.response.PageResult;
import com.csun.mall.web.response.ResponseData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("role")
@Api(tags = "后台用户角色管理")
@RestController
@Slf4j
public class RoleController {
    @Autowired
    private SysRoleService sysRoleService;

    @ApiOperation("添加后台菜单")
    @PostMapping(value = "/create")
    public ResponseData create(@RequestBody SysRole sysRole) {
        int count = sysRoleService.create(sysRole);
        if (count > 0) {
            return ResponseData.success();
        }
        return ResponseData.failure();
    }

    @ApiOperation("修改角色")
    @PostMapping(value = "/update")
    public ResponseData update(@RequestParam Long id, @RequestBody SysRole role) {
        int count = sysRoleService.update(id, role);
        if (count > 0) {
            return ResponseData.success();
        }
        return ResponseData.failure();
    }

    @ApiOperation("批量删除角色")
    @PostMapping(value = "/delete")
    public ResponseData delete(@RequestParam("ids") List<Long> ids) {
        int count = sysRoleService.delete(ids);
        if (count > 0) {
            return ResponseData.success(count);
        }
        return ResponseData.failure();
    }


    @ApiOperation("获取所有角色")
    @GetMapping(value = "/listAll")
    //TODO 通过某一字段进行获取
    public ResponseData<List<SysRole>> listAll() {
        List<SysRole> roleList = sysRoleService.list();
        return ResponseData.success(roleList);
    }

    @ApiOperation("根据角色名称分页获取角色列表")
    @GetMapping(value = "/list")
    public ResponseData<PageResult> list(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                         @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        PageResult list = sysRoleService.list(pageNum, pageSize);
        return ResponseData.success(list);
    }

    @ApiOperation("修改角色状态")
    @PostMapping(value = "/updateStatus")
    public ResponseData updateStatus(@RequestParam Long id, @RequestParam(value = "status") Boolean enable) {
        SysRole sysRole = new SysRole();
        sysRole.setEnable(enable);
        int count = sysRoleService.update(id, sysRole);
        if (count > 0) {
            return ResponseData.success();
        }
        return ResponseData.failure();
    }

    @ApiOperation("获取角色相关菜单")
    @GetMapping(value = "/listMenu")
    public ResponseData<List<SysMenu>> listMenu(@RequestParam Long roleId) {
        List<SysMenu> roleList = sysRoleService.listMenu(roleId);
        return ResponseData.success(roleList);
    }


    @ApiOperation("给角色分配菜单")
    @PostMapping(value = "/allocMenu")
    public ResponseData allocMenu(@RequestParam Long id, @RequestParam List<Long> menuIds) {
        int count = sysRoleService.allocMenu(id, menuIds);
        if (count > 0) {
            return ResponseData.success();
        }
        return ResponseData.failure();
    }

}
