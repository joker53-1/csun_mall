package com.csun.mall.pojo.dto;

import com.csun.mall.domain.SysPermission;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 后台权限节点封装
 */
public class SysPermissionNode extends SysPermission {
    @Getter
    @Setter
    @ApiModelProperty(value = "子级权限")
    private List<SysPermissionNode> children;
}
