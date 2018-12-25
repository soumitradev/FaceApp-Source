package com.google.common.base;

import java.io.Serializable;
import javax.annotation.Nullable;

class Functions$SupplierFunction<T> implements Function<Object, T>, Serializable {
    private static final long serialVersionUID = 0;
    private final Supplier<T> supplier;

    private Functions$SupplierFunction(Supplier<T> supplier) {
        this.supplier = (Supplier) Preconditions.checkNotNull(supplier);
    }

    public T apply(@Nullable Object input) {
        return this.supplier.get();
    }

    public boolean equals(@Nullable Object obj) {
        if (!(obj instanceof Functions$SupplierFunction)) {
            return false;
        }
        return this.supplier.equals(((Functions$SupplierFunction) obj).supplier);
    }

    public int hashCode() {
        return this.supplier.hashCode();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Functions.forSupplier(");
        stringBuilder.append(this.supplier);
        stringBuilder.append(")");
        return stringBuilder.toString();
    }
}
