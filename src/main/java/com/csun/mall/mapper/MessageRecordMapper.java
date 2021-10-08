package com.csun.mall.mapper;

import com.csun.mall.common.base.BaseMapper;
import com.csun.mall.domain.MessageRecord;
import com.csun.mall.pojo.dto.MessageDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MessageRecordMapper extends BaseMapper<MessageRecord> {

    List<MessageDTO> page(@Param("messageId") Long messageId);

}