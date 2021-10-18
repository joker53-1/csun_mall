package com.csun.mall.service;

import com.csun.mall.domain.Message;
import com.csun.mall.domain.MessageRecord;
import com.csun.mall.domain.ProductCategory;
import com.csun.mall.mapper.MessageMapper;
import com.csun.mall.mapper.MessageRecordMapper;
import com.csun.mall.pojo.dto.MessageDTO;
import com.csun.mall.pojo.dto.MessageRO;
import com.csun.mall.web.response.PageParam;
import com.csun.mall.web.response.PageResult;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;


@Slf4j
@Service
public class MessageRecordService {

    @Resource
    private MessageRecordMapper messageRecordMapper;

    @Resource
    private MessageMapper messageMapper;

    public int addMessage(Message message){
        return messageMapper.insert(message);
    }

    public void addRecord(MessageRecord message) {
        messageRecordMapper.insert(message);
    }

    public void addRecord(MessageRO messageRO, Message message) {
        MessageRecord messageRecord = new MessageRecord();
        messageRecord.setMessageId(message.getId());
        messageRecord.setMessage(messageRO.getMessage());
        messageRecord.setType(messageRO.getType());
        messageRecord.setSendTime(messageRO.getSendTime());
        messageRecord.setReplyUserId(message.getReplyUserId());
        messageRecordMapper.insert(messageRecord);
    }

    public PageResult<MessageDTO> page(Long messageId, PageParam param) {
        PageHelper.startPage(param.getPageNum(), param.getPageSize());
        List<MessageDTO> page = messageRecordMapper.page(messageId);
        return PageResult.from(page, MessageDTO.class);
    }

}
