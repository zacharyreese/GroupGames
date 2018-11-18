package com.groupgames.web.webapp.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

// Placeholder servlet. Should be removed once everything is up and running
@WebServlet(name = "TestServlet", urlPatterns = "/testdb")
public class TestServlet extends ServletTemplate {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean dbInitSuccess = false;
        PrintWriter out = response.getWriter();
        HttpSession mySession = request.getSession();
        String successGrantMsg = "GRANT ALL PRIVILEGES ON `groupgames`.* TO `gg_server`@`localhost`";
        try {
            ResultSet rs = dbMgr.getConnection().createStatement().executeQuery("SHOW GRANTS FOR CURRENT_USER");
            while(rs.next() && !dbInitSuccess){
                dbInitSuccess |= rs.getString(1).equalsIgnoreCase(successGrantMsg);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if(dbInitSuccess) {
            response.getWriter().write("Success! Looks like the database is initialized!");
            System.out.println("Successfully connected to DB");
            System.out.println("Session ID: " + mySession.getId());
            System.out.println("New session? " + mySession.isNew());
        } else {
            response.getWriter().write("Something went wrong. Make sure to modify and run sql/init_db.sql");
        }
    }
}
