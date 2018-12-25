package com.google.common.reflect;

import com.google.common.collect.Sets;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.Set;
import javax.annotation.concurrent.NotThreadSafe;

@NotThreadSafe
abstract class TypeVisitor {
    private final Set<Type> visited = Sets.newHashSet();

    TypeVisitor() {
    }

    public final void visit(Type... types) {
        for (Type type : types) {
            if (type != null) {
                if (this.visited.add(type)) {
                    boolean succeeded = false;
                    try {
                        if (type instanceof TypeVariable) {
                            visitTypeVariable((TypeVariable) type);
                        } else if (type instanceof WildcardType) {
                            visitWildcardType((WildcardType) type);
                        } else if (type instanceof ParameterizedType) {
                            visitParameterizedType((ParameterizedType) type);
                        } else if (type instanceof Class) {
                            visitClass((Class) type);
                        } else if (type instanceof GenericArrayType) {
                            visitGenericArrayType((GenericArrayType) type);
                        } else {
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("Unknown type: ");
                            stringBuilder.append(type);
                            throw new AssertionError(stringBuilder.toString());
                        }
                        if (!true) {
                            this.visited.remove(type);
                        }
                    } catch (Throwable th) {
                        if (!succeeded) {
                            this.visited.remove(type);
                        }
                    }
                }
            }
        }
    }

    void visitClass(Class<?> cls) {
    }

    void visitGenericArrayType(GenericArrayType t) {
    }

    void visitParameterizedType(ParameterizedType t) {
    }

    void visitTypeVariable(TypeVariable<?> typeVariable) {
    }

    void visitWildcardType(WildcardType t) {
    }
}
