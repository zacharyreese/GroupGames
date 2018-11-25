package com.groupgames.web.webapp.servlets;

import com.groupgames.web.game.GameLobby;
import com.groupgames.web.game.view.View;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "NewHostServlet",  urlPatterns = "/game/host/new")
public class NewHostServlet extends GameBaseServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession clientSession = request.getSession();

        String lobbyID = gameManager.newLobby();
        System.out.println("LobbyID: " + lobbyID);
        GameLobby lobby = gameManager.getLobby(lobbyID);
        View view = lobby.getView(null, webRootPath);

        boolean successResponse = view.respond(response.getWriter());

        if(!successResponse){
            // TODO: Handle write failure
        }

        // Set the gameCode for the session so it doesnt have to be looked up by the HostServlet
        clientSession.setAttribute("gamecode", lobbyID);
        //response.sendRedirect("../");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}
