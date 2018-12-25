package com.koushikdutta.async.http.socketio;

import android.net.Uri;
import com.koushikdutta.async.http.AsyncHttpPost;

public class SocketIORequest extends AsyncHttpPost {
    Config config;
    String endpoint;
    String query;

    public static class Config {
        boolean randomizeReconnectDelay = false;
        long reconnectDelay = 1000;
        long reconnectDelayMax = 0;

        public void setRandomizeReconnectDelay(boolean randomizeReconnectDelay) {
            this.randomizeReconnectDelay = randomizeReconnectDelay;
        }

        public boolean isRandomizeReconnectDelay() {
            return this.randomizeReconnectDelay;
        }

        public void setReconnectDelay(long reconnectDelay) {
            if (reconnectDelay < 0) {
                throw new IllegalArgumentException("reconnectDelay must be >= 0");
            }
            this.reconnectDelay = reconnectDelay;
        }

        public long getReconnectDelay() {
            return this.reconnectDelay;
        }

        public void setReconnectDelayMax(long reconnectDelayMax) {
            if (this.reconnectDelay < 0) {
                throw new IllegalArgumentException("reconnectDelayMax must be >= 0");
            }
            this.reconnectDelayMax = reconnectDelayMax;
        }

        public long getReconnectDelayMax() {
            return this.reconnectDelayMax;
        }
    }

    public SocketIORequest(String uri) {
        this(uri, "");
    }

    public Config getConfig() {
        return this.config;
    }

    public String getEndpoint() {
        return this.endpoint;
    }

    public String getQuery() {
        return this.query;
    }

    public SocketIORequest(String uri, String endpoint) {
        this(uri, endpoint, null);
    }

    public SocketIORequest(String uri, String endpoint, String query) {
        this(uri, endpoint, query, null);
    }

    public SocketIORequest(String uri, String endpoint, String query, Config config) {
        String str;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(uri);
        if (query == null) {
            str = "";
        } else {
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("?");
            stringBuilder2.append(query);
            str = stringBuilder2.toString();
        }
        stringBuilder.append(str);
        super(Uri.parse(stringBuilder.toString()).buildUpon().encodedPath("/socket.io/1/").build().toString());
        this.config = config != null ? config : new Config();
        this.endpoint = endpoint;
        this.query = query;
    }
}
