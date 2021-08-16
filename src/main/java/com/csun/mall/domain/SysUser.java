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
    private Long id;

    /**
     * 是否启用（1：启用，0：冻结）
     */
    private Boolean enable;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 姓名
     */
    private String name;

    /**
     * 电话号码
     */
    private String mobile;

    /**
     * 昵称
     */
    @Column(name = "nick_name")
    private String nickName;

    /**
     * 头像
     */
    private String icon;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

}