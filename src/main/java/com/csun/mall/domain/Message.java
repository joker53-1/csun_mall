package com.csun.mall.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "device_id")
    private String deviceId;

    private String name;

    private String email;

    private String topic;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "reply_user_id")
    private Long replyUserId;

    @Column(name = "unread_number")
    private Integer unreadNumber;


}