package com.ppd.mubler.service;

import com.ppd.mubler.model.entity.MublerRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class RequestBroadcastHandler {

    private List<WebSocketSession> webSocketSessionList = new ArrayList<>();

    public void addWebSocketSession(WebSocketSession webSocketSession){
        this.webSocketSessionList.add(webSocketSession);
    }

    public void removeWebSocketSession(WebSocketSession webSocketSession){
        this.webSocketSessionList.remove(webSocketSession);
    }

    public void broadcastRequest(MublerRequest mublerRequest){
        for (WebSocketSession webSocketSession: this.webSocketSessionList){
            try {
                webSocketSession.sendMessage(new TextMessage("REQUEST-AVAILABLE/" + mublerRequest.getId() + "/"+ mublerRequest.getPrice()+ "/" + mublerRequest.getIdRequester()+"/"+ mublerRequest.getVehiculeTypeName()
                +"/"+mublerRequest.getStartCoordX()+"#"+mublerRequest.getStartCoordY()+"#"+mublerRequest.getEndCoordX()+"#"+mublerRequest.getEndCoordY()));
            } catch (IOException e) {
                System.err.println("error sending request to session : " + webSocketSession.getId());
            }
        }
    }
}
