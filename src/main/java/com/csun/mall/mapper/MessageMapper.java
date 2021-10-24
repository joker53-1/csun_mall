package com.csun.mall.mapper;

import com.csun.mall.common.base.BaseMapper;
import com.csun.mall.domain.Message;
import com.csun.mall.pojo.dto.MessageDTO;
import com.csun.mall.pojo.dto.MessageRecoreDTO;

import java.util.List;

public interface MessageMapper extends BaseMapper<Message> {
    List<MessageDTO> page(Long replyUserId);
}