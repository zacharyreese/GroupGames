package com.groupgames.web.states.kah;

import com.groupgames.web.core.GameManager;
import com.groupgames.web.core.Player;
import com.groupgames.web.game.GameAction;
import com.groupgames.web.game.State;
import com.groupgames.web.game.StateManager;
import com.groupgames.web.game.view.JsonView;
import com.groupgames.web.game.view.TemplateView;
import com.groupgames.web.game.view.View;
import com.groupgames.web.states.kah.actions.BeginGameAction;


import javax.websocket.Session;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import static com.groupgames.web.states.lobby.PlayerJoinState.GAME_CODE_TAG;
import static com.groupgames.web.states.lobby.PlayerJoinState.USERS_TAG;

public class KahStartState extends State {

    private int countdownTimer;
    private Timer timer;

    HashMap<String, Object> templateData = new HashMap<>();

    public KahStartState(StateManager manager, Map<String, Object> context) {
        super(manager, context);

        countdownTimer = 5;

        //forceRefresh();
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                if (countdownTimer > 0) {
                    countdownTimer--;
                } else {
                    timer.cancel();
                    manager.setState(new KahSubmitState(manager, context));
                }
            }
        }, 100, 1000);
    }

    @Override
    public void update() {

    }

    @Override
    public View getView(String uid, String webRootPath) {
        View view = null;

        // Handle start view
        HashMap<String, Object> templateData = new HashMap<>();
        templateData.put("gamecode", getContext().get(GAME_CODE_TAG));
        templateData.put("uid", uid);

        try {
            view = new TemplateView(webRootPath,"startgame.ftl", templateData);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return view;
    }

    @Override
    public void doAction(String uid, GameAction action) {
        Map<String, Object> context = this.getContext();

        switch (action.getType()) {
            case "begin":
                // Handles begin game action
                timer.cancel();
                timer = null;
                BeginGameAction beginGameAction = new BeginGameAction(action);
                manager.setState(new KahSubmitState(manager, context));
                break;

        }
    }

    private void forceRefresh() {
        HashMap<String, Object> JSONData = new HashMap<>();
        JSONData.put("method", "refresh");
        JsonView json = new JsonView(JSONData);
        String refreshCommand = json.toString();
        for(Player p : usersMap.values()) {
            p.writeUpdate(refreshCommand);
        }
        writeUpdate(refreshCommand);
    }

}
