package com.google.common.base;

import java.io.Serializable;
import java.util.List;
import javax.annotation.Nullable;

class Predicates$AndPredicate<T> implements Predicate<T>, Serializable {
    private static final long serialVersionUID = 0;
    private final List<? extends Predicate<? super T>> components;

    private Predicates$AndPredicate(List<? extends Predicate<? super T>> components) {
        this.components = components;
    }

    public boolean apply(@Nullable T t) {
        for (int i = 0; i < this.components.size(); i++) {
            if (!((Predicate) this.components.get(i)).apply(t)) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        return this.components.hashCode() + 306654252;
    }

    public boolean equals(@Nullable Object obj) {
        if (!(obj instanceof Predicates$AndPredicate)) {
            return false;
        }
        return this.components.equals(((Predicates$AndPredicate) obj).components);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Predicates.and(");
        stringBuilder.append(Predicates.access$800().join(this.components));
        stringBuilder.append(")");
        return stringBuilder.toString();
    }
}
