package com.groupgames.web.states.kah;

import com.groupgames.web.game.GameAction;
import com.groupgames.web.game.State;
import com.groupgames.web.game.StateManager;
import com.groupgames.web.game.view.TemplateView;
import com.groupgames.web.game.view.View;
import com.groupgames.web.states.lobby.actions.BeginGameAction;
import com.groupgames.web.states.lobby.actions.SubmitAction;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.groupgames.web.states.lobby.PlayerJoinState.GAME_CODE_TAG;
import static com.groupgames.web.states.lobby.PlayerJoinState.USERS_TAG;

public class KahSubmitState extends State {
    public KahSubmitState(StateManager manager, Map<String, Object> context) {
        super(manager, context);
    }

    @Override
    public void update() {

    }

    @Override
    public View getView(String uid, String webRootPath) {
        View view = null;

        // Handle submit view
        if(uid == null) {
            HashMap<String, Object> templateData = new HashMap<>();
            templateData.put("gamecode", getContext().get(GAME_CODE_TAG));
            templateData.put("users", getContext().get(USERS_TAG));

            try {
                view = new TemplateView(webRootPath,"submit.ftl", templateData);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return view;
    }

    @Override
    public void doAction(String uid, GameAction action) {
        Map<String, Object> context = this.getContext();

        switch (action.getType()) {
            case "submit":
                // Handles submit action
                SubmitAction submitAction = new SubmitAction(action);
                manager.setState(new KahVoteState(manager, context));
                break;

        }
    }

}
