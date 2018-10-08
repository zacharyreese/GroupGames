package com.groupgames.web.webapp.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
        } else {
            response.getWriter().write("Something went wrong. Make sure to modify and run sql/init_db.sql");
        }
    }
}
