package com.groupgames.web.game;

/**
 *
 */
public class GameLobby implements StateManager {
    private State currentState;

    /**
     * For a given user, get the current state information to be displayed to the user. Should return an object or map
     * that is compatible with the FreeMarker template provided by getTemplatePath() as they are used in conjunction
     *
     * @param uid user ID of the user requesting a state update. Null indicates host
     */
    public synchronized View getView(String uid) {
        return currentState.getView(uid);
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
     */
    public synchronized void addUser(String uid, String name) {
        // TODO: Implement user registration
    }


    @Override
    public synchronized void setState(State state) {
        this.currentState = state;
    }

    @Override
    public synchronized void setUpdateRate(int millis) {

    }
}
