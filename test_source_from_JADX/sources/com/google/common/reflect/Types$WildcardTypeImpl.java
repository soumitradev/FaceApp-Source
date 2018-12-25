package com.google.common.reflect;

import com.google.common.collect.ImmutableList;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;
import java.util.Arrays;
import java.util.Iterator;

final class Types$WildcardTypeImpl implements WildcardType, Serializable {
    private static final long serialVersionUID = 0;
    private final ImmutableList<Type> lowerBounds;
    private final ImmutableList<Type> upperBounds;

    Types$WildcardTypeImpl(Type[] lowerBounds, Type[] upperBounds) {
        Types.access$200(lowerBounds, "lower bound for wildcard");
        Types.access$200(upperBounds, "upper bound for wildcard");
        this.lowerBounds = Types$JavaVersion.CURRENT.usedInGenericType(lowerBounds);
        this.upperBounds = Types$JavaVersion.CURRENT.usedInGenericType(upperBounds);
    }

    public Type[] getLowerBounds() {
        return Types.access$300(this.lowerBounds);
    }

    public Type[] getUpperBounds() {
        return Types.access$300(this.upperBounds);
    }

    public boolean equals(Object obj) {
        boolean z = false;
        if (!(obj instanceof WildcardType)) {
            return false;
        }
        WildcardType that = (WildcardType) obj;
        if (this.lowerBounds.equals(Arrays.asList(that.getLowerBounds())) && this.upperBounds.equals(Arrays.asList(that.getUpperBounds()))) {
            z = true;
        }
        return z;
    }

    public int hashCode() {
        return this.lowerBounds.hashCode() ^ this.upperBounds.hashCode();
    }

    public String toString() {
        StringBuilder builder = new StringBuilder("?");
        Iterator i$ = this.lowerBounds.iterator();
        while (i$.hasNext()) {
            Type lowerBound = (Type) i$.next();
            builder.append(" super ");
            builder.append(Types$JavaVersion.CURRENT.typeName(lowerBound));
        }
        for (Type lowerBound2 : Types.access$700(this.upperBounds)) {
            builder.append(" extends ");
            builder.append(Types$JavaVersion.CURRENT.typeName(lowerBound2));
        }
        return builder.toString();
    }
}
