package com.groupgames.web.states.kah.actions;

import com.groupgames.web.game.GameAction;

public class SubmitAction extends GameAction {
    private static final String PLAYER_UID_TAG = "uid";

    public SubmitAction(GameAction baseAction) throws IllegalArgumentException {
        super(baseAction);
        requireField(PLAYER_UID_TAG, String.class);

    }

    public String getSelected(){
        return (String) parsed.get(PLAYER_UID_TAG);
    }
}