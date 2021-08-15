package com.csun.mall.pojo.dto;

import lombok.*;

import javax.persistence.Column;
import java.util.Date;

/**
 * @author Created by Cen xr on 2021/6/24 13:26
 */
@Setter
@Getter
public class UserTokenDTO {

    private Long id;

    private Boolean enable;

    private String username;

    private String name;

    private String mobile;

    private String nickName;

    private String icon;

    private Integer sort;

    private Date createTime;

    private Long deviceId;

    private String token;


}
