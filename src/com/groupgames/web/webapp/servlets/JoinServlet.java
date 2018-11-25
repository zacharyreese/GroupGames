package com.groupgames.web.webapp.servlets;

import com.groupgames.web.core.GameManager;
import com.groupgames.web.core.Player;
import com.groupgames.web.game.GameLobby;
import com.groupgames.web.game.view.View;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "JoinServlet", urlPatterns = "/game/play/join")
public class JoinServlet extends ServletTemplate {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        GameManager gameManager = GameManager.getInstance();
        Session websocket;
        PrintWriter out = response.getWriter();
        String gameCode = request.getParameter("lobbyCode");
        String username = request.getParameter("userName");
        //String userID = playerSession.getId();
        Player player = null;

        if(gameCode.length() != 0 && username.length() != 0) { //Create player object and pass player to host to be added to ArrayList of players (Could be changed to add to DB)
            player = new Player(username, gameCode);
            if(gameManager.getLobby(player.getGameCode()) != null) {
                GameLobby lobby = gameManager.getLobby(player.getGameCode());
                lobby.addUser("1234", player.getUsername());
                View view = lobby.getView("1234", webRootPath);

                boolean successResponse = view.respond(response.getWriter());
            }
            //player.registerWebsocket(websocket.getOpenSessions());
            /*playerSession.setAttribute("Player", player);
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/game/host");
            dispatcher.forward(request, response);*/
        } else {
            out.println("Enter a username and a game code");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
