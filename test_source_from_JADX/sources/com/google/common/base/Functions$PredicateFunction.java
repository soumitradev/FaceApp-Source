package com.google.common.base;

import java.io.Serializable;
import javax.annotation.Nullable;

class Functions$PredicateFunction<T> implements Function<T, Boolean>, Serializable {
    private static final long serialVersionUID = 0;
    private final Predicate<T> predicate;

    private Functions$PredicateFunction(Predicate<T> predicate) {
        this.predicate = (Predicate) Preconditions.checkNotNull(predicate);
    }

    public Boolean apply(@Nullable T t) {
        return Boolean.valueOf(this.predicate.apply(t));
    }

    public boolean equals(@Nullable Object obj) {
        if (!(obj instanceof Functions$PredicateFunction)) {
            return false;
        }
        return this.predicate.equals(((Functions$PredicateFunction) obj).predicate);
    }

    public int hashCode() {
        return this.predicate.hashCode();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Functions.forPredicate(");
        stringBuilder.append(this.predicate);
        stringBuilder.append(")");
        return stringBuilder.toString();
    }
}
