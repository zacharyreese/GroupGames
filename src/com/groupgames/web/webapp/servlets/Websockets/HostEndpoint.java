package com.groupgames.web.webapp.servlets.Websockets;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@ServerEndpoint("/hostWS")
public class HostEndpoint {
    //URI = "ws://localhost:8080/GroupGames_Web_exploded/hostWS"
    private Map<String, String> usernames = new HashMap<String, String>();

    @OnOpen
    public void open(Session session) throws IOException {
        session.getBasicRemote().sendText("(Server): You are connected");
    }

    @OnMessage
    public void handleMessage(String message, Session session) throws IOException {
        String userID = session.getId();
        if(usernames.containsKey(userID)) {
            String username = usernames.get(userID);
                for(Session peer : session.getOpenSessions()) {
                    peer.getBasicRemote().sendText(message); //Sent message will be command for servlet
        }
        } else {
            usernames.put(userID, message);
            session.getBasicRemote().sendText("(" + message + "): has connected!"); //String for your client connection
            for (Session peer : session.getOpenSessions()) {
                if(!peer.getId().equals(userID)) {
                    peer.getBasicRemote().sendText("(" + message + "): has connected!"); //String for others client connection
                }
            }
        }
    }

    @OnClose
    public void close(Session session) throws IOException {
        String userID = session.getId();
        if(usernames.containsKey(userID)) {
            String username = usernames.get(userID);
            usernames.remove(userID);
            for(Session peer : session.getOpenSessions()) {
                peer.getBasicRemote().sendText("(Server): " + username + " has disconnected");
            }
        }
    }
}
