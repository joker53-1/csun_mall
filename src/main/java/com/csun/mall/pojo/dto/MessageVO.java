package com.csun.mall.pojo.dto;

import com.csun.mall.domain.CsrMember;
import lombok.Data;

import java.util.Date;

/**
 * @Author Joker Zheng
 * @create 2021/8/30 10:08
 */
@Data
public class MessageVO {

    /**
     * 用户
     */
    private CsrMember user;
    /**
     * 消息信息
     */
    private String message;
    /**
     * 图片
     */
    private String image;
    /**
     * 消息id
     */
    private String messageId;
    /**
     * 发送时间
     */
    private Date sendTime;

    /**
     * 接收者
     */
    private String receiver;

    private Integer type;

    public MessageVO(CsrMember user, String message, String image,Integer type) {
        this.user = user;
        this.message = message;
        this.image = image;
        this.type = type;
    }

    public MessageVO(CsrMember user, String message, String image,String receiver) {
        this.user = user;
        this.message = message;
        this.image = image;
        this.receiver = receiver;
    }
}
