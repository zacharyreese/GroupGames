package com.groupgames.web.states.kah.actions;

import com.groupgames.web.game.GameAction;

public class SubmitAction extends GameAction {
    private static final String CARD_ID_TAG = "cardID";

    public SubmitAction(GameAction baseAction) throws IllegalArgumentException {
        super(baseAction);
        requireField(CARD_ID_TAG, String.class);

    }

    public Integer getSelected(){
        String rawId = (String) parsed.get(CARD_ID_TAG);
        Double id = null;

        try {
            id = Double.valueOf(rawId);
        } catch (NumberFormatException e){
            e.printStackTrace();
        }

        return id.intValue();
    }
}