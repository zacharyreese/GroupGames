package com.groupgames.web.core;

import java.util.ArrayList;

public class Host {
    String lobbyID;
    String gameCode;
    ArrayList<Player> currentUsers;

    public Host(String lobbyID, String gameCode){
        this.lobbyID = lobbyID;
        this.gameCode = gameCode;
        currentUsers = new ArrayList<>();
    }

    public String getLobbyID() {
        return this.lobbyID;
    }

    public String getGameCode() {
        return this.gameCode;
    }

    public void addUser(Player player) {
        currentUsers.add(player);
    }
}
