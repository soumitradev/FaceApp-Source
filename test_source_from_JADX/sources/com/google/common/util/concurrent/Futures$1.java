package com.google.common.util.concurrent;

import com.google.common.base.Preconditions;

class Futures$1 implements AsyncFunction<Throwable, V> {
    final /* synthetic */ FutureFallback val$fallback;

    Futures$1(FutureFallback futureFallback) {
        this.val$fallback = futureFallback;
    }

    public ListenableFuture<V> apply(Throwable t) throws Exception {
        return (ListenableFuture) Preconditions.checkNotNull(this.val$fallback.create(t), "FutureFallback.create returned null instead of a Future. Did you mean to return immediateFuture(null)?");
    }
}
