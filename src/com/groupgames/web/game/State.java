package com.groupgames.web.game;

import com.groupgames.web.game.view.View;

import javax.websocket.Session;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public abstract class State {
    public static final String HOST_WS_TAG = "hostWS";

    protected StateManager manager;
    private Map<String, Object> context;

    /**
     * A State must be constructed with a reference to a StateManager so that the state may
     * conduct state changes
     *
     * @param manager StateManager for the state to use for switching states
     */
    public State(StateManager manager){
        this.manager = manager;
    }

    /**
     * Construct a state with a preexisting context from another state. Copies the state's context
     * into a new reference before use
     *
     * @param manager
     * @param context
     */
    public State(StateManager manager, Map<String, Object> context) {
        // Call the base constructor before adding functionality
        this(manager);

        if (context != null) {
            // Copy the existing context to prevent editing the existing one
            this.context = new HashMap<>(context);
        }
    }

    public void setWebsocket(Session peer) {
        this.getContext().put(HOST_WS_TAG, peer);
    }

    public boolean writeUpdate(String updateText) {
        Session websocket = (Session) getContext().get(HOST_WS_TAG);
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

    /**
     * Return the current state's context
     *
     * @return map of context variables
     */
    public Map<String, Object> getContext() {
        return context;
    }

     /**
      * Should be used for updating context information in the background such as countdown timers
      *
      */
    public abstract void update();

    /**
     * Uses the user ID to generate the associated view for the user.
     *
     * @param uid user ID performing the action
     * @return current View for the respective used
     */
    public abstract View getView(String uid, String webRootPath);

    /**
     * Perform an action by a user in the lobby. It is up to the implementation to verify the GameAction is valid for
     * the given game type. A null user ID represents the host executing the action
     *
     * @param uid user ID performing the action
     * @param action action to be performed
     */
    public abstract void doAction(String uid, GameAction action);
}
