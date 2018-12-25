package com.google.common.util.concurrent;

import com.google.common.base.Function;
import com.google.common.util.concurrent.Futures.AbstractChainingFuture;

final class Futures$ChainingFuture<I, O> extends AbstractChainingFuture<I, O, Function<? super I, ? extends O>> {
    Futures$ChainingFuture(ListenableFuture<? extends I> inputFuture, Function<? super I, ? extends O> function) {
        super(inputFuture, function);
    }

    void doTransform(Function<? super I, ? extends O> function, I input) {
        set(function.apply(input));
    }
}
