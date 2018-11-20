package com.groupgames.web.core;

import com.groupgames.web.game.GameLobby;

import java.util.HashMap;
import java.util.Map;

/**
 * GameManager
 *
 *
 */
public class GameManager {
    // Singleton instance
    private static GameManager gameManager;

    // Map of game lobbies to their associated ID
    private Map<String, GameLobby> lobbies;

    /**
     * Singleton GameManager instance
     * Initialize the lobby map
     */
    private GameManager(){
        lobbies = new HashMap<>();
    }

    /**
     * Get an instance of the GameManager
     * @return Singleton GameManager instance
     */
    public static synchronized GameManager getInstance(){
        if(gameManager == null){
            gameManager = new GameManager();
        }
        return gameManager;
    }

    /**
     * Generates a new lobby and returns the generated unique game ID
     *
     * @return newly generated game lobby ID
     */
    public synchronized String newLobby() {
        // Return generated lobby ID
        return "";
    }

    /**
     * Returns the lobby associated with the given id
     *
     * @param id String id of the requested game lobby
     * @return the lobby requested
     */
    public synchronized GameLobby getLobby(String id) {
        return null;
    }

    /**
     * Close a lobby and remove it from the map of active lobbies
     *
     * @param id String id of the game lobby to close
     * @return operation successful
     */
    public synchronized boolean closeLobby(String id) {
        return false;
    }
}
