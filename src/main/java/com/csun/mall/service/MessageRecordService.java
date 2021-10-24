package com.csun.mall.service;

import com.csun.mall.common.tools.CookieTool;
import com.csun.mall.domain.CsrMember;
import com.csun.mall.domain.Message;
import com.csun.mall.domain.MessageRecord;
import com.csun.mall.mapper.CsrMemberMapper;
import com.csun.mall.mapper.MessageMapper;
import com.csun.mall.mapper.MessageRecordMapper;
import com.csun.mall.pojo.dto.MessageRecoreDTO;
import com.csun.mall.pojo.dto.MessageRO;
import com.csun.mall.web.response.PageParam;
import com.csun.mall.web.response.PageResult;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;


@Slf4j
@Service
public class MessageRecordService {

    @Resource
    private MessageRecordMapper messageRecordMapper;

    @Resource
    private MessageMapper messageMapper;

    @Resource
    private CsrMemberMapper csrMemberMapper;

    public int addMessage(Message message){
        return messageMapper.insert(message);
    }

    public Message getMessage(Long id){
        return messageMapper.selectByPrimaryKey(id);
    }

    public Long addUserId(Long messageId, Long userId, HttpServletRequest request){
        if(messageId==null){
            Example example = new Example(Message.class);
            example.createCriteria().andEqualTo("userId",userId);
            Message message = messageMapper.selectOneByExample(example);
            if(ObjectUtils.isEmpty(message)){
                CsrMember csrMember = csrMemberMapper.selectByPrimaryKey(userId);
                Message newMessage = Message.builder().userId(userId).replyUserId(0L).name(csrMember.getUsername()).deviceId(CookieTool.getCookieValue(request, "device_id")).unreadNumber(0).build();
                messageMapper.insert(newMessage);
                return newMessage.getId();
            }
            else {
                return message.getId();
            }

        }

        Message message = messageMapper.selectByPrimaryKey(messageId);

        if(message.getUserId()==null){
            message.setUserId(userId);
            messageMapper.updateByPrimaryKey(message);
            return message.getId();
        }
        else if(message.getUserId().equals(userId)) {
            return message.getId();
        } else {
            CsrMember csrMember = csrMemberMapper.selectByPrimaryKey(userId);
            Message newMessage = Message.builder().userId(userId).replyUserId(0L).name(csrMember.getUsername()).deviceId(CookieTool.getCookieValue(request, "device_id")).unreadNumber(0).build();
            messageMapper.insert(newMessage);
            return newMessage.getId();
        }


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

    public PageResult<MessageRecoreDTO> page(Long messageId, PageParam param) {
        PageHelper.startPage(param.getPageNum(), param.getPageSize());
        List<MessageRecoreDTO> page = messageRecordMapper.page(messageId);
        return PageResult.from(page, MessageRecoreDTO.class);
    }

    public PageResult<MessageRecoreDTO> page(Long replyId, String messageLike, PageParam param){
        PageHelper.startPage(param.getPageNum(), param.getPageSize());
        List<MessageRecoreDTO> page = messageRecordMapper.pageAll(replyId,messageLike);
        return PageResult.from(page, MessageRecoreDTO.class);
    }

    public static void main(String[] args) {
        Message message =new Message();
        System.out.println(message.getId()==null);
    }
}
