package com.google.common.base;

import java.io.Serializable;
import javax.annotation.Nullable;

class Predicates$NotPredicate<T> implements Predicate<T>, Serializable {
    private static final long serialVersionUID = 0;
    final Predicate<T> predicate;

    Predicates$NotPredicate(Predicate<T> predicate) {
        this.predicate = (Predicate) Preconditions.checkNotNull(predicate);
    }

    public boolean apply(@Nullable T t) {
        return this.predicate.apply(t) ^ 1;
    }

    public int hashCode() {
        return this.predicate.hashCode() ^ -1;
    }

    public boolean equals(@Nullable Object obj) {
        if (!(obj instanceof Predicates$NotPredicate)) {
            return false;
        }
        return this.predicate.equals(((Predicates$NotPredicate) obj).predicate);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Predicates.not(");
        stringBuilder.append(this.predicate);
        stringBuilder.append(")");
        return stringBuilder.toString();
    }
}
