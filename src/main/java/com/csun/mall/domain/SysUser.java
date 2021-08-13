package com.csun.mall.domain;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "sys_user")
public class SysUser {
    /**
     * 主键ID
     */
    @Id
    @ApiModelProperty(value = "用户id", required = false)
    private Long id;

    /**
     * 是否启用（1：启用，0：冻结）
     */
    @ApiModelProperty(value = "是否启用", required = false)
    private Boolean enable;

    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名", required = true)
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 姓名
     */
    @ApiModelProperty(value = "姓名", required = true)
    private String name;

    /**
     * 电话号码
     */
    @ApiModelProperty(value = "电话号码", required = true)
    private String moblie;

    /**
     * 昵称
     */
    @Column(name = "nick_name")
    @ApiModelProperty(value = "昵称", required = true)
    private String nickName;

    /**
     * 头像
     */
    @ApiModelProperty(value = "头像", required = false)
    private String icon;

    /**
     * 排序
     */
    @ApiModelProperty(value = "排序", required = false)
    private Integer sort;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    @ApiModelProperty(value = "创建时间", required = false)
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
     * 获取用户名
     *
     * @return username - 用户名
     */
    public String getUsername() {
        return username;
    }

    /**
     * 设置用户名
     *
     * @param username 用户名
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 获取密码
     *
     * @return password - 密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置密码
     *
     * @param password 密码
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取姓名
     *
     * @return name - 姓名
     */
    public String getName() {
        return name;
    }

    /**
     * 设置姓名
     *
     * @param name 姓名
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取电话号码
     *
     * @return moblie - 电话号码
     */
    public String getMoblie() {
        return moblie;
    }

    /**
     * 设置电话号码
     *
     * @param moblie 电话号码
     */
    public void setMoblie(String moblie) {
        this.moblie = moblie;
    }

    /**
     * 获取昵称
     *
     * @return nick_name - 昵称
     */
    public String getNickName() {
        return nickName;
    }

    /**
     * 设置昵称
     *
     * @param nickName 昵称
     */
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    /**
     * 获取头像
     *
     * @return icon - 头像
     */
    public String getIcon() {
        return icon;
    }

    /**
     * 设置头像
     *
     * @param icon 头像
     */
    public void setIcon(String icon) {
        this.icon = icon;
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