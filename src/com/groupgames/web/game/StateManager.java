package com.groupgames.web.game;

public interface StateManager {

    /**
     * Update the current state to a provided state
     *
     * @param state
     */
    void setState(State state);

    /**
     * Set the update rate of the manager. This determines the rate at which the current state's update() method
     * is called. It is up to the state manager to enforce these updates
     *
     * @param millis
     */
    void setUpdateRate(int millis);
}
