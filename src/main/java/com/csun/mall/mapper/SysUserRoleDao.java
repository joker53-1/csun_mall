package com.csun.mall.mapper;

import com.csun.mall.domain.SysPermission;
import com.csun.mall.domain.SysRole;
import org.apache.ibatis.annotations.Param;
import java.util.List;

public interface SysUserRoleDao {

    /**
     * 获取用于所有角色
     */
    List<SysRole> getRoleList(@Param("userId") Long userId);

    /**
     * 获取用户所有权限
     */
    List<SysPermission> getPermissionList(@Param("userId") Long userId);
}
