package com.groupgames.web.states.lobby;

import com.groupgames.web.core.GameManager;
import com.groupgames.web.core.Player;
import com.groupgames.web.game.*;
import com.groupgames.web.game.view.JsonView;
import com.groupgames.web.game.view.TemplateView;
import com.groupgames.web.game.view.View;
import com.groupgames.web.states.kah.KahStartState;
import com.groupgames.web.states.lobby.actions.*;

import javax.websocket.Session;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerJoinState extends State {
    public static final String USERS_TAG = "users";
    public static final String GAME_CODE_TAG = "gamecode";
    HashMap<String, Object> templateData = new HashMap<>();
    HashMap<String, Player> usersMap;


    private List<Player> connectedUsers;

    public PlayerJoinState(StateManager manager, Map<String, Object> context) {
        super(manager, context);
        usersMap = (HashMap<String, Player>)getContext().get(USERS_TAG);
        //forceRefresh();
    }

    @Override
    public void update() {
        HashMap<String, Object> JSONData = new HashMap<>();
        JSONData.put("users", usersMap.values());
        JSONData.put("method", "update");
        JsonView json = new JsonView(JSONData);
        String jsonStr = json.toString();
        for(Player p : usersMap.values()) {
            p.writeUpdate(jsonStr);
        }
        writeUpdate(jsonStr);
    }

    @Override
    public View getView(String uid, String webRootPath) {
        View view = null;

        templateData.put("gamecode", getContext().get(GAME_CODE_TAG));
        templateData.put("users", getContext().get(USERS_TAG));
        templateData.put("uid", uid);

        // Handle host view
        try {
            if(uid == null) {
                view = new TemplateView(webRootPath,"hostLobby.ftl", templateData);
            } else {
                view = new TemplateView(webRootPath,"playerLobby.ftl", templateData);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return view;
    }

    @Override
    public void doAction(String uid, GameAction action) {
        switch (action.getType()) {
            case "start":
                // Handle start game action
                GameStartAction gameStartAction = new GameStartAction(action);
                boolean success = startGame(gameStartAction.getSelected());
                // TODO: Check success?
                break;
            case "options":
                // Handle options change
                OptionAction optionsAction = new OptionAction(action);
                break;
            case "kick":
                // Handle kick action
                KickAction kickAction = new KickAction(action);
                usersMap.remove(uid);
                update();
                break;
        }
    }

    private boolean startGame(String selectedGame) {
        Map<String, Object> context = this.getContext();
        // Add/Remove from the context before starting the game

        switch (selectedGame) {
            case "KaH":
                manager.setState(new KahStartState(manager, context));
                broadcastRefresh();
                return true;
        }

        // Failed to match any configured games. Return false to indicate start failure
        return false;
    }
}
