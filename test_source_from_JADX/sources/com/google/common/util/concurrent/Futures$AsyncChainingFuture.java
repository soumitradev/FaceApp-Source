package com.google.common.util.concurrent;

import com.google.common.base.Preconditions;
import com.google.common.util.concurrent.Futures.AbstractChainingFuture;

final class Futures$AsyncChainingFuture<I, O> extends AbstractChainingFuture<I, O, AsyncFunction<? super I, ? extends O>> {
    Futures$AsyncChainingFuture(ListenableFuture<? extends I> inputFuture, AsyncFunction<? super I, ? extends O> function) {
        super(inputFuture, function);
    }

    void doTransform(AsyncFunction<? super I, ? extends O> function, I input) throws Exception {
        ListenableFuture<? extends O> outputFuture = function.apply(input);
        Preconditions.checkNotNull(outputFuture, "AsyncFunction.apply returned null instead of a Future. Did you mean to return immediateFuture(null)?");
        setFuture(outputFuture);
    }
}
