package com.csun.mall.pojo.dto;

import com.csun.mall.domain.MessageRecord;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Transient;
import java.util.Date;
import java.util.List;

/**
 * @author Created by Cen xr on 2021/9/28 16:12
 */
@Data
public class MessageRecoreDTO {

    private Long id;
    @Column(name = "message_id")
    private Long messageId;
    @Column(name = "reply_user_id")
    private Long replyUserId;
    @Column(name = "reply_user_name")
    private String replyUserName;

    private String message;

    private Integer type;
    @Column(name = "send_time")
    private Date sendTime;

}
