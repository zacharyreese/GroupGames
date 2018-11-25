package com.groupgames.web.states.kah;

import com.groupgames.web.game.GameAction;
import com.groupgames.web.game.State;
import com.groupgames.web.game.StateManager;
import com.groupgames.web.game.view.TemplateView;
import com.groupgames.web.game.view.View;

import com.groupgames.web.states.kah.actions.QuitAction;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.groupgames.web.states.lobby.PlayerJoinState.GAME_CODE_TAG;
import static com.groupgames.web.states.lobby.PlayerJoinState.USERS_TAG;

public class KahWinnerState extends State {
    public KahWinnerState(StateManager manager, Map<String, Object> context) {
        super(manager, context);
    }

    @Override
    public void update() {

    }

    @Override
    public View getView(String uid, String webRootPath) {
        View view = null;

        // Handle winner view
        if(uid == null) {
            HashMap<String, Object> templateData = new HashMap<>();
            templateData.put("gamecode", getContext().get(GAME_CODE_TAG));
            templateData.put("users", getContext().get(USERS_TAG));

            try {
                view = new TemplateView(webRootPath,"winner.ftl", templateData);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return view;    }

    @Override
    public void doAction(String uid, GameAction action) {
        Map<String, Object> context = this.getContext();

        switch (action.getType()) {
            case "timer":
                // Handle start game action
                TimerAction timer = new TimerAction(action);
                manager.setState(new KahSubmitState(manager, context));
                break;
            case "quit":
                // Handle options change
                QuitAction quit = new QuitAction(action);
                manager.setState(new KahStartState(manager, context));

                break;
        }
    }
}
