package com.ppd.mubler.controllers.websocket;

import com.ppd.mubler.model.entity.log4j.MublerLogger;
import com.ppd.mubler.service.MublerRequestService;
import com.ppd.mubler.service.RequestBroadcastHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;

public class RequestHandler extends TextWebSocketHandler {

    @Autowired
    private RequestBroadcastHandler requestBroadcastHandler;
    @Autowired
    private MublerRequestService mublerRequestService;

    private MublerLogger mublerLogger = MublerLogger.getInstance();


    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message){
        mublerLogger.logInfo("RECIEVED MESSAGE : " + message);
        try {
            String msg = message.getPayload();
            if (msg.contains("ACCEPTED")){
                mublerLogger.logInfo("REQUEST ACCEPTED WITH MSG : " + msg);
                long idMubler = Long.valueOf(msg.split("/")[1]);
                long idRequest = Long.valueOf(msg.split("/")[2]);
                mublerRequestService.acceptRequest(idMubler, idRequest);
            }
            if (msg.contains("END")) {
                mublerLogger.logInfo("REQUEST ENDED WITH MSG : " + msg);
                long idRequest = Long.valueOf(msg.split("/")[1]);
                mublerRequestService.endRequest(idRequest);
            }
            if (msg.contains("CANCEL")) {
                mublerLogger.logInfo("REQUEST CANCELED WITH MSG : " + msg);
                long idRequest = Long.valueOf(msg.split("/")[1]);
                mublerRequestService.endRequest(idRequest);
            }
            session.sendMessage(new TextMessage("OK"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        requestBroadcastHandler.addWebSocketSession(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        requestBroadcastHandler.removeWebSocketSession(session);
    }
}
