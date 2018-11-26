package com.groupgames.web.states.kah;

import com.groupgames.web.core.*;
import com.groupgames.web.game.*;
import com.groupgames.web.game.view.JsonView;
import com.groupgames.web.game.view.TemplateView;
import com.groupgames.web.game.view.View;
import com.groupgames.web.states.kah.actions.SubmitAction;

import javax.websocket.Session;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

import static com.groupgames.web.states.lobby.PlayerJoinState.GAME_CODE_TAG;
import static com.groupgames.web.states.lobby.PlayerJoinState.USERS_TAG;

public class KahSubmitState extends State {
    public static final String PLAYER_HANDS_TAG = "playerHands";
    public static final int HAND_COUNT = 5;
    private static final String HOST_WS_TAG = "hostWS";
    CardManager cardManager = new CardManager();

    int countdownTimer = 15;
    private Timer timer;
    String submittedCardID;

    Map<String, List<Card>> playerHands;
    Map<String, Integer> submittedCards;
    HashMap<String, Player> usersMap;


    public KahSubmitState(StateManager manager, Map<String, Object> context) throws SQLException {
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
                if (countdownTimer > 0) {
                        update();
                        countdownTimer--;
                } else {
                    timer.cancel();
                    //manager.setState(new KahVoteState(manager, context));
                }
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

        // Handle submit view
        if(uid != null) {
            HashMap<String, Object> templateData = new HashMap<>();
            templateData.put("cards", playerHands.get(uid));
            //templateData.put("timer", countdownTimer);
            templateData.put("gamecode", this.getContext().get(GAME_CODE_TAG));
            templateData.put("uid", uid);

            try {
                view = new TemplateView(webRootPath, "playerHand.ftl", templateData);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            HashMap<String, Object> templateData = new HashMap<>();
            try {
                view = new TemplateView(webRootPath, "hostSubmission.ftl", templateData);
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
    private void topUpHands(HashMap<String, Player> users, Map<String, List<Card>> playerHands) throws SQLException {
        this.playerHands = playerHands;
        int counter = 0;
        int handsize = 0;
        for(Player player : users.values()){
            ArrayList<Card> newCards = cardManager.getWhiteCards(HAND_COUNT * users.size());
            for(int x = 0; x < 5; x++) {
                playerHands.put(player.getUserID(), newCards.subList(counter, (counter + 5)));
            }
            counter += HAND_COUNT;

           /* List<Card> playerHand = playerHands.get(player.getUserID());
            if(playerHand == null)
                playerHand = new ArrayList<Card>();

            if(playerHand.size() < HAND_COUNT) {
                ArrayList<Card> newCards = cardManager.getWhiteCards(HAND_COUNT - playerHand.size());
                playerHand.addAll(newCards);
            }*/
        }
    }

    public void getBlackCard() {

    }

    public HashMap<String, String> convertTemplate(List<Card> cards) {
        HashMap<String, String> cardList = new HashMap<String, String>();
        for(int i = 0; i < cards.size(); i++) {
            String cardID = (String) cards.get(i).getCardID(cards.get(i));
            String cardText = cards.get(i).getCardID(cards.get(i));
            cardList.put(cardID, cardText);
        }
        return cardList;
    }
}

