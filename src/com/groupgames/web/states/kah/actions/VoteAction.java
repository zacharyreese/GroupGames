package com.groupgames.web.states.kah.actions;

import com.groupgames.web.game.GameAction;

public class VoteAction extends GameAction{
    private static final String CARD_ID = "cardID";

    public VoteAction(GameAction baseAction) throws IllegalArgumentException {
        super(baseAction);

        requireField(CARD_ID, String.class);
    }

    public String getSelected(){
        return (String) parsed.get(CARD_ID);
    }
}