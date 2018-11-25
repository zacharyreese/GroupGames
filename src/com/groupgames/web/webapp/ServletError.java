package com.groupgames.web.webapp;

import java.util.HashMap;
import java.util.Map;

public enum ServletError {
    RESOURCE_NOT_FOUND(2, "resource not found"),
    MALFORMED_REQUEST (3, "malformed request"),
    INTERNAL_FAILURE  (-1, "internal error");

    public final int errCode;
    public final String errMsg;

    ServletError(int code, String msg){
        this.errCode = code;
        this.errMsg = msg;
    }

    public Map<String, Object> asMap() {
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("errCode", this.errCode);
        dataMap.put("errMsg", this.errMsg);
        return dataMap;
    }

    public static void setError(Map<String, Object> templateData, ServletError err) {
        templateData.put("error", err.asMap());
    }
}
