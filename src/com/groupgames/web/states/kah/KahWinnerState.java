package com.groupgames.web.states.kah;

import com.groupgames.web.core.Card;
import com.groupgames.web.core.Player;
import com.groupgames.web.core.PlayerHand;
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
import java.util.*;

import static com.groupgames.web.states.lobby.PlayerJoinState.GAME_CODE_TAG;
import static com.groupgames.web.states.lobby.PlayerJoinState.USERS_TAG;

public class KahWinnerState extends State {
    public static final String WINNING_CARD_TAG = "winningCard";
    public static final String WINNER_TAG = "winnerTag";

    private HashMap<String, Player> usersMap;
    private Card winningCard;
    private Player winner;

    private int countdownTimer = 10;
    private static Timer timer;

    public KahWinnerState(StateManager manager, Map<String, Object> context) {
        super(manager, context);

        usersMap = (HashMap<String, Player>) getContext().get(USERS_TAG);
        winner = (Player) getContext().get(WINNER_TAG);
        winningCard = (Card) getContext().get(WINNING_CARD_TAG);

        // Start the countdown timer
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                if (countdownTimer > 0) {
                    countdownTimer--;
                } else {
                    //transitionWinState();
                }
                update();
            }
        }, 5000, 1000);
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
        writeUpdate(timerUpdate);
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
                manager.setState(new PlayerJoinState(manager, context));

                break;
        }
    }
}
