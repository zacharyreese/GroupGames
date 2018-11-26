package com.groupgames.web.states.kah;

import com.groupgames.web.core.*;
import com.groupgames.web.game.*;
import com.groupgames.web.game.view.TemplateView;
import com.groupgames.web.game.view.View;
import com.groupgames.web.states.kah.actions.SubmitAction;

import java.io.IOException;
import java.util.*;

import static com.groupgames.web.states.lobby.PlayerJoinState.GAME_CODE_TAG;
import static com.groupgames.web.states.lobby.PlayerJoinState.USERS_TAG;

public class KahSubmitState extends State {
    public static final String PLAYER_HANDS_TAG = "playerHands";
    public static final int HAND_COUNT = 5;

    Integer countdownTimer = 10;
    private static Timer timer;
    String submittedCardID;

    Map<String, List<Card>> playerHands;
    Map<String, Integer> submittedCards;
    HashMap<String, Player> usersMap;


    public KahSubmitState(StateManager manager, Map<String, Object> context) {
        super(manager, context);
        usersMap = (HashMap<String, Player>)getContext().get(USERS_TAG);
        playerHands = (Map<String, List<Card>>) context.get(PLAYER_HANDS_TAG);

        if (playerHands == null) {
            playerHands = new HashMap<String, List<Card>>();
            context.put(PLAYER_HANDS_TAG, playerHands);
        }
        topUpHands(usersMap, playerHands);

        submittedCards = new HashMap<>();

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

        // Handle submit view
        if(uid == null) {
            HashMap<String, Object> templateData = new HashMap<>();
            templateData.put("cards", playerHands.get(uid));
            templateData.put("timer", countdownTimer);

            try {
                view = new TemplateView(webRootPath,"playerHand.ftl", templateData);
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
                // Update the user's submitted card with the new card
                SubmitAction submitAction = new SubmitAction(action);
                submittedCards.put("submittedCard", submitAction.getSelected());

                // If all votes are accounted for, transition states
                if(submittedCards.size() == usersMap.size()) {
                    // attach the list of cards to the context before starting new state
                    manager.setState(new KahVoteState(manager, context));
                }
                break;
        }
    }

    /**
     * Ensure all players have a hand with HAND_COUNT # of cards. If any users dont have a hand, initialize one
     * and fill it. If they are missing cards, top their hand up with random cards
     *
     * @param users
     * @param playerHands
     */
    private void topUpHands(HashMap<String, Player> users, Map<String, List<Card>> playerHands){

        for(Player player : users.values()){
            List<Card> playerHand = playerHands.get(player.getUserID());
            if(playerHand == null)
                playerHand = new ArrayList<Card>();

            if(playerHand.size() < HAND_COUNT) {
                List<Card> newCards = CardManager.getWhiteCards(HAND_COUNT - playerHand.size());
                playerHand.addAll(newCards);
            }
        }
    }
}
