package com.csun.mall.pojo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;

@Getter
@Setter
public class SysRoleDTO {
    @ApiModelProperty(required = false)
    private Long id;

    /**
     * 是否启用（1：启用，0：冻结）
     */
    @ApiModelProperty(value = "是否启用", required = false)
    private Boolean enable;

    /**
     * 名称
     */
    @ApiModelProperty(value = "名称", required = true)
    private String name;

    /**
     * 角色描述
     */
    @ApiModelProperty(value = "角色描述", required = true)
    private String description;

    /**
     * 后台用户数量
     */
    @ApiModelProperty(value = "后台用户数量", required = false)
    private Integer userCount;

    /**
     * 排序
     */
    @ApiModelProperty(value = "排序", required = false)
    private Integer sort;


}
