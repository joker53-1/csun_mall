package com.csun.mall.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Setter
@Getter
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

}