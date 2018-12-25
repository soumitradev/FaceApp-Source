package com.koushikdutta.async.future;

public interface FutureRunnable<T> {
    T run() throws Exception;
}
