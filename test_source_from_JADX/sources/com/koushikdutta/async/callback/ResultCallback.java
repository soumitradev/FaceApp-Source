package com.koushikdutta.async.callback;

public interface ResultCallback<S, T> {
    void onCompleted(Exception exception, S s, T t);
}
