package com.google.common.base;

import java.io.Serializable;
import java.util.List;
import javax.annotation.Nullable;

class Predicates$OrPredicate<T> implements Predicate<T>, Serializable {
    private static final long serialVersionUID = 0;
    private final List<? extends Predicate<? super T>> components;

    private Predicates$OrPredicate(List<? extends Predicate<? super T>> components) {
        this.components = components;
    }

    public boolean apply(@Nullable T t) {
        for (int i = 0; i < this.components.size(); i++) {
            if (((Predicate) this.components.get(i)).apply(t)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return this.components.hashCode() + 87855567;
    }

    public boolean equals(@Nullable Object obj) {
        if (!(obj instanceof Predicates$OrPredicate)) {
            return false;
        }
        return this.components.equals(((Predicates$OrPredicate) obj).components);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Predicates.or(");
        stringBuilder.append(Predicates.access$800().join(this.components));
        stringBuilder.append(")");
        return stringBuilder.toString();
    }
}
