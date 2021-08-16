package com.csun.mall.mapper;

import com.csun.mall.domain.SysMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;
public interface SysRoleDao {
    /**
     * 根据后台用户ID获取菜单
     */
    List<SysMenu> getMenuList(@Param("adminId") Long adminId);
    /**
     * 根据角色ID获取菜单
     */
    List<SysMenu> getMenuListByRoleId(@Param("roleId") Long roleId);
}
