package com.groupgames.web.states.kah.actions;

import com.groupgames.web.game.GameAction;

public class BeginGameAction extends GameAction {
    private static final String SELECTED_TAG = "selected";

    public BeginGameAction(GameAction baseAction) throws IllegalArgumentException {
        super(baseAction);

        requireField(SELECTED_TAG, String.class);
    }

    public String getSelected(){
        return (String) parsed.get(SELECTED_TAG);
    }
}