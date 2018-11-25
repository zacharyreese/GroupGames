package com.groupgames.web.states.lobby.actions;

import com.groupgames.web.game.GameAction;

import java.util.Timer;
import java.util.TimerTask;

public class TimerAction extends GameAction{

    private static final String TIMER = "timer";
    private static int time;
    static Timer timer;

    public TimerAction(GameAction baseAction) throws IllegalArgumentException {
        super(baseAction);

        requireField(TIMER, String.class);
    }

    public String getSelected(){
        return (String) parsed.get(TIMER);
    }

    public void countdown() {
        time = 10;
            timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {
                public void run() {
                    System.out.println(setInterval());
                }
            }, 0, 1000);
    }

    private static final int setInterval() {
        if (time == 0)
            timer.cancel();
        return time--;
    }

    public static void main(String args[]) {
        timer = new Timer();
        time = 10;
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                System.out.println(setInterval());
            }
        }, 0, 1000);
    }
}
