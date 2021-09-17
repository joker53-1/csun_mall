package com.csun.mall.domain;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;

@Data
@Builder
public class Contact {
    @Id
    private Long id;

    private String name;

    private String email;

    private String company;

    private String phone;

    private String message;

    @Column(name = "create_time")
    private Date createTime;


}