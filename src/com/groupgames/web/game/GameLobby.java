package com.groupgames.web.game;

/**
 *
 */
public abstract class GameLobby {
    private static final int DEFAULT_UPDATE_RATE = 1000;

    /**
     * For a given user, get the current state information to be displayed to the user. Should return an object or map
     * that is compatible with the FreeMarker template provided by getTemplatePath() as they are used in conjunction
     *
     * @param uid user ID of the user requesting a state update. Null indicates host
     */
    public abstract void getState(String uid);

    /**
     * Perform an action by a user in the lobby. It is up to the implementation to verify the GameAction is valid for
     * the given game type. A null user ID represents the host executing the action
     *
     * @param uid user ID of the user performing the action. Null indicates host
     * @param action action to be performed
     */
    public abstract void doAction(String uid, GameAction action);

    /**
     * Update is called at the interval defined by the getUpdateRate() method. Should be used for updating
     * state information in the background such as countdown timers
     */
    public abstract void update();

    /**
     * Registers a user with the given username to this lobby.
     *
     * @param uid user ID of the user to be added
     * @param name custom name entered by the user
     */
    public void addUser(String uid, String name){
        // TODO: Implement user registration
    }

    /**
     * Get the desired update rate of the lobby. Should be overridden to modify the default value of 1 second.
     *
     * @return desired update rate in milliseconds
     */
    public int getUpdateRate(){
        return DEFAULT_UPDATE_RATE;
    }

    /**
     * Uses the user ID to lookup the associated view for the user. Returns a corresponding FreeMarker template
     * path for a given view. By default, returns a template informing the user the game lobby has no view defined.
     * Should be overridden to provide custom views for each game.
     *
     * @param uid user ID of the user requesting a view
     * @return template path relative to the template root
     */
    public String getTemplatePath(String uid) {
        // TODO: Create and return default ftl template file path
        return "";
    }
}
