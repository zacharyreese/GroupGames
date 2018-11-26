package com.groupgames.web.states.kah;

import com.groupgames.web.core.Player;
import com.groupgames.web.game.GameAction;
import com.groupgames.web.game.State;
import com.groupgames.web.game.StateManager;
import com.groupgames.web.game.view.JsonView;
import com.groupgames.web.game.view.TemplateView;
import com.groupgames.web.game.view.View;
import com.groupgames.web.states.kah.actions.QuitAction;
import com.groupgames.web.states.lobby.PlayerJoinState;
import com.groupgames.web.states.kah.actions.QuitAction;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import static com.groupgames.web.states.lobby.PlayerJoinState.GAME_CODE_TAG;
import static com.groupgames.web.states.lobby.PlayerJoinState.USERS_TAG;

public class KahWinnerState extends State {
    int countdownTimer = 10;
    private static Timer timer;

    public KahWinnerState(StateManager manager, Map<String, Object> context) {
        super(manager, context);
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                    if (countdownTimer == 0) {
                        timer.cancel();
                        manager.setState(new KahStartState(manager, context));
                    }
                    update();
                    countdownTimer--;
            }
        }, 0, 1000);
    }

    @Override
    public void update() {
        HashMap<String, Object> JSONData = new HashMap<>();
        JSONData.put("timer", countdownTimer);
        JsonView json = new JsonView(JSONData);
        String timerUpdate = json.toString();
        HashMap<String, Player> usersMap = (HashMap<String, Player>)getContext().get(USERS_TAG);
        for(Player p : usersMap.values()) {
            p.writeUpdate(timerUpdate);
        }
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
            case "quit":
                // Handle options change
                QuitAction quit = new QuitAction(action);
                manager.setState(new PlayerJoinState(manager, context));

                break;
        }
    }
}
