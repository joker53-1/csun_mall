package com.csun.mall.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/messages").setAllowedOriginPatterns("*").withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        //表明在topic、queue、user这三个域上可以向客户端发消息。
        config.enableSimpleBroker("/topic","/queue","/user");
        //客户端向服务端发起请求时，需要以/app为前缀。
        config.setApplicationDestinationPrefixes("/app");
        //给指定用户发送一对一的消息前缀是/user/。
        config.setUserDestinationPrefix("/user/");
    }
}
