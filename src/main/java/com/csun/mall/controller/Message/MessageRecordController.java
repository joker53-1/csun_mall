package com.csun.mall.controller.Message;

import com.csun.mall.common.tools.CookieTool;
import com.csun.mall.domain.Message;
import com.csun.mall.pojo.dto.MessageDTO;
import com.csun.mall.service.MessageRecordService;
import com.csun.mall.web.response.PageParam;
import com.csun.mall.web.response.PageResult;
import com.csun.mall.web.response.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

/**
 * @Author Joker Zheng
 * @create 2021/9/27 14:57
 */

@RestController
@RequestMapping("message")
public class MessageRecordController {

    @Autowired
    private MessageRecordService messageRecordService;

    @PostMapping("commit")
    public ResponseData commit(String name, String email, String topic, Long userId, HttpServletRequest request, HttpServletResponse response) {
        String deviceId = CookieTool.getCookieValue(request, "device_id");
        Message message = Message.builder().deviceId(deviceId).name(name).topic(topic).email(email).userId(userId).replyUserId(0L).unreadNumber(0).build();
        messageRecordService.addMessage(message);

        return ResponseData.success(message.getId());
    }

    @GetMapping("page/{messageId}")
    public ResponseData<PageResult<MessageDTO>> commit(@PathVariable Long messageId, PageParam param) {
        param.setPageSize(1000);
        PageResult<MessageDTO> page = messageRecordService.page(messageId, param);

        return ResponseData.success(page);
    }
}
