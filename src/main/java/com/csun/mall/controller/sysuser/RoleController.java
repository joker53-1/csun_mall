package com.csun.mall.controller.sysuser;

import com.csun.mall.domain.SysMenu;
import com.csun.mall.domain.SysPermission;
import com.csun.mall.domain.SysRole;
import com.csun.mall.pojo.dto.SysRoleDTO;
import com.csun.mall.service.SysRoleService;
import com.csun.mall.web.response.PageResult;
import com.csun.mall.web.response.ResponseData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@RequestMapping("role")
@Api(tags = "后台用户角色管理")
@RestController
@Slf4j
public class RoleController {
    @Autowired
    private SysRoleService sysRoleService;

    @ApiOperation("添加角色")
    @PostMapping(value = "/create")
    @PreAuthorize("hasAnyAuthority('USER','USER_ADD')")
    public ResponseData create(SysRole sysRole) {
        int count = sysRoleService.create(sysRole);
        if (count > 0) {
            return ResponseData.success();
        }
        return ResponseData.failure();
    }

    @ApiOperation("修改角色")
    @PutMapping(value = "/update")
    @PreAuthorize("hasAnyAuthority('USER','USER_EDIT')")
    public ResponseData update(@RequestParam Long id, SysRole role) {
        int count = sysRoleService.update(id, role);
        if (count > 0) {
            return ResponseData.success();
        }
        return ResponseData.failure();
    }

    @ApiOperation("批量删除角色")
    @DeleteMapping(value = "/delete")
    @PreAuthorize("hasAnyAuthority('USER','USER_DELETE')")
    public ResponseData delete(@RequestParam("ids") List<Long> ids) {
        int count = sysRoleService.delete(ids);
        if (count > 0) {
            return ResponseData.success(count);
        }
        return ResponseData.failure();
    }


    @ApiOperation("获取所有角色")
    @GetMapping(value = "/listall")
    @ApiIgnore
    //TODO 通过某一字段进行获取
    public ResponseData<List<SysRole>> listAll() {
        List<SysRole> roleList = sysRoleService.list();
        return ResponseData.success(roleList);
    }

    @ApiOperation("根据角色名称分页获取角色列表")
    @GetMapping(value = "/page")
    @PreAuthorize("hasAnyAuthority('USER','USER_LIST')")
    public ResponseData<PageResult<SysRoleDTO>> page(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                     @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize) {
        PageResult<SysRoleDTO> list = sysRoleService.page(pageNum, pageSize);
        return ResponseData.success(list);
    }

    @ApiOperation("修改角色状态")
    @PutMapping(value = "/updateenable")
    @PreAuthorize("hasAnyAuthority('USER','USER_EDIT')")
    public ResponseData updateEnable(@RequestParam Long id, @RequestParam(value = "enable") Boolean enable) {
        SysRole sysRole = new SysRole();
        sysRole.setEnable(enable);

        return sysRoleService.update(id,sysRole)>0?ResponseData.success():ResponseData.failure();
    }

//    @ApiOperation("获取角色相关菜单")
//    @GetMapping(value = "/listmenu")
//    public ResponseData<List<SysMenu>> listMenu(@RequestParam Long roleId) {
//        List<SysMenu> roleList = sysRoleService.listMenu(roleId);
//        return ResponseData.success(roleList);
//    }


//    @ApiOperation("给角色分配菜单")
//    @PostMapping(value = "/allocmenu")
//    public ResponseData allocMenu(@RequestParam Long id, @RequestParam List<Long> menuIds) {
//        int count = sysRoleService.allocMenu(id, menuIds);
//        if (count > 0) {
//            return ResponseData.success();
//        }
//        return ResponseData.failure();
//    }

    @ApiOperation("获取相应角色权限")
    @GetMapping(value = "/permission")
    @PreAuthorize("hasAnyAuthority('USER','USER_LIST')")
    public ResponseData<List<SysPermission>> getPermissionList(@RequestParam Long id) {
        List<SysPermission> permissionList = sysRoleService.getPermissionList(id);
        return ResponseData.success(permissionList);
    }

    @ApiOperation("修改角色权限")
    @PutMapping(value = "/permission/update")
    @PreAuthorize("hasAnyAuthority('USER','USER_EDIT')")
    @ApiImplicitParam(name = "permissionIds",value = "permissionIds",dataTypeClass = List.class, paramType = "query")
    public ResponseData updatePermission(@RequestParam Long id,
                                         @RequestParam("permissionIds") List<Long> permissionIds) {
        int count = sysRoleService.updatePermission(id, permissionIds);
        if (count > 0) {
            return ResponseData.success("修改成功");
        }
        return ResponseData.failure();
    }

}
