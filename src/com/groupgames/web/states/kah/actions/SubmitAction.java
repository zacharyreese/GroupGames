package com.groupgames.web.states.kah.actions;

import com.groupgames.web.game.GameAction;

public class SubmitAction extends GameAction {
    private static final String CARD_ID_TAG = "cardID";

    public SubmitAction(GameAction baseAction) throws IllegalArgumentException {
        super(baseAction);
        requireField(CARD_ID_TAG, Integer.class);

    }

    public Integer getSelected(){
        return (Integer) parsed.get(CARD_ID_TAG);
    }
}