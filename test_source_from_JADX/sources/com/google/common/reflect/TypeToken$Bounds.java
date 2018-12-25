package com.google.common.reflect;

import java.lang.reflect.Type;

class TypeToken$Bounds {
    private final Type[] bounds;
    private final boolean target;

    TypeToken$Bounds(Type[] bounds, boolean target) {
        this.bounds = bounds;
        this.target = target;
    }

    boolean isSubtypeOf(Type supertype) {
        for (Type bound : this.bounds) {
            if (TypeToken.of(bound).isSubtypeOf(supertype) == this.target) {
                return this.target;
            }
        }
        return this.target ^ 1;
    }

    boolean isSupertypeOf(Type subtype) {
        TypeToken<?> type = TypeToken.of(subtype);
        for (Type bound : this.bounds) {
            if (type.isSubtypeOf(bound) == this.target) {
                return this.target;
            }
        }
        return this.target ^ 1;
    }
}
