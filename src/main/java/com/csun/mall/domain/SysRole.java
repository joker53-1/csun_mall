package com.csun.mall.domain;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "sys_role")
public class SysRole {
    /**
     * 主键ID
     */
    @Id
    private Long id;

    /**
     * 是否启用（1：启用，0：冻结）
     */
    private Boolean enable;

    /**
     * 名称
     */
    private String name;

    /**
     * 角色描述
     */
    private String description;

    /**
     * 后台用户数量
     */
    @Column(name = "user_count")
    private Integer userCount;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

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
     * 获取是否启用（1：启用，0：冻结）
     *
     * @return enable - 是否启用（1：启用，0：冻结）
     */
    public Boolean getEnable() {
        return enable;
    }

    /**
     * 设置是否启用（1：启用，0：冻结）
     *
     * @param enable 是否启用（1：启用，0：冻结）
     */
    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    /**
     * 获取名称
     *
     * @return name - 名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置名称
     *
     * @param name 名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取角色描述
     *
     * @return description - 角色描述
     */
    public String getDescription() {
        return description;
    }

    /**
     * 设置角色描述
     *
     * @param description 角色描述
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 获取后台用户数量
     *
     * @return user_count - 后台用户数量
     */
    public Integer getUserCount() {
        return userCount;
    }

    /**
     * 设置后台用户数量
     *
     * @param userCount 后台用户数量
     */
    public void setUserCount(Integer userCount) {
        this.userCount = userCount;
    }

    /**
     * 获取排序
     *
     * @return sort - 排序
     */
    public Integer getSort() {
        return sort;
    }

    /**
     * 设置排序
     *
     * @param sort 排序
     */
    public void setSort(Integer sort) {
        this.sort = sort;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}