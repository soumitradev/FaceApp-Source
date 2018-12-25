package com.google.common.base;

import com.google.common.annotations.GwtIncompatible;
import java.io.Serializable;
import javax.annotation.Nullable;

@GwtIncompatible("Class.isInstance")
class Predicates$InstanceOfPredicate implements Predicate<Object>, Serializable {
    private static final long serialVersionUID = 0;
    private final Class<?> clazz;

    private Predicates$InstanceOfPredicate(Class<?> clazz) {
        this.clazz = (Class) Preconditions.checkNotNull(clazz);
    }

    public boolean apply(@Nullable Object o) {
        return this.clazz.isInstance(o);
    }

    public int hashCode() {
        return this.clazz.hashCode();
    }

    public boolean equals(@Nullable Object obj) {
        boolean z = false;
        if (!(obj instanceof Predicates$InstanceOfPredicate)) {
            return false;
        }
        if (this.clazz == ((Predicates$InstanceOfPredicate) obj).clazz) {
            z = true;
        }
        return z;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Predicates.instanceOf(");
        stringBuilder.append(this.clazz.getName());
        stringBuilder.append(")");
        return stringBuilder.toString();
    }
}
