package com.example.spring.config;



import com.example.spring.interceptor.WebsocketInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

public class WebSocketConfig implements WebSocketConfigurer {

    @Autowired
    private com.example.websocket.handler.WebsocketHandler websocketHandler;
    @Autowired
    private WebsocketInterceptor websocketInterceptor;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(websocketHandler, "/ws").setAllowedOrigins("*").addInterceptors(websocketInterceptor);
    }

}
