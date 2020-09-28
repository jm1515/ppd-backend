package com.ppd.mubler.controllers.websocket;

import com.ppd.mubler.model.entity.log4j.MublerLogger;
import com.ppd.mubler.service.DialogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;

public class DialogHandler extends TextWebSocketHandler {

    @Autowired
    private DialogService dialogService;

    private MublerLogger mublerLogger = MublerLogger.getInstance();

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
        mublerLogger.logInfo("RECIEVED MESSAGE (dialog): " + message);
        String msg = message.getPayload();
        mublerLogger.logInfo("PAYLOAD = " + msg);
        if (msg.contains("SUB")) {
            mublerLogger.logInfo("SUBING USER");
            dialogService.addWebSocketSession(session, Long.valueOf(msg.split("/")[1]));
        }
        if (msg.contains("UNSUB")) {
            dialogService.removeWebSocketSession(Long.valueOf(msg.split("/")[1]));

        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
    }
}
