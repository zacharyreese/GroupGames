package com.groupgames.web.core;

import com.groupgames.web.game.GameLobby;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * GameManager
 *
 *
 */
public class GameManager {
    private static final int UID_SIZE = 3; // UID size in bytes. 2x bytes required due to hex encoding

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
        String lobbyID;

        do {
            // TODO: this has a possibility of hanging forever if all keys are taken
            lobbyID = GameManager.genUid(UID_SIZE);
        } while (lobbies.containsKey(lobbyID));

        // Register the new lobby with the generated ID
        lobbies.put(lobbyID, new GameLobby(lobbyID));

        // Return generated lobby ID
        return lobbyID;
    }

    /**
     * Returns the lobby associated with the given id
     *
     * @param id String id of the requested game lobby
     * @return the lobby requested, null if doesn't exist
     */
    public synchronized GameLobby getLobby(String id) {
        return lobbies.get(id);
    }

    /**
     * Close a lobby and remove it from the map of active lobbies
     *
     * @param id String id of the game lobby to close
     */
    public synchronized void closeLobby(String id) {
        lobbies.remove(id);
    }

    /**
     * Generate and return a random hex ID
     *
     * @param byteCount number of bytes to generate for the UID (Hex output will be 2x this value)
     * @return Hex string representation of each byte concatenated
     */
    public static String genUid(int byteCount){
        StringBuffer strOut = new StringBuffer();
        byte[] uidBytes = new byte[byteCount];

        new Random().nextBytes(uidBytes);

        for(byte b : uidBytes)
            strOut.append(String.format("%02X", b));

        return strOut.toString();
    }
}
