package com.csun.mall.mapper;

import com.csun.mall.domain.SysRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysUserRoleDao {

    /**
     * 获取用于所有角色
     */
    List<SysRole> getRoleList(@Param("adminId") Long adminId);
}
