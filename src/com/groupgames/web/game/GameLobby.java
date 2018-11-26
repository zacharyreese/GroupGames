package com.groupgames.web.game;

import com.groupgames.web.core.GameManager;
import com.groupgames.web.core.Player;
import com.groupgames.web.game.view.View;
import com.groupgames.web.states.lobby.PlayerJoinState;

import javax.websocket.Session;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class GameLobby implements StateManager {
    private String gameCode;

    private State currentState;
    private Map<String, Player> users;
    private Session hostWebsocket;

    public GameLobby(String gameCode) {
        this.gameCode = gameCode;
        this.users = new HashMap<>();

        // Pass the reference to the list of connected users & gamecode to the state
        Map<String, Object> context = new HashMap<>();
        context.put(PlayerJoinState.USERS_TAG, this.users);
        context.put(PlayerJoinState.GAME_CODE_TAG, this.gameCode);

        // Start the initial state with necessary parameters
        currentState = new PlayerJoinState(this, context);
    }

    /**
     * For a given user, get the current state information to be displayed to the user. Should return an object or map
     * that is compatible with the FreeMarker template provided by getTemplatePath() as they are used in conjunction
     *
     * @param uid user ID of the user requesting a state update. Null indicates host
     */
    public synchronized View getView(String uid, String webRootPath) {
        return currentState.getView(uid, webRootPath);
    }

    /**
     * Perform an action by a user in the lobby. It is up to the implementation to verify the GameAction is valid for
     * the given game type. A null user ID represents the host executing the action
     *
     * @param uid user ID of the user performing the action. Null indicates host
     * @param action action to be performed
     */
    public synchronized void doAction(String uid, GameAction action) {
        currentState.doAction(uid, action);
    }

    /**
     * Registers a user with the given username to this lobby.
     *
     * @param uid user ID of the user to be added
     * @param name custom name entered by the user
     *
     * @return operation successful? false indicates user ID already exists
     */
    public synchronized boolean addUser(String uid, String name) {
        if (!users.containsKey(uid)) {
            Player newUser = new Player(uid, name);
            users.put(uid, newUser);
            currentState.update();
            return true;
        }
        return false;
    }

    public synchronized boolean registerWebsocket(String id, Session websocket) {
        // Null ID == host
        if (id == null){
            hostWebsocket = websocket;
        } else {
            // Attach websocket to specific player
            Player user = users.get(id);
            if (user != null) {
                user.registerWebsocket(websocket);
                return true;
            }
        }

        return false;
    }


    @Override
    public synchronized void setState(State state) {
        this.currentState = state;
    }

    @Override
    public synchronized void setUpdateRate(int millis) {

    }
}
