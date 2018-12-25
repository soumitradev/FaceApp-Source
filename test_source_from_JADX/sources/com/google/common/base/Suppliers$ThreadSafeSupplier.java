package com.google.common.base;

import java.io.Serializable;

class Suppliers$ThreadSafeSupplier<T> implements Supplier<T>, Serializable {
    private static final long serialVersionUID = 0;
    final Supplier<T> delegate;

    Suppliers$ThreadSafeSupplier(Supplier<T> delegate) {
        this.delegate = delegate;
    }

    public T get() {
        T t;
        synchronized (this.delegate) {
            t = this.delegate.get();
        }
        return t;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Suppliers.synchronizedSupplier(");
        stringBuilder.append(this.delegate);
        stringBuilder.append(")");
        return stringBuilder.toString();
    }
}
