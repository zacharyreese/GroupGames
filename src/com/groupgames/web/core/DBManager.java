package com.groupgames.web.core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBManager {

    private Connection conn = null;

    public DBManager(String dbUrl, String dbName, String username, String password) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            conn = DriverManager.getConnection(dbUrl+dbName, username, password);

        } catch (SQLException e) {
            System.err.println("Failed to connect to SQL server");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Unknown error prevented connecting to database server.");
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return this.conn;
    }
}
