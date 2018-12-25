package com.google.common.base;

import com.google.common.annotations.VisibleForTesting;
import java.io.Serializable;

@VisibleForTesting
class Suppliers$MemoizingSupplier<T> implements Supplier<T>, Serializable {
    private static final long serialVersionUID = 0;
    final Supplier<T> delegate;
    volatile transient boolean initialized;
    transient T value;

    Suppliers$MemoizingSupplier(Supplier<T> delegate) {
        this.delegate = delegate;
    }

    public T get() {
        if (!this.initialized) {
            synchronized (this) {
                if (!this.initialized) {
                    T t = this.delegate.get();
                    this.value = t;
                    this.initialized = true;
                    return t;
                }
            }
        }
        return this.value;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Suppliers.memoize(");
        stringBuilder.append(this.delegate);
        stringBuilder.append(")");
        return stringBuilder.toString();
    }
}
