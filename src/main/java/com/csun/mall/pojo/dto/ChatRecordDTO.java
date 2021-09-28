package com.csun.mall.pojo.dto;

import com.csun.mall.domain.CsrMember;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 * 聊天记录数据传输层
 *
 * @author yanpanyi
 * @date 2019/4/4
 */
@Getter
@Setter
@ToString
public class ChatRecordDTO {

    private String deviceId;
    /**
     * 用户信息
     */
    private CsrMember user;
    /**
     * 消息
     */
    private String message;
    /**
     * 图片
     */
    private String image;
    /**
     * 消息类型
     */
    private Integer type;
    /**
     * 发送时间
     */
    private Date sendTime;


}
