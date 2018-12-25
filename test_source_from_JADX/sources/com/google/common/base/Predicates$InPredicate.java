package com.google.common.base;

import java.io.Serializable;
import java.util.Collection;
import javax.annotation.Nullable;

class Predicates$InPredicate<T> implements Predicate<T>, Serializable {
    private static final long serialVersionUID = 0;
    private final Collection<?> target;

    private Predicates$InPredicate(Collection<?> target) {
        this.target = (Collection) Preconditions.checkNotNull(target);
    }

    public boolean apply(@Nullable T t) {
        try {
            return this.target.contains(t);
        } catch (NullPointerException e) {
            return false;
        } catch (ClassCastException e2) {
            return false;
        }
    }

    public boolean equals(@Nullable Object obj) {
        if (!(obj instanceof Predicates$InPredicate)) {
            return false;
        }
        return this.target.equals(((Predicates$InPredicate) obj).target);
    }

    public int hashCode() {
        return this.target.hashCode();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Predicates.in(");
        stringBuilder.append(this.target);
        stringBuilder.append(")");
        return stringBuilder.toString();
    }
}
