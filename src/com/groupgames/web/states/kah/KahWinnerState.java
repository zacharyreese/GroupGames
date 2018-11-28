package com.groupgames.web.states.kah;

import com.groupgames.web.core.Card;
import com.groupgames.web.core.Player;
import com.groupgames.web.game.GameAction;
import com.groupgames.web.game.State;
import com.groupgames.web.game.StateManager;
import com.groupgames.web.game.view.TemplateView;
import com.groupgames.web.game.view.View;
import com.groupgames.web.states.kah.actions.QuitAction;
import com.groupgames.web.states.lobby.PlayerJoinState;
import com.groupgames.web.states.lobby.actions.GameStartAction;

import java.io.IOException;
import java.util.*;


public class KahWinnerState extends State {
    public static final String WINNING_CARD_TAG = "winningCard";
    public static final String WINNER_TAG = "winnerTag";

    private HashMap<String, Player> usersMap;
    private Card winningCard;
    private Player winner;

    private int countdownTimer;
    private Timer timer;

    public KahWinnerState(StateManager manager, Map<String, Object> context) {
        super(manager, context);

        usersMap = (HashMap<String, Player>) getContext().get(USERS_TAG);
        winner = (Player) getContext().get(WINNER_TAG);
        winningCard = (Card) getContext().get(WINNING_CARD_TAG);
        if (winningCard == null) {
            winningCard = new Card(-1, "No card was chosen as the winner :(");
        }

        countdownTimer = 10;

        // Start the countdown timer
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                if (countdownTimer > 0) {
                    countdownTimer--;
                } else {
                    cancel();
                    manager.setState(new KahSubmitState(manager, context));
                    broadcastRefresh();
                }
                update();
            }
        }, 100, 1000);
    }

    @Override
    public void update() {
        HashMap<String, Object> JSONData = new HashMap<>();
        JSONData.put("timer", countdownTimer);
        broadcast(JSONData);
    }

    @Override
    public View getView(String uid, String webRootPath) {
        View view = null;

        HashMap<String, Object> templateData = new HashMap<>();
        // Add generic template fields to the data map
        templateData.put("timer", countdownTimer);
        templateData.put("gamecode", this.getContext().get(GAME_CODE_TAG));
        templateData.put("uid", uid);

        templateData.put("winnerCardText", winningCard.getCardText());

        // Add host/player specific fields and set the corresponding ftl files
        try {
            if(uid == null){
                // Handle host view
                view = new TemplateView(webRootPath, "hostWinView.ftl", templateData);
            } else {
                // Handle host view
                view = new TemplateView(webRootPath, "playerWinView.ftl", templateData);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return view;
    }

    @Override
    public void doAction(String uid, GameAction action) {
        Map<String, Object> context = this.getContext();

        switch (action.getType()) {
            case "quit":
                // Handle options change
                QuitAction quit = new QuitAction(action);
                for(String userID : usersMap.keySet()){
                    kickPlayer(userID);
                }
                break;
            case "navigation":
                GameStartAction navAction = new GameStartAction(action);
                if (navAction.getSelected().equalsIgnoreCase("lobby")) {
                    // Handle options change
                    timer.cancel();
                    timer = null;
                    manager.setState(new PlayerJoinState(manager, getContext()));
                    for(String userID : usersMap.keySet()){
                        kickPlayer(userID);
                    }
                }
                break;
        }
    }
}
