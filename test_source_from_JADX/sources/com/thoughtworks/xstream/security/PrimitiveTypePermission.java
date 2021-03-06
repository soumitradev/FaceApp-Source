package com.thoughtworks.xstream.security;

import com.thoughtworks.xstream.core.util.Primitives;

public class PrimitiveTypePermission implements TypePermission {
    public static final TypePermission PRIMITIVES = new PrimitiveTypePermission();

    public boolean allows(Class type) {
        return (type != null && type.isPrimitive()) || Primitives.isBoxed(type);
    }

    public int hashCode() {
        return 7;
    }

    public boolean equals(Object obj) {
        return obj != null && obj.getClass() == PrimitiveTypePermission.class;
    }
}
