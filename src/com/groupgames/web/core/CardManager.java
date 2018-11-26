package com.groupgames.web.core;

import java.sql.*;
import java.util.ArrayList;

public class CardManager {
    Connection conn;
    static Statement stmt;
    static ResultSet RS;
    static PreparedStatement preparedStmt;

    ArrayList<Card> whiteCardsList;
    ArrayList<Card> blackCardsList;

    public ArrayList<Card> getWhiteCards(int cardAmount) throws SQLException {
        String SQL = "SELECT * FROM kah_whitecard ORDER BY RAND() LIMIT " + cardAmount;
        whiteCardsList = new ArrayList<>();
        conn  = DBManager.getInstance().getConnection();
        stmt = conn.createStatement();
        RS = stmt.executeQuery(SQL);

        while(RS.next()) {
            String cardID = RS.getString(1);
            String cardText = RS.getString(2);
            Card card = new Card(cardID, cardText);
            whiteCardsList.add(card);
        }
        return whiteCardsList;
    }

    public ArrayList<Card> getAllBlackCards() throws SQLException {
        String SQL = "SELECT * FROM kah_blackcard";
        blackCardsList = new ArrayList<>();
        conn  = DBManager.getInstance().getConnection();
        stmt = conn.createStatement();
        RS = stmt.executeQuery(SQL);

        while(RS.next()) {
            String cardID = RS.getString(1);
            String cardText = RS.getString(2);
            Card card = new Card(cardID, cardText);
            blackCardsList.add(card);
        }
        return blackCardsList;
    }

    public Card getRandBlackCard() throws SQLException {
        String SQL = "SELECT * FROM kah_blackcard ORDER BY RAND() LIMIT 1";
        blackCardsList = new ArrayList<>();
        conn  = DBManager.getInstance().getConnection();
        stmt = conn.createStatement();
        RS = stmt.executeQuery(SQL);
        Card card = null;

        while(RS.next()) {
            String cardID = RS.getString(1);
            String cardText = RS.getString(2);
            card = new Card(cardID, cardText);
        }
        return card;
    }

    public static void main(String args[]) throws SQLException {
        CardManager c = new CardManager();
        ArrayList<Card> arr = c.getWhiteCards(20);
        for(Card card : arr) {
            System.out.println(card.cardText);
        }
    }

}
