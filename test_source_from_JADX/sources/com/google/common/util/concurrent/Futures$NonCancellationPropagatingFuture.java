package com.google.common.util.concurrent;

import com.google.common.annotations.GwtIncompatible;

@GwtIncompatible("TODO")
final class Futures$NonCancellationPropagatingFuture<V> extends TrustedFuture<V> {
    Futures$NonCancellationPropagatingFuture(final ListenableFuture<V> delegate) {
        delegate.addListener(new Runnable() {
            public void run() {
                Futures$NonCancellationPropagatingFuture.this.setFuture(delegate);
            }
        }, MoreExecutors.directExecutor());
    }
}
