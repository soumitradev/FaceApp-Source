package com.google.common.base;

import java.io.Serializable;
import javax.annotation.Nullable;

final class Converter$FunctionBasedConverter<A, B> extends Converter<A, B> implements Serializable {
    private final Function<? super B, ? extends A> backwardFunction;
    private final Function<? super A, ? extends B> forwardFunction;

    private Converter$FunctionBasedConverter(Function<? super A, ? extends B> forwardFunction, Function<? super B, ? extends A> backwardFunction) {
        this.forwardFunction = (Function) Preconditions.checkNotNull(forwardFunction);
        this.backwardFunction = (Function) Preconditions.checkNotNull(backwardFunction);
    }

    protected B doForward(A a) {
        return this.forwardFunction.apply(a);
    }

    protected A doBackward(B b) {
        return this.backwardFunction.apply(b);
    }

    public boolean equals(@Nullable Object object) {
        boolean z = false;
        if (!(object instanceof Converter$FunctionBasedConverter)) {
            return false;
        }
        Converter$FunctionBasedConverter<?, ?> that = (Converter$FunctionBasedConverter) object;
        if (this.forwardFunction.equals(that.forwardFunction) && this.backwardFunction.equals(that.backwardFunction)) {
            z = true;
        }
        return z;
    }

    public int hashCode() {
        return (this.forwardFunction.hashCode() * 31) + this.backwardFunction.hashCode();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Converter.from(");
        stringBuilder.append(this.forwardFunction);
        stringBuilder.append(", ");
        stringBuilder.append(this.backwardFunction);
        stringBuilder.append(")");
        return stringBuilder.toString();
    }
}
