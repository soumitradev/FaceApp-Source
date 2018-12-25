package com.koushikdutta.async.future;

public interface FutureCallback<T> {
    void onCompleted(Exception exception, T t);
}
