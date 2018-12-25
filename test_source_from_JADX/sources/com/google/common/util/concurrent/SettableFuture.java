package com.google.common.util.concurrent;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import javax.annotation.Nullable;

@GwtCompatible
public final class SettableFuture<V> extends TrustedFuture<V> {
    public static <V> SettableFuture<V> create() {
        return new SettableFuture();
    }

    private SettableFuture() {
    }

    public boolean set(@Nullable V value) {
        return super.set(value);
    }

    public boolean setException(Throwable throwable) {
        return super.setException(throwable);
    }

    @Beta
    public boolean setFuture(ListenableFuture<? extends V> future) {
        return super.setFuture(future);
    }
}
