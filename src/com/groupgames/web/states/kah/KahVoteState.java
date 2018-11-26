package com.groupgames.web.states.kah;

import com.groupgames.web.core.Player;
import com.groupgames.web.game.GameAction;
import com.groupgames.web.game.State;
import com.groupgames.web.game.StateManager;
import com.groupgames.web.game.view.JsonView;
import com.groupgames.web.game.view.TemplateView;
import com.groupgames.web.game.view.View;
import com.groupgames.web.states.kah.actions.VoteAction;
import com.sun.org.apache.xerces.internal.xs.StringList;

import java.io.IOException;
import java.util.*;

import static com.groupgames.web.states.lobby.PlayerJoinState.GAME_CODE_TAG;
import static com.groupgames.web.states.lobby.PlayerJoinState.USERS_TAG;

public class KahVoteState extends State {
    private static final String SUBMIT_CARDS_TAG = "SubmitCard";
    List<String> myList = new ArrayList<String>();
    HashMap<String, Player> usersMap;


    Integer countdownTimer = 10;
    private static Timer timer;
    HashMap<Integer, Integer> cardVotes = new HashMap<>();

    public KahVoteState(StateManager manager, Map<String, Object> context) {
        super(manager, context);

        usersMap = (HashMap<String, Player>)getContext().get(USERS_TAG);

        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                synchronized(countdownTimer) {
                    if (countdownTimer == 0) {
                        timer.cancel();
                        manager.setState(new KahWinnerState(manager, context));
                    }
                    countdownTimer--;
                    update();
                }
            }
        }, 0, 1000);
    }

    @Override
    public void update() {
        HashMap<String, Object> JSONData = new HashMap<>();
        JSONData.put("timer", countdownTimer);
        JsonView json = new JsonView(JSONData);
        String timerUpdate = json.toString();
        HashMap<String, Player> usersMap = (HashMap<String, Player>)getContext().get(USERS_TAG);
        for(Player p : usersMap.values()) {
            p.writeUpdate(timerUpdate);
        }
    }

    @Override
    public View getView(String uid, String webRootPath) {
        View view = null;

        // Handle vote view
        if(uid == null) {
            HashMap<String, Object> templateData = new HashMap<>();
            templateData.put("cards", getContext().get(SUBMIT_CARDS_TAG));
            templateData.put("timer", countdownTimer);

            try {
                view = new TemplateView(webRootPath,"playerHand.ftl", templateData);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return view;
    }

    @Override
    public void doAction(String uid, GameAction action) {
        Map<String, Object> context = this.getContext();
        switch (action.getType()) {
            case "vote":
                // Handles submit action
                VoteAction voteAction = new VoteAction(action);

                if(!myList.contains(uid)) {

                    int count = cardVotes.get(voteAction.getSelected());
                    cardVotes.put(voteAction.getSelected(), count + 1);
                    myList.add(uid);

                }
                if(myList.size()==usersMap.size()){


                    manager.setState(new KahWinnerState(manager, context));
                }

                break;

        }
    }
    private Integer getVotedCard() {
        Map.Entry<Integer, Integer> firstEntry = cardVotes.entrySet().iterator().next();
        int largestKey = firstEntry.getKey();
        Integer largestKeyValue = firstEntry.getValue();

        for (Map.Entry<Integer, Integer> entry : cardVotes.entrySet()) {
            int value = entry.getValue();
            if (value > largestKeyValue) {
                largestKey = entry.getKey();
                largestKeyValue = entry.getValue();            }
        }
        return largestKey;

    }
}
