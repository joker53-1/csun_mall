package com.csun.mall.service;

import com.csun.mall.domain.Message;
import com.csun.mall.mapper.MessageMapper;
import com.csun.mall.pojo.dto.MessageDTO;
import com.csun.mall.pojo.dto.MessageRO;
import com.csun.mall.pojo.dto.MessageRecoreDTO;
import com.csun.mall.pojo.dto.MessageVO;
import com.csun.mall.web.response.PageParam;
import com.csun.mall.web.response.PageResult;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author Created by Cen xr on 2021/7/29 8:50
 */
@Service
public class MessageService {

    @Resource
    private SimpMessagingTemplate messagingTemplate;

    @Resource
    private MessageMapper messageMapper;

    @Resource
    private MessageRecordService messageRecordService;

    public void sendMessage(String subAddress, MessageVO messageVO) throws Exception {
        if (StringUtils.isEmpty(subAddress)) {
            throw new NullPointerException("地址为空！");
        }
        messagingTemplate.convertAndSend(subAddress, messageVO);
    }

    public void sendMessageToUser(MessageRO messageRO) throws Exception {
        if (StringUtils.isEmpty(messageRO.getMessageId())) {
            throw new NullPointerException("对话加载失败");
        }
//        messageRO.setSendTime(new Date());
        Message message = messageMapper.selectByPrimaryKey(messageRO.getMessageId());
        messageRecordService.addRecord(messageRO, message);
        if (messageRO.getType().equals(0)) {
            messagingTemplate.convertAndSendToUser("k" + message.getReplyUserId().toString(), "/chat", messageRO);
        } else{
            messagingTemplate.convertAndSendToUser("u" + message.getReplyUserId().toString(), "/chat", messageRO);
        }
    }


    public PageResult<MessageDTO> page(Long replyId, boolean isUnRead, PageParam param) {
        return PageResult.from(param,()->messageMapper.page(replyId,isUnRead));
    }

    public int changeServiceId(Long messageId,Long serviceId){
        Message message = messageMapper.selectByPrimaryKey(messageId);
        Message newMessage = new Message();
        newMessage.setId(message.getId());
        newMessage.setReplyUserId(serviceId);
        return messageMapper.updateByPrimaryKeySelective(newMessage);
    }
}
