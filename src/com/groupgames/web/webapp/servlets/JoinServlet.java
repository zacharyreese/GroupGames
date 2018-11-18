package com.groupgames.web.webapp.servlets;

import com.groupgames.web.core.Player;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "JoinServlet", urlPatterns = "/game/play/join")
public class JoinServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession playerSession = request.getSession();
        PrintWriter out = response.getWriter();
        String gameCode = request.getParameter("gameCode");
        String username = request.getParameter("username");
        String userID = playerSession.getId();
        Player player = null;

        if(gameCode.length() != 0 && username.length() != 0) { //Create player object and pass player to host to be added to ArrayList of players (Could be changed to add to DB)
            player = new Player(userID, username, gameCode);
            playerSession.setAttribute("Player", player);
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/game/host");
            dispatcher.forward(request, response);
        } else {
            out.println("Enter a username and a game code");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
