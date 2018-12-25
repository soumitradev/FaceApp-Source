package com.google.common.base;

import java.io.Serializable;
import javax.annotation.Nullable;

class Functions$ConstantFunction<E> implements Function<Object, E>, Serializable {
    private static final long serialVersionUID = 0;
    private final E value;

    public Functions$ConstantFunction(@Nullable E value) {
        this.value = value;
    }

    public E apply(@Nullable Object from) {
        return this.value;
    }

    public boolean equals(@Nullable Object obj) {
        if (!(obj instanceof Functions$ConstantFunction)) {
            return false;
        }
        return Objects.equal(this.value, ((Functions$ConstantFunction) obj).value);
    }

    public int hashCode() {
        return this.value == null ? 0 : this.value.hashCode();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Functions.constant(");
        stringBuilder.append(this.value);
        stringBuilder.append(")");
        return stringBuilder.toString();
    }
}
