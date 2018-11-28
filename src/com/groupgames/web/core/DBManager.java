package com.groupgames.web.core;

import com.mysql.cj.xdevapi.DbDoc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBManager {
    private static final String dbUrl  = "jdbc:mysql://localhost/";
    private static final String dbName = "groupgames";
    private static final String dbUser = "root";
    private static final String dbPass = "password";

    private static DBManager dbManagerInstance;

    private Connection conn = null;

    public static DBManager getInstance(){
        if (dbManagerInstance == null){
            dbManagerInstance = new DBManager(dbUrl, dbName, dbUser, dbPass);
        }
        return dbManagerInstance;
    }

    private DBManager(String dbUrl, String dbName, String username, String password) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            conn = DriverManager.getConnection(dbUrl+dbName, username, password);
            System.out.println("Connected to DB");

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
