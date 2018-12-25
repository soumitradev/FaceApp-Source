package com.koushikdutta.async.http.cache;

enum ResponseSource {
    CACHE,
    CONDITIONAL_CACHE,
    NETWORK;

    public boolean requiresConnection() {
        if (this != CONDITIONAL_CACHE) {
            if (this != NETWORK) {
                return false;
            }
        }
        return true;
    }
}
