package com.groupgames.web.core;

public class Card {
    String cardID;
    String cardText;

    public Card(String cardID, String cardText) {
        this.cardID = cardID;
        this.cardText = cardText;
    }

    public String getCardID() {
        return this.cardID;
    }

    public String getCardText() {
        return this.cardText;
    }
}
