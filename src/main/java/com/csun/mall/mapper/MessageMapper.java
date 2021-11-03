package com.csun.mall.mapper;

import com.csun.mall.common.base.BaseMapper;
import com.csun.mall.domain.Message;
import com.csun.mall.pojo.dto.MessageDTO;
import com.csun.mall.pojo.dto.MessageRecoreDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MessageMapper extends BaseMapper<Message> {
    List<MessageDTO> page(@Param("replyUserId") Long replyUserId, @Param("isUnRead") boolean isUnRead);
}