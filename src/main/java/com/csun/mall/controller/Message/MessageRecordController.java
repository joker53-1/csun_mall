package com.csun.mall.controller.Message;

import com.csun.mall.common.tools.CookieTool;
import com.csun.mall.controller.portal.interceptor.UserTokenInterceptor;
import com.csun.mall.domain.Message;
import com.csun.mall.pojo.dto.MessageRecoreDTO;
import com.csun.mall.service.MessageRecordService;
import com.csun.mall.web.response.PageParam;
import com.csun.mall.web.response.PageResult;
import com.csun.mall.web.response.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public ResponseData commit(String name, String email, String topic, Long userId, HttpServletRequest request, HttpServletResponse response) {
        String deviceId = CookieTool.getCookieValue(request, "device_id");
        Message message = Message.builder().deviceId(deviceId).name(name).topic(topic).email(email).userId(userId).replyUserId(0L).unreadNumber(0).build();
        messageRecordService.addMessage(message);

        return ResponseData.success(message.getId());
    }

    @PutMapping("adduserid")
    public ResponseData addUserId(Long messageId,Long userId,HttpServletRequest request){
        if(userId==null){
            userId = UserTokenInterceptor.userId.get();
        }
        Long res = messageRecordService.addUserId(messageId,userId,request);
        if(res==-1L){
            return ResponseData.failure();
        }
        return ResponseData.success(res);

    }



    @GetMapping("page/{messageId}")
    public ResponseData<PageResult<MessageRecoreDTO>> commit(@PathVariable Long messageId, PageParam param) {
        PageResult<MessageRecoreDTO> page = messageRecordService.page(messageId, param);

        return ResponseData.success(page);
    }
}
