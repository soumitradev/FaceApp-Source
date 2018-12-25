package com.google.common.base;

import com.google.common.annotations.VisibleForTesting;
import java.io.Serializable;
import java.util.concurrent.TimeUnit;

@VisibleForTesting
class Suppliers$ExpiringMemoizingSupplier<T> implements Supplier<T>, Serializable {
    private static final long serialVersionUID = 0;
    final Supplier<T> delegate;
    final long durationNanos;
    volatile transient long expirationNanos;
    volatile transient T value;

    Suppliers$ExpiringMemoizingSupplier(Supplier<T> delegate, long duration, TimeUnit unit) {
        this.delegate = (Supplier) Preconditions.checkNotNull(delegate);
        this.durationNanos = unit.toNanos(duration);
        Preconditions.checkArgument(duration > 0);
    }

    public T get() {
        long nanos = this.expirationNanos;
        long now = Platform.systemNanoTime();
        if (nanos == 0 || now - nanos >= 0) {
            synchronized (this) {
                if (nanos == this.expirationNanos) {
                    T t = this.delegate.get();
                    this.value = t;
                    nanos = now + this.durationNanos;
                    this.expirationNanos = nanos == 0 ? 1 : nanos;
                    return t;
                }
            }
        }
        return this.value;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Suppliers.memoizeWithExpiration(");
        stringBuilder.append(this.delegate);
        stringBuilder.append(", ");
        stringBuilder.append(this.durationNanos);
        stringBuilder.append(", NANOS)");
        return stringBuilder.toString();
    }
}
