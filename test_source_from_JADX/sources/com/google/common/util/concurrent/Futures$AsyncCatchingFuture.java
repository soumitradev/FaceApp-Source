package com.google.common.util.concurrent;

import com.google.common.base.Preconditions;
import com.google.common.util.concurrent.Futures.AbstractCatchingFuture;

final class Futures$AsyncCatchingFuture<V, X extends Throwable> extends AbstractCatchingFuture<V, X, AsyncFunction<? super X, ? extends V>> {
    Futures$AsyncCatchingFuture(ListenableFuture<? extends V> input, Class<X> exceptionType, AsyncFunction<? super X, ? extends V> fallback) {
        super(input, exceptionType, fallback);
    }

    void doFallback(AsyncFunction<? super X, ? extends V> fallback, X cause) throws Exception {
        ListenableFuture<? extends V> replacement = fallback.apply(cause);
        Preconditions.checkNotNull(replacement, "AsyncFunction.apply returned null instead of a Future. Did you mean to return immediateFuture(null)?");
        setFuture(replacement);
    }
}
