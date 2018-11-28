package com.groupgames.web.states.kah;

import com.groupgames.web.core.*;
import com.groupgames.web.game.*;
import com.groupgames.web.game.view.JsonView;
import com.groupgames.web.game.view.TemplateView;
import com.groupgames.web.game.view.View;
import com.groupgames.web.states.kah.actions.CardSubmitAction;

import java.io.IOException;
import java.util.*;

import static com.groupgames.web.states.lobby.PlayerJoinState.GAME_CODE_TAG;
import static com.groupgames.web.states.lobby.PlayerJoinState.USERS_TAG;

public class KahSubmitState extends State {
    public static final String PLAYER_HANDS_TAG = "playerHands";

    private Integer countdownTimer;
    private Timer timer;

    private Map<String, PlayerHand> playerHands;
    private Map<String, Card> submittedCards;
    private HashMap<String, Player> usersMap;
    private Card blackCard;

    public KahSubmitState(StateManager manager, Map<String, Object> context) {
        super(manager, context);
        usersMap = (HashMap<String, Player>) context.get(USERS_TAG);
        playerHands = (Map<String, PlayerHand>) context.get(PLAYER_HANDS_TAG);

        // Instantiate the player hands if not stored in the context yet
        if (playerHands == null) {
            playerHands = new HashMap<>();
            context.put(PLAYER_HANDS_TAG, playerHands);
        }
        topUpHands(usersMap, playerHands);

        // Retrieve the black card for this round
        blackCard = CardManager.getRandBlackCard();
        if(blackCard == null){
            // TODO: FAILED TO FETCH BLACK CARD
        }
        submittedCards = new HashMap<>();

        countdownTimer = 30;
        // Start the countdown timer
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                if (countdownTimer > 0) {
                        countdownTimer--;
                } else {
                    transitionVoteState();
                    cancel();
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

        // Add host/player specific fields and set the corresponding ftl files
        try {
            if(uid == null) {
                // Handle host view
                templateData.put("hostCardText", blackCard.getCardText());

                view = new TemplateView(webRootPath, "hostSubmission.ftl", templateData);
            } else {
                // Handle user view
                PlayerHand hand = playerHands.get(uid);
                if (hand != null)
                    templateData.put("cards", hand.asMap());

                view = new TemplateView(webRootPath, "playerHand.ftl", templateData);
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
            case "cardSubmit":
                // Update the user's submitted card with the new card
                CardSubmitAction cardSubmitAction = new CardSubmitAction(action);

                // Ensure the player owns the card they're trying to play
                PlayerHand pHand = playerHands.get(uid);
                if(pHand == null || !pHand.hasCard(cardSubmitAction.getCardId())){
                    // TODO: Handle failure. User does not own the card they're trying to play
                    return;
                }

                // Submit the card
                Card toPlay = pHand.playCard(cardSubmitAction.getCardId());
                submittedCards.put(uid, toPlay);

                // If all submissions are accounted for, transition states
                if(submittedCards.size() == usersMap.size()) {
                    // attach the list of cards to the context before starting new state
                    transitionVoteState();
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
    private void topUpHands(HashMap<String, Player> users, Map<String, PlayerHand> playerHands){

        for(Player player : users.values()){
            PlayerHand playerHand = playerHands.get(player.getUserID());
            if(playerHand == null) {
                playerHand = new PlayerHand();
                playerHands.put(player.getUserID(), playerHand);
            }

            playerHand.refill();
        }
    }

    private void transitionVoteState(){
        timer.cancel();
        timer = null;

        Map<String, Object> context = this.getContext();
        context.put(KahVoteState.SUBMIT_CARDS_TAG, submittedCards);
        context.put(KahVoteState.BLACK_CARD_TAG, blackCard);


        manager.setState(new KahVoteState(manager, context));

        broadcastRefresh();
    }
}

