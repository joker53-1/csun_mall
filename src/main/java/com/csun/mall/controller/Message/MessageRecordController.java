package com.csun.mall.controller.Message;

import com.csun.mall.common.tools.CookieTool;
import com.csun.mall.domain.Message;
import com.csun.mall.service.MessageRecordService;
import com.csun.mall.web.response.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
    public ResponseData commit(String name, String email, String topic, Long userId,HttpServletRequest request, HttpServletResponse response){
        String deviceId = CookieTool.getCookieValue(request,"device_id");
        Message message = Message.builder().deviceId(deviceId).name(name).topic(topic).email(email).userId(userId).build();
        messageRecordService.addMessage(message);
        return ResponseData.success(message.getId());
    }
}
