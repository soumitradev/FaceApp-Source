package com.koushikdutta.async.future;

public interface Future<T> extends Cancellable, java.util.concurrent.Future<T> {
    Future<T> setCallback(FutureCallback<T> futureCallback);

    <C extends FutureCallback<T>> C then(C c);

    T tryGet();

    Exception tryGetException();
}
