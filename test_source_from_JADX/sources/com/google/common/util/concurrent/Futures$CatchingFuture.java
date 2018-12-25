package com.google.common.util.concurrent;

import com.google.common.base.Function;
import com.google.common.util.concurrent.Futures.AbstractCatchingFuture;

final class Futures$CatchingFuture<V, X extends Throwable> extends AbstractCatchingFuture<V, X, Function<? super X, ? extends V>> {
    Futures$CatchingFuture(ListenableFuture<? extends V> input, Class<X> exceptionType, Function<? super X, ? extends V> fallback) {
        super(input, exceptionType, fallback);
    }

    void doFallback(Function<? super X, ? extends V> fallback, X cause) throws Exception {
        set(fallback.apply(cause));
    }
}
