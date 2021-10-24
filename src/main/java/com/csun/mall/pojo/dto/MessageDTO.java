package com.csun.mall.pojo.dto;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @Author Joker Zheng
 * @create 2021/10/24 14:49
 */
@Data
public class MessageDTO {
    private Long id;
    private String deviceId;

    private String email;

    private String topic;

    private Long userId;

    private String userName;

    private Long replyUserId;

    private String replyUserName;

    private Integer unreadNumber;

    private String lastMessageRecord;


}
