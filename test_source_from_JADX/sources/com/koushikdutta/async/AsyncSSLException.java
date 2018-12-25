package com.koushikdutta.async;

public class AsyncSSLException extends Exception {
    private boolean mIgnore = false;

    public AsyncSSLException(Throwable cause) {
        super("Peer not trusted by any of the system trust managers.", cause);
    }

    public void setIgnore(boolean ignore) {
        this.mIgnore = ignore;
    }

    public boolean getIgnore() {
        return this.mIgnore;
    }
}
