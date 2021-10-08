package com.csun.mall.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageRecord {
    @Id
    private Long id;

    private Long messageId;

    private String message;

    private String image;

    private Integer type;

    @Column(name = "send_time")
    private Date sendTime;

    @Column(name = "reply_user_id")
    private Long replyUserId;;

}