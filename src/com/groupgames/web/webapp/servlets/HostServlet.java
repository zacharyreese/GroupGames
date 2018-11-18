package com.groupgames.web.webapp.servlets;

import com.groupgames.web.core.Host;
import com.groupgames.web.core.Player;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "HostServlet", urlPatterns = "/game/host")
public class HostServlet extends ServletTemplate {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        HttpSession hostSession = request.getSession();
        HttpSession playerSession = request.getSession();
        String gameSessionID = (String)hostSession.getAttribute("gameSessionID"); //Get attributes from /game/host/new
        String gameCode = (String)hostSession.getAttribute("gameCode");
        Host host = (Host)hostSession.getAttribute("Host"); //Get host from /game/host/new
        out.println("Session ID: " + host.getLobbyID());
        out.println("Game code: " + host.getGameCode());
        Player player = null;

        if((Player)playerSession.getAttribute("Player") != null) {
            player = (Player)playerSession.getAttribute("Player"); //Get player from /game/play/join
            host.addUser(player); //Add player to player ArrayList
        }

        /*this.getServletConfig().getServletContext().setAttribute("Host", host);
        request.getRequestDispatcher("/game/play/join").forward(request, response);*/
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String gameID = request.getParameter("gameID");
        HttpSession hostSession = request.getSession();
      //  hostSession.getState(); //Not initialized
    }
}
