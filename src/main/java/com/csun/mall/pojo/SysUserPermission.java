package com.csun.mall.pojo;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "sys_user_permission")
public class SysUserPermission {
    /**
     * 主键ID
     */
    @Id
    private Long id;

    /**
     * 后台用户ID
     */
    @Column(name = "user_id")
    private Long userId;

    /**
     * 权限ID
     */
    @Column(name = "permission_id")
    private Long permissionId;

    /**
     * 获取主键ID
     *
     * @return id - 主键ID
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置主键ID
     *
     * @param id 主键ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取后台用户ID
     *
     * @return user_id - 后台用户ID
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * 设置后台用户ID
     *
     * @param userId 后台用户ID
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 获取权限ID
     *
     * @return permission_id - 权限ID
     */
    public Long getPermissionId() {
        return permissionId;
    }

    /**
     * 设置权限ID
     *
     * @param permissionId 权限ID
     */
    public void setPermissionId(Long permissionId) {
        this.permissionId = permissionId;
    }
}