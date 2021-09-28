package com.csun.mall.controller.Message;

import com.csun.mall.common.tools.CookieTool;
import com.csun.mall.domain.CsrMember;
import com.csun.mall.pojo.dto.MessageRO;
import com.csun.mall.pojo.dto.MessageVO;
import com.csun.mall.service.MessageService;
import org.springframework.http.HttpRequest;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


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
    public String handleSubscribe(MessageRO  messageRO) {
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
     * 聊天室发布订阅
     *
     * @param messageRO 消息请求对象
     * @param user 发送消息的用户对象
     * @throws Exception
     */
//    @MessageMapping("/chatRoom")
//    public void chatRoom(MessageRO messageRO, CsrMember user) throws Exception {
//        String message = messageRO.getMessage();
//
//        messageService.sendMessage("/topic/chatRoom", new MessageVO(user, message, messageRO.getImage()));
//    }

    /**
     * 发送消息到指定用户
     *
     * @param messageRO 消息请求对象
     * @param user 发送消息的用户对象
     * @throws Exception
     */
    @MessageMapping("/chat")
    public void sendToUser(MessageRO messageRO, CsrMember user) throws Exception {
        messageService.sendMessageToUser(messageRO.getReceiver(), new MessageVO(user, messageRO.getMessage(),
                messageRO.getImage(),messageRO.getType()));
    }

}

