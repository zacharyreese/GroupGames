package com.groupgames.web.states.lobby;

import com.groupgames.web.core.Player;
import com.groupgames.web.game.*;
import com.groupgames.web.game.view.JsonView;
import com.groupgames.web.game.view.View;
import com.groupgames.web.states.kah.KahStartState;
import com.groupgames.web.states.lobby.actions.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerJoinState extends State {
    public static final String USERS_TAG = "users";
    public static final String GAME_CODE_TAG = "gamecode";


    private List<Player> connectedUsers;

    public PlayerJoinState(StateManager manager, Map<String, Object> context) {
        super(manager, context);
    }

    @Override
    public void update() {

    }

    @Override
    public View getView(String uid) {
        View view = null;

        // Handle host view
        if(uid == null) {
            String gameCode = (String) getContext().get(GAME_CODE_TAG);
            HashMap<String, String> jsonData = new HashMap<>();
            jsonData.put("gamecode", gameCode);
            view = new JsonView(jsonData);
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
                break;
        }
    }

    private boolean startGame(String selectedGame) {
        Map<String, Object> context = this.getContext();
        // Add/Remove from the context before starting the game

        switch (selectedGame) {
            case "kah":
                manager.setState(new KahStartState(manager, context));
                return true;
        }

        // Failed to match any configured games. Return false to indicate start failure
        return false;
    }
}
