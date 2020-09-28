package com.ppd.mubler.controllers.websocket.config;

import com.ppd.mubler.controllers.websocket.DialogHandler;
import com.ppd.mubler.controllers.websocket.MublerPositionHandler;
import com.ppd.mubler.controllers.websocket.RequestHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(mublerPositionHandler(), "/mublerPosition").setAllowedOrigins("*");
        registry.addHandler(requestHanlder(), "/requests").setAllowedOrigins("*");
        registry.addHandler(dialogHanlder(), "/dialog").setAllowedOrigins("*");
    }

    @Bean
    public WebSocketHandler mublerPositionHandler() {
        return new MublerPositionHandler();
    }

    @Bean
    public WebSocketHandler requestHanlder() {
        return new RequestHandler();
    }

    @Bean
    public WebSocketHandler dialogHanlder() {
        return new DialogHandler();
    }
}
