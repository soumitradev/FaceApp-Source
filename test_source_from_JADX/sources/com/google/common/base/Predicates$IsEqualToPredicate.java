package com.google.common.base;

import java.io.Serializable;
import javax.annotation.Nullable;

class Predicates$IsEqualToPredicate<T> implements Predicate<T>, Serializable {
    private static final long serialVersionUID = 0;
    private final T target;

    private Predicates$IsEqualToPredicate(T target) {
        this.target = target;
    }

    public boolean apply(T t) {
        return this.target.equals(t);
    }

    public int hashCode() {
        return this.target.hashCode();
    }

    public boolean equals(@Nullable Object obj) {
        if (!(obj instanceof Predicates$IsEqualToPredicate)) {
            return false;
        }
        return this.target.equals(((Predicates$IsEqualToPredicate) obj).target);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Predicates.equalTo(");
        stringBuilder.append(this.target);
        stringBuilder.append(")");
        return stringBuilder.toString();
    }
}
