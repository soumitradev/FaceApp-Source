package com.google.common.base;

import java.io.Serializable;
import javax.annotation.Nullable;

class Suppliers$SupplierComposition<F, T> implements Supplier<T>, Serializable {
    private static final long serialVersionUID = 0;
    final Function<? super F, T> function;
    final Supplier<F> supplier;

    Suppliers$SupplierComposition(Function<? super F, T> function, Supplier<F> supplier) {
        this.function = function;
        this.supplier = supplier;
    }

    public T get() {
        return this.function.apply(this.supplier.get());
    }

    public boolean equals(@Nullable Object obj) {
        boolean z = false;
        if (!(obj instanceof Suppliers$SupplierComposition)) {
            return false;
        }
        Suppliers$SupplierComposition<?, ?> that = (Suppliers$SupplierComposition) obj;
        if (this.function.equals(that.function) && this.supplier.equals(that.supplier)) {
            z = true;
        }
        return z;
    }

    public int hashCode() {
        return Objects.hashCode(new Object[]{this.function, this.supplier});
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Suppliers.compose(");
        stringBuilder.append(this.function);
        stringBuilder.append(", ");
        stringBuilder.append(this.supplier);
        stringBuilder.append(")");
        return stringBuilder.toString();
    }
}
