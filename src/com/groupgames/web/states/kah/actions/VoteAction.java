package com.groupgames.web.states.kah.actions;

import com.groupgames.web.game.GameAction;

public class VoteAction extends GameAction{
    private static final String CARD_ID = "ID";


    public VoteAction(GameAction baseAction) throws IllegalArgumentException {
        super(baseAction);

        requireField(CARD_ID, Integer.class);
    }

    public Integer getSelected(){
        return (Integer) parsed.get(CARD_ID);
    }
}


