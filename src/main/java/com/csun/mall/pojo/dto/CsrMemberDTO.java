package com.csun.mall.pojo.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import java.util.Date;

/**
 * @Author Joker Zheng
 * @create 2021/8/23 10:41
 */
@Setter
@Getter
public class CsrMemberDTO {
    private Long id;

    private Integer enable;

    private String username;

    private String password;

    private String email;

    private String nickname;

    private String phone;

    private Date createTime;

    private Date updateTime;

    private String icon;

    private Integer gender;

    private Date birthday;

    private String city;

    private String job;

    private String personalizedSignature;
}
