package com.csun.mall.pojo.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@ToString
public class MessageRO implements Serializable {

    private static final long serialVersionUID = 3544216886850149310L;

    private String messageId;

    private String message;

//    private String image;

    private Integer type;

    private Date sendTime;
}
