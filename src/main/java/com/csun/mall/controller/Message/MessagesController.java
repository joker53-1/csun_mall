package com.csun.mall.controller.Message;

import com.csun.mall.pojo.dto.MessageRO;
import com.csun.mall.service.MessageService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
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
    @MessageMapping("/subscribe")
    @SendTo("/topic/subscribe")
    public String handleSubscribe(MessageRO messageRO) {
        System.out.println(messageRO.getMessage());
        return messageRO.getMessage();
    }
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

}

