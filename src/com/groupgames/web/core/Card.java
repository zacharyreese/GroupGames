package com.groupgames.web.core;

public class Card {
    String cardID;
    String cardText;

    public Card(String cardID, String cardText) {
        this.cardID = cardID;
        this.cardText = cardText;
    }

    public String getCardID(Card card) {
        return card.cardID;
    }

    public String getCardText(Card card) {
        return card.cardText;
    }
}
