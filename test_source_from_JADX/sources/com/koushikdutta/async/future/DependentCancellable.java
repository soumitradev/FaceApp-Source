package com.koushikdutta.async.future;

public interface DependentCancellable extends Cancellable {
    DependentCancellable setParent(Cancellable cancellable);
}
