package com.groupgames.web.webapp.servlets.Websockets;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.groupgames.web.core.GameManager;
import com.groupgames.web.game.GameLobby;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@ServerEndpoint("/playWS")
public class PlayEndpoint {
    //URI = "ws://localhost:8080/GroupGames_Web_exploded/playWS"
    private Map<String, String> usernames = new HashMap<String, String>();

    @OnOpen
    public void open(Session session) throws IOException {
        session.getBasicRemote().sendText("(Server): You are connected as " + ((int)(Math.random() * 10000)));
    }

    @OnMessage
    public void handleMessage(String message, Session session) throws IOException {
        Map<String, String> parsedRegisterMessage;
        try {
            parsedRegisterMessage = new Gson().fromJson(message, new HashMap<String, String>().getClass());
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        String userID = parsedRegisterMessage.get("player_id");
        String lobbyID = parsedRegisterMessage.get("gamecode");
        if (userID == null || lobbyID == null) {
            // TODO : failed to register user
            return;
        }

        GameLobby lobby = GameManager.getInstance().getLobby(lobbyID);
        if (!lobby.registerWebsocket(userID, session)) {
            // TODO : handle failed websocket register
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
