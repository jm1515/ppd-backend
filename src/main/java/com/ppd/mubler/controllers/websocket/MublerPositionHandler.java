package com.ppd.mubler.controllers.websocket;

import com.ppd.mubler.model.entity.User;
import com.ppd.mubler.service.SecurityTokenService;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;

public class MublerPositionHandler extends TextWebSocketHandler {

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message){
        String securityToken = message.getPayload().split("@")[0];
        User user = SecurityTokenService.getUserFromToken(securityToken);
        if (user != null){
            try {
                session.sendMessage(new TextMessage("updating position of user : " + user.getFirstName()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            try {
                session.sendMessage(new TextMessage("bad token"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
