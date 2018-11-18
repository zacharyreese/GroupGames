package com.groupgames.web.core;

public class Player {
    String userID;
    String username;
    String gameCode;

    public Player(String userID, String username, String gameCode) {
        this.userID = userID;
        this.username = username;
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

}
