package com.csun.mall.mapper;

import com.csun.mall.domain.SysPermission;
import com.csun.mall.domain.SysRolePermission;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 自定义角色权限关系管理Dao
 */
public interface SysRolePermissionDao {
    /**
     * 批量插入角色和权限关系
     */

    int insertList(@Param("list") List<SysRolePermission> list);

    /**
     * 根据角色获取权限
     */
    List<SysPermission> getPermissionList(@Param("roleId") Long roleId);
}
