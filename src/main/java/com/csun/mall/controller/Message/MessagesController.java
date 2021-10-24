package com.csun.mall.controller.Message;

import com.csun.mall.pojo.dto.MessageDTO;
import com.csun.mall.pojo.dto.MessageRO;
import com.csun.mall.pojo.dto.MessageRecoreDTO;
import com.csun.mall.service.MessageService;
import com.csun.mall.web.response.PageParam;
import com.csun.mall.web.response.PageResult;
import com.csun.mall.web.response.ResponseData;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


@RestController
public class MessagesController {

    //    private final SimpMessageSendingOperations simpMessageSendingOperations;
//
//    public MessagesController(SimpMessageSendingOperations simpMessageSendingOperations) {
//        this.simpMessageSendingOperations = simpMessageSendingOperations;
//    }
//
//    @MessageMapping("/message")
//    public String handleShout(CeegMessage ceegMessage) {
//        simpMessageSendingOperations.convertAndSendToUser(ceegMessage.getDevice(), "/message", ceegMessage.getMessage());
//        return null;
//    }
//
//    @MessageMapping("/subscribe")
//    @SendTo("/topic/subscribe")
//    public String handleSubscribe(MessageRO messageRO) {
//        return messageRO.getMessage();
//    }
//
//    // 消息异常
//    @MessageExceptionHandler(Exception.class)
//    @SendToUser("/queue/errors")
//    public Exception handleExceptions(Exception t) {
//        t.printStackTrace();
//        return t;
//    }

    @Resource
    private MessageService messageService;

    /**
     * 发送给指定人
     *
     * @param messageRO 消息请求对象
     * @throws Exception
     */
    @MessageMapping("/chat")
    public void sendToUser(MessageRO messageRO) throws Exception {
        messageService.sendMessageToUser(messageRO);
    }

    @GetMapping("page")
    public ResponseData<PageResult<MessageDTO>> page(Long replyId, PageParam param){

        PageResult<MessageDTO> page = messageService.page(replyId, param);
        return ResponseData.success(page);

    }


    @PutMapping("change")
    public ResponseData change(Long messageId,Long replyUserId){
        if(messageService.changeServiceId(messageId,replyUserId)>0){
            return ResponseData.success();
        }
        else
        {
            return ResponseData.failure();
        }
    }
}

