package com.google.common.base;

import com.google.common.annotations.GwtIncompatible;
import java.io.Serializable;
import javax.annotation.Nullable;

@GwtIncompatible("Class.isAssignableFrom")
class Predicates$AssignableFromPredicate implements Predicate<Class<?>>, Serializable {
    private static final long serialVersionUID = 0;
    private final Class<?> clazz;

    private Predicates$AssignableFromPredicate(Class<?> clazz) {
        this.clazz = (Class) Preconditions.checkNotNull(clazz);
    }

    public boolean apply(Class<?> input) {
        return this.clazz.isAssignableFrom(input);
    }

    public int hashCode() {
        return this.clazz.hashCode();
    }

    public boolean equals(@Nullable Object obj) {
        boolean z = false;
        if (!(obj instanceof Predicates$AssignableFromPredicate)) {
            return false;
        }
        if (this.clazz == ((Predicates$AssignableFromPredicate) obj).clazz) {
            z = true;
        }
        return z;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Predicates.assignableFrom(");
        stringBuilder.append(this.clazz.getName());
        stringBuilder.append(")");
        return stringBuilder.toString();
    }
}
