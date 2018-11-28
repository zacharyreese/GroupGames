package com.groupgames.web.states.kah;

import com.groupgames.web.core.Card;
import com.groupgames.web.core.CardManager;
import com.groupgames.web.core.Player;
import com.groupgames.web.core.PlayerHand;
import com.groupgames.web.game.GameAction;
import com.groupgames.web.game.State;
import com.groupgames.web.game.StateManager;
import com.groupgames.web.game.view.TemplateView;
import com.groupgames.web.game.view.View;
import com.groupgames.web.states.kah.actions.CardSubmitAction;

import java.io.IOException;
import java.util.*;

public class KahVoteState extends State {
    public static final String SUBMIT_CARDS_TAG = "submittedCards";
    public static final String BLACK_CARD_TAG = "blackCard";

    private Integer countdownTimer;
    private Timer timer;

    private HashMap<String, Card> submittedCards; // map userID to the card they played
    private Card blackCard;

    private List<String> votedUsers = new ArrayList<>();
    private Map<Integer, Integer> cardVotes = new HashMap<>(); // map to hold votes for each card ID

    public KahVoteState(StateManager manager, Map<String, Object> context) {
        super(manager, context);

        submittedCards = (HashMap<String, Card>) getContext().get(SUBMIT_CARDS_TAG);
        blackCard = (Card) getContext().get(BLACK_CARD_TAG);

        countdownTimer = 30;
        // Start the countdown timer
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                if (countdownTimer > 0) {
                    countdownTimer--;
                } else {
                    transitionWinState();
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

        templateData.put("handMessage", "Pick the best card!");

        // Add host/player specific fields and set the corresponding ftl files
        try {
            if(uid == null) {
                // Handle host view
                templateData.put("hostCardText", blackCard.getCardText());

                view = new TemplateView(webRootPath, "hostSubmission.ftl", templateData);
            } else {
                // Handle user view
                PlayerHand tempHand = new PlayerHand(new ArrayList<>(submittedCards.values()));
                templateData.put("cards", tempHand.asMap());

                view = new TemplateView(webRootPath, "playerHand.ftl", templateData);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return view;
    }

    @Override
    public void doAction(String uid, GameAction action) {
        switch (action.getType()) {
            case "cardSubmit":
                // Update the user's submitted card with the new card
                CardSubmitAction cardSubmitAction = new CardSubmitAction(action);

                // If the user hasn't already voted, cast their vote
                if(!votedUsers.contains(uid)) {
                    int cardId = cardSubmitAction.getCardId();
                    Integer count = cardVotes.get(cardId);
                    count = count != null ? count + 1 : 1; // If the vote count exists, increment it. Otherwise set to 1
                    cardVotes.put(cardId, count);
                    votedUsers.add(uid);
                }

                // If all votes are accounted for, transition states
                if(votedUsers.size() == usersMap.size()){
                    transitionWinState();
                }
                break;
        }
    }

    private Card getVotedCard(Map<Integer, Integer> cardVotes) {
        Integer cardId = null;
        Integer maxVoteCount = null;

        for (Map.Entry<Integer, Integer> entry : cardVotes.entrySet()) {
            int voteCount = entry.getValue();
            if (maxVoteCount == null || voteCount > maxVoteCount) {
                cardId = entry.getKey();
                maxVoteCount = voteCount;
            }
        }

        if (cardId == null)
            return null;

        return CardManager.getWhiteCardId(cardId);
    }

    private Player getWinner(Card winningCard) {
        for(Map.Entry<String, Card> submitted : submittedCards.entrySet()) {
            String submitterId = submitted.getKey();
            Card card = submitted.getValue();

            if (card.equals(winningCard)){
                Player submitter = usersMap.get(submitterId);
                return submitter;
            }
        }

        // Failed to find the user that submitted the card
        return null;
    }

    private void transitionWinState(){
        timer.cancel();
        timer = null;

        Map<String, Object> context = this.getContext();

        Card winningCard = getVotedCard(this.cardVotes);
        context.put(KahWinnerState.WINNING_CARD_TAG, winningCard);
        context.put(KahWinnerState.WINNER_TAG, getWinner(winningCard));

        manager.setState(new KahWinnerState(manager, context));

        broadcastRefresh();
    }
}
