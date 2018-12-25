package com.google.common.base;

import java.io.Serializable;
import javax.annotation.Nullable;

class Suppliers$SupplierOfInstance<T> implements Supplier<T>, Serializable {
    private static final long serialVersionUID = 0;
    final T instance;

    Suppliers$SupplierOfInstance(@Nullable T instance) {
        this.instance = instance;
    }

    public T get() {
        return this.instance;
    }

    public boolean equals(@Nullable Object obj) {
        if (!(obj instanceof Suppliers$SupplierOfInstance)) {
            return false;
        }
        return Objects.equal(this.instance, ((Suppliers$SupplierOfInstance) obj).instance);
    }

    public int hashCode() {
        return Objects.hashCode(new Object[]{this.instance});
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Suppliers.ofInstance(");
        stringBuilder.append(this.instance);
        stringBuilder.append(")");
        return stringBuilder.toString();
    }
}
