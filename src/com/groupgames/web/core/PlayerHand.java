package com.groupgames.web.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerHand  {
    private static final int HAND_COUNT = 5;

    private Map<Integer, Card> cards;

    public PlayerHand(){
        cards = new HashMap<>();
        this.refill();
    }

    public PlayerHand(List<Card> initCards){
        cards = new HashMap<>();
        for(Card c : initCards){
            cards.put(c.getCardID(), c);
        }
    }

    public boolean hasCard(Integer cardID) {
        return cards.containsKey(cardID);
    }

    public Card playCard(Integer cardID){
        Card c = cards.get(cardID);

        if(c != null)
            cards.remove(cardID);

        return c;
    }

    /**
     * Fill the cards map with more cards to reach HAND_COUNT
     *
     */
    public void refill(){
        int missingCards = HAND_COUNT - cards.size();

        if(missingCards > 0) {
            List<Card> newCards = CardManager.getWhiteCards(missingCards);
            for (Card c : newCards){
                cards.put(c.getCardID(), c);
            }
        }
    }

    public Map<Integer, String> asMap(){
        Map<Integer, String> cardsMap = new HashMap<>();

        for(Card c : cards.values()) {
            int cardID = c.getCardID();
            String cardText = c.getCardText();
            cardsMap.put(cardID, cardText);
        }
        return cardsMap;
    }
}
