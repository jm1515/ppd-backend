package com.ppd.mubler.service;

import com.ppd.mubler.model.entity.MublerRequest;
import com.ppd.mubler.model.entity.log4j.MublerLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class DialogService {
    @Autowired
    private MublerRequestService mublerRequestService;

    private Map<Long, WebSocketSession> webSocketSessionList = new HashMap();

    private MublerLogger mublerLogger = MublerLogger.getInstance();

    public void addWebSocketSession(WebSocketSession webSocketSession, long id){
        mublerLogger.logInfo("Adding session to dialogservice : " + id);
        this.webSocketSessionList.put(id, webSocketSession);
    }

    public void removeWebSocketSession(long id){
        this.webSocketSessionList.remove(id);
    }

    public void sendMessage(long id, String message){
        try {
            this.webSocketSessionList.get(id).sendMessage(new TextMessage(message));
        } catch (IOException e) {
            System.err.println("error sending message to session : " + id);
        }
    }

    public void endRequest(long idRequest){
        mublerRequestService.endRequest(idRequest);
    }

}
