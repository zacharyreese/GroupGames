package com.groupgames.web.game;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Map;

/**
 * GameAction represents an action performed by the user through a post request. Is to be parsed as a JSON object
 *
 */
public class GameAction {
    protected Map<String, Object> parsed;

    private static final String TYPE_TAG = "type";

    /**
     * Takes a JSON string parses the input into a GameAction object.
     * Requires a single "type" field with a string to be a valid object
     *
     * @param jsonStr
     * @throws IllegalArgumentException
     */
    public GameAction(String jsonStr) throws IllegalArgumentException {
        // Parse the JSON into a generic object map
        Type type = new TypeToken<Map<String, Object>>(){}.getType();
        parsed = new Gson().fromJson(jsonStr, type);

        // Ensure all required fields are of the correct type
        requireField(TYPE_TAG, String.class);
    }

    /**
     * Copies an actions parsed values into this one. Will not throw an error as the
     * initial constructor should have checked for this as well.
     *
     * @param baseAction
     */
    public GameAction (GameAction baseAction) {
        this.parsed = baseAction.parsed;
    }

    /**
     * Returns true iff the field exists in the map AND the value is an instance of the class provided
     *
     * @param fieldKey
     * @param valueType
     * @return
     */
    protected boolean requireField(String fieldKey, Class valueType) throws IllegalArgumentException {
        Object value = parsed.get(fieldKey);
        if (value == null)
            return false;

        return valueType.isInstance(value);
    }

    /**
     * Returns the type value of the GameAction
     *
     * @return
     */
    public String getType(){
        return (String) parsed.get(TYPE_TAG);
    }
}
