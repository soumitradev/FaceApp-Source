package com.koushikdutta.async.http.socketio;

import org.json.JSONArray;

public interface EventCallback {
    void onEvent(JSONArray jSONArray, Acknowledge acknowledge);
}
