package com.groupgames.web.game.view;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;

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
        try {
            this.gson.toJson(jsonData, out);
        } catch (JsonIOException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public String toString(){
        try {
            return this.gson.toJson(jsonData);
        } catch (JsonIOException e){
            e.printStackTrace();
            return "{}";
        }
    }
}
