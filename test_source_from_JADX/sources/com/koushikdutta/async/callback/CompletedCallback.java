package com.koushikdutta.async.callback;

public interface CompletedCallback {

    public static class NullCompletedCallback implements CompletedCallback {
        public void onCompleted(Exception ex) {
        }
    }

    void onCompleted(Exception exception);
}
