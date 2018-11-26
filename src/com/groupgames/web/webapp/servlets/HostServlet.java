package com.groupgames.web.webapp.servlets;

import com.groupgames.web.core.GameManager;
import com.groupgames.web.core.Host;
import com.groupgames.web.core.Player;
import com.groupgames.web.game.GameAction;
import com.groupgames.web.game.GameLobby;
import com.groupgames.web.game.view.View;
import com.groupgames.web.webapp.ServletError;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "HostServlet", urlPatterns = "/game/host")
public class HostServlet extends GameBaseServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession clientSession = request.getSession();

        // playerAction stores the action attempted to be executed by the client
        GameAction playerAction;

        try {
            // Read the entire JSON-encoded body into a string
            String jsonAction = readBody(request);
            // Attempt to parse the JSON action into a game action
            playerAction = new GameAction(jsonAction);
        } catch (IllegalArgumentException e){
            // Body of request was not JSON-encoded or did not meet GameAction field requirement check
            respondWithError(response, "error.ftl", ServletError.MALFORMED_REQUEST);
            return;
        }

        String gameCode = (String) clientSession.getAttribute("gamecode");
        if (gameCode == null) {
            // Redirect user back to homepage
            response.sendRedirect(request.getContextPath());
            return;
        }

        GameLobby lobby = gameManager.getLobby(gameCode);
        if (lobby == null) {
            respondWithError(response, "error.ftl", ServletError.RESOURCE_NOT_FOUND);
            return;
        }

        // Perform the action
        lobby.doAction(clientSession.getId(), playerAction);

        View view = lobby.getView(null, webRootPath);
        if (view == null || !view.respond(response.getWriter())) {
            // TODO: Handle write failure
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession clientSession = request.getSession();

        String gameCode = (String) clientSession.getAttribute("gamecode");
        if (gameCode == null) {
            // Redirect user back to homepage
            response.sendRedirect("../../");
            return;
        }

        GameLobby lobby = gameManager.getLobby(gameCode);
        if (lobby == null) {
            respondWithError(response, "error.ftl", ServletError.RESOURCE_NOT_FOUND);
            return;
        }

        View view = lobby.getView(null, webRootPath);
        if (view == null || !view.respond(response.getWriter())) {
            // TODO: Handle write failure
        }
    }
}
