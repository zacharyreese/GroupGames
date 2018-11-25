package com.groupgames.web.states.kah.actions;

import com.groupgames.web.game.GameAction;

public class TimerAction extends GameAction{

    private static final String TIME = "time";

    public TimerAction(GameAction baseAction) throws IllegalArgumentException {
        super(baseAction);

        requireField(TIME, String.class);
    }

    public String getSelected(){
        return (String) parsed.get(TIME);
    }
}
