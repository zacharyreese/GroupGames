package com.groupgames.web.states.kah;

import com.groupgames.web.game.GameAction;
import com.groupgames.web.game.State;
import com.groupgames.web.game.StateManager;
import com.groupgames.web.game.view.View;

import java.util.Map;

public class KahStartState extends State {

    public KahStartState(StateManager manager, Map<String, Object> context) {
        super(manager, context);
    }

    @Override
    public void update() {

    }

    @Override
    public View getView(String uid, String webRootPath) {
        return null;
    }

    @Override
    public void doAction(String uid, GameAction action) {
ayyyy
    }
}
