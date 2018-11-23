package com.groupgames.web.game.view;

import com.google.gson.Gson;

import java.io.Writer;

public class JsonView implements View {
    private Gson gson; //gson encoder
    private Object jsonData;

    public JsonView(Object data){
        this.jsonData = data;

        gson = new Gson();
    }

    @Override
    public boolean respond(Writer out) {
        this.gson.toJson(jsonData, out);
        return true;
    }
}
