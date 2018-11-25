package com.groupgames.web.states.kah;

import com.groupgames.web.core.Player;
import com.groupgames.web.game.GameAction;
import com.groupgames.web.game.State;
import com.groupgames.web.game.StateManager;
import com.groupgames.web.game.view.JsonView;
import com.groupgames.web.game.view.TemplateView;
import com.groupgames.web.game.view.View;
import com.groupgames.web.states.lobby.actions.VoteAction;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import static com.groupgames.web.states.lobby.PlayerJoinState.GAME_CODE_TAG;
import static com.groupgames.web.states.lobby.PlayerJoinState.USERS_TAG;

public class KahVoteState extends State {
    GameAction countdownConstruct = new GameAction("{ \"type\": \"timer\", \"time\": \"10\" }");
    GameAction countdown = new GameAction(countdownConstruct);
    Integer countdownTimer = 10;
    private static Timer timer;

    public KahVoteState(StateManager manager, Map<String, Object> context) {
        super(manager, context);
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                synchronized(countdownTimer) {
                    if (countdownTimer == 0) {
                        timer.cancel();
                        manager.setState(new KahVoteState(manager, context));
                    }
                    countdownTimer--;
                    update();
                }
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

        // Handle vote view
        if(uid == null) {
            HashMap<String, Object> templateData = new HashMap<>();
            templateData.put("gamecode", getContext().get(GAME_CODE_TAG));
            templateData.put("users", getContext().get(USERS_TAG));

            try {
                view = new TemplateView(webRootPath,"vote.ftl", templateData);
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
            case "vote":
                // Handles submit action
                VoteAction voteAction = new VoteAction(action);
                manager.setState(new KahWinnerState(manager, context));
                break;

        }
    }

}
