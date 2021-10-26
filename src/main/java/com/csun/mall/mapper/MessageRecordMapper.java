package com.csun.mall.mapper;

import com.csun.mall.common.base.BaseMapper;
import com.csun.mall.domain.MessageRecord;
import com.csun.mall.pojo.dto.MessageRecoreDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MessageRecordMapper extends BaseMapper<MessageRecord> {

    List<MessageRecoreDTO> page(@Param("messageId") Long messageId);

//    List<MessageRecoreDTO> pageAll(@Param("replyUserId") Long replyId, @Param("messageLike") String messageLike);

}