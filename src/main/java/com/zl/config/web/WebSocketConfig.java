package com.zl.config.web;

import com.zl.websocket.handle.SimpleWebSocketHandler;
import com.zl.websocket.interceptor.SimpleWebSocketHandlerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;


/**
 * 开启WebSocket支持
 * @author zl
 */
@Configuration
public class WebSocketConfig implements WebSocketConfigurer {


    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        webSocketHandlerRegistry.addHandler(alterationLiveWebSocketHandler(), "/chat")
                .setAllowedOrigins("*")
                .addInterceptors(new SimpleWebSocketHandlerInterceptor());
    }

    @Bean
    public WebSocketHandler alterationLiveWebSocketHandler() {
        return new SimpleWebSocketHandler();
    }

}
