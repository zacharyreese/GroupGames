package com.groupgames.web.webapp.servlets;

import com.groupgames.web.core.GameManager;
import com.groupgames.web.game.GameLobby;
import com.groupgames.web.game.view.View;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "NewHostServlet",  urlPatterns = "/game/host/new")
public class NewHostServlet extends ServletTemplate {
    private GameManager gameManager = GameManager.getInstance();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String lobbyID = gameManager.newLobby();
        GameLobby lobby = gameManager.getLobby(lobbyID);
        View view = lobby.getView(null, webRootPath);

        boolean successResponse = view.respond(response.getWriter());

        if(!successResponse){
            // Handle write failure
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
