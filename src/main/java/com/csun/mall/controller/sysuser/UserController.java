package com.csun.mall.controller.sysuser;

import com.csun.mall.common.tools.PojoConvertTool;
import com.csun.mall.domain.SysPermission;
import com.csun.mall.domain.SysRole;
import com.csun.mall.domain.SysUser;
import com.csun.mall.pojo.dto.SysUserDTO;
import com.csun.mall.service.SysUserService;
import com.csun.mall.web.response.PageParam;
import com.csun.mall.web.response.PageResult;
import com.csun.mall.web.response.ResponseData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

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
    @ApiIgnore
    public ResponseData<SysUser> register(@RequestBody SysUserDTO userDTO, BindingResult result) {
        SysUser sysUser = sysUserService.register(userDTO);
        if (sysUser == null) {
            ResponseData.failure();
        }
        return ResponseData.success(sysUser);
    }

    @ApiOperation("根据用户名、昵称或姓名分页和手机号码获取用户列表")
    @GetMapping(value = "/page")
    @PreAuthorize("hasAnyAuthority('USER','USER_LIST')")
    public ResponseData<PageResult<SysUserDTO>> page(@RequestParam(value = "keyword", required = false)
                                                     @ApiParam(value = "用户名、昵称或姓名") String keyword,
                                                     @RequestParam(value = "mobile", required = false)
                                                     @ApiParam(value = "手机号码")
                                                             String mobile,
                                                     PageParam pageParam) {
        PageResult<SysUserDTO> adminList = sysUserService.page(keyword, mobile, pageParam);
        return ResponseData.success(adminList);
    }


    @ApiOperation("获取指定用户信息")
    @GetMapping(value = "/info")
    @PreAuthorize("hasAnyAuthority('USER','USER_LIST')")
    public ResponseData<SysUser> getItem(@RequestParam Long id) {
        SysUser user = sysUserService.getItem(id);
        return ResponseData.success(user);
    }

    @ApiOperation("新增管理员")
    @PutMapping(value = "/create")
    @PreAuthorize("hasAnyAuthority('USER','USER_ADD')")
    public ResponseData<SysUserDTO> create(SysUserDTO user) {
        if (sysUserService.getUserByMobile(user.getMobile()) != null) {
            return ResponseData.failure("电话号码已存在！");
        }
        if (sysUserService.getUserByUsername(user.getUsername()) != null) {
            return ResponseData.failure("用户名已存在！");
        }
        SysUser sysUser = sysUserService.register(user);
        if (sysUser == null) {
            ResponseData.failure("新增失败");
        }
        return ResponseData.success(user);
    }

    @ApiOperation("删除指定用户信息")
    @DeleteMapping(value = "/delete")
    @PreAuthorize("hasAnyAuthority('USER','USER_DELETE')")
    public ResponseData delete(@RequestParam Long id) {
        int count = sysUserService.delete(id);
        if (count > 0) {
            return ResponseData.success("删除成功");
        }
        return ResponseData.failure();
    }

    @ApiOperation("修改指定管理员信息")
    @PostMapping(value = "/update")
    @PreAuthorize("hasAnyAuthority('USER','USER_EDIT')")
    public ResponseData<SysUserDTO> update(@RequestParam Long id, SysUserDTO user) {
        SysUser user1 = sysUserService.getUserByMobile(user.getMobile());
        if (user1 != null && user1.getId() != id) {
            return ResponseData.failure("电话号码已存在！");
        }
        SysUser user2 = sysUserService.getUserByUsername(user.getUsername());
        if (user2 != null && user2.getId() != id) {
            return ResponseData.failure("用户名已存在！");
        }

        int count = sysUserService.update(id, user);
        if (count > 0) {
            SysUserDTO sysUserDTO = PojoConvertTool.convert(sysUserService.getUserByUserId(id), SysUserDTO.class);
            sysUserDTO.setPassword(null);
            return ResponseData.success(sysUserDTO);
        }
        return ResponseData.failure();
    }

    @ApiOperation("给用户分配角色")
    @PutMapping(value = "/role/update")
    @PreAuthorize("hasAnyAuthority('USER','USER_EDIT')")
    @ApiImplicitParam(name = "roleIds",value = "roleIds",dataTypeClass = List.class, paramType = "query")
    public ResponseData updateRole(@RequestParam Long id,
                                   @RequestParam("roleIds")
                                   @ApiParam(value = "角色id列表")
                                           List<Long> roleIds) {
        int count = sysUserService.updateRole(id, roleIds);
        if (count >= 0) {
            return ResponseData.success("修改成功");
        }
        return ResponseData.failure();
    }

    @ApiOperation("获取指定用户的角色")
    @GetMapping(value = "/role")
    @PreAuthorize("hasAnyAuthority('USER','USER_LIST')")
    public ResponseData<List<SysRole>> getRoleList(@RequestParam Long id) {
        List<SysRole> roleList = sysUserService.getRoleList(id);
        return ResponseData.success(roleList);
    }


    @ApiOperation("获取用户所有权限")
    @GetMapping(value = "/permission")
    @PreAuthorize("hasAnyAuthority('USER','USER_LIST')")
    public ResponseData<List<SysPermission>> getPermissionList(@RequestParam Long id) {
        List<SysPermission> permissionList = sysUserService.getPermissionList(id);
        return ResponseData.success(permissionList);
    }
}
