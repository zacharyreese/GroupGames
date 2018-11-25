package com.groupgames.web.webapp.servlets;

import com.groupgames.web.game.GameLobby;
import com.groupgames.web.game.view.View;
import com.groupgames.web.webapp.ServletError;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "JoinServlet", urlPatterns = "/game/play/join")
public class JoinServlet extends GameBaseServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession clientSession = request.getSession();

        String gameCode = request.getParameter("gamecode");
        String username = request.getParameter("username");

        if (gameCode == null || username == null) {
            respondWithError(response, "error.ftl", ServletError.MALFORMED_REQUEST);
            return;
        }

        GameLobby lobby = gameManager.getLobby(gameCode);
        if (lobby == null){
            respondWithError(response, "error.ftl", ServletError.RESOURCE_NOT_FOUND);
            return;
        }

        if (!lobby.addUser(clientSession.getId(), username)){
            // TODO: This redirect is broken. Should redirect to /game/play with params for lobby ID and user ID
            response.sendRedirect("../");
        }

        View view = lobby.getView(clientSession.getId(), webRootPath);
        if (view == null || !view.respond(response.getWriter())) {
            // TODO: Handle write failure
        }

        // Set the gameCode for the session so it doesnt have to be looked up by the PlayServlet
        clientSession.setAttribute("gamecode", gameCode);
        //response.sendRedirect("../");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
