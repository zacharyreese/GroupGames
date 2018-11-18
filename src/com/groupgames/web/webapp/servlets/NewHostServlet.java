package com.groupgames.web.webapp.servlets;

import com.groupgames.web.core.Host;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "NewHostServlet",  urlPatterns = "/game/host/new")
public class NewHostServlet extends ServletTemplate {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession hostSession = request.getSession(); //Get host session
        PrintWriter out = response.getWriter();
        String gameSessionID = hostSession.getId();
        String gameCode = "";
        System.out.println("Session ID: " + gameSessionID);
        //Generate 4 letter game code
        for(int i = 0; i < 4; i++) {
            char codeChar = (char)((int)'A'+Math.random()*((int)'Z'-(int)'A'+1));
            gameCode += codeChar;
        }
        Host host = new Host(gameSessionID, gameCode); //Create host object with sessionID and gamecode
        hostSession.setAttribute("gameSessionID", gameSessionID); //Add attributes to be passed into /game/host
        hostSession.setAttribute("gameCode", gameCode);
        hostSession.setAttribute("Host", host);
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/game/host"); //Forward to /game/host
        dispatcher.forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
