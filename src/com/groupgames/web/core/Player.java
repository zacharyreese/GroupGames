package com.groupgames.web.core;

import javax.websocket.Session;
import java.io.IOException;

public class Player {
    public String userID = String.valueOf((int)(Math.random() * 1000000));
    String username;
    String gameCode;
    Session websocket;

    public Player(String username, String gameCode) {
        this.gameCode = gameCode;
        this.username = username;
    }

    public Player(String userID, String username, String gameCode) {
        this(userID, username);
        this.gameCode = gameCode;
    }

    public String getUserID(){
        return this.userID;
    }

    public String getUsername() {
        return this.username;
    }

    public String getGameCode() {
        return this.gameCode;
    }

    public void registerWebsocket(Session websocket) {
        this.websocket = websocket;
    }

    public boolean writeUpdate(String updateText) {

        if (websocket != null) {
            try {
                websocket.getBasicRemote().sendText(updateText);
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }

        // Failed to write update. User hasn't registered websocket connection yet
        return false;
    }
}
