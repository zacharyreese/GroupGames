package com.groupgames.web.core;

public class Card {
    int cardID;
    String cardText;

    public Card(int cardID, String cardText) {
        this.cardID = cardID;
        this.cardText = cardText;
    }

    public int getCardID() {
        return this.cardID;
    }

    public String getCardText() {
        return this.cardText;
    }

    public boolean equals(Card c){
        return this.getCardID() == c.getCardID();
    }
}
