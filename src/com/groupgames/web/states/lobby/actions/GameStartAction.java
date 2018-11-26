package com.groupgames.web.states.lobby.actions;

import com.groupgames.web.game.GameAction;

public class GameStartAction extends GameAction {
    private static final String SELECTED_TAG = "selection";

    public GameStartAction(GameAction baseAction) throws IllegalArgumentException {
        super(baseAction);

        requireField(SELECTED_TAG, String.class);
    }

    public String getSelected(){
        return (String) parsed.get(SELECTED_TAG);
    }
}
