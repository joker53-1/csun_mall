package com.csun.mall.service;

import com.csun.mall.pojo.dto.MessageVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Created by Cen xr on 2021/7/29 8:50
 */
@Service
public class MessageService {

    @Resource
    private SimpMessagingTemplate messagingTemplate;

    public void sendMessage(String subAddress, MessageVO messageVO) throws Exception{
        if (StringUtils.isEmpty(subAddress)) {
            throw new NullPointerException("地址为空！");
        }

        messagingTemplate.convertAndSend(subAddress, messageVO);
    }

    public void sendMessageToUser(String receiver, MessageVO messageVO) throws Exception{
        if (StringUtils.isEmpty(receiver)) {
            throw new NullPointerException("地址为空！");
        }

        messagingTemplate.convertAndSendToUser(receiver, "/chat", messageVO);

    }


}
