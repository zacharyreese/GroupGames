package com.groupgames.web.states.lobby.actions;

import com.groupgames.web.game.GameAction;

public class KickAction extends GameAction {
    private static final String PLAYER_UID_TAG = "uid";

    public KickAction(GameAction baseAction) throws IllegalArgumentException {
        super(baseAction);

        requireField(PLAYER_UID_TAG, String.class);
    }

    public String getPlayerUid(){
        return (String) parsed.get(PLAYER_UID_TAG);
    }
}
