package com.google.common.reflect;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.concurrent.atomic.AtomicReference;

class TypeToken$1 extends TypeVisitor {
    final /* synthetic */ Class val$declaringClass;
    final /* synthetic */ AtomicReference val$result;

    TypeToken$1(Class cls, AtomicReference atomicReference) {
        this.val$declaringClass = cls;
        this.val$result = atomicReference;
    }

    void visitTypeVariable(TypeVariable<?> var) {
        if (var.getGenericDeclaration() == this.val$declaringClass) {
            this.val$result.set(Types.subtypeOf(Object.class));
        }
    }

    void visitParameterizedType(ParameterizedType pt) {
        this.val$result.set(Types.newParameterizedTypeWithOwner(this.val$declaringClass.getEnclosingClass() == null ? pt.getOwnerType() : TypeToken.access$000(pt.getOwnerType(), this.val$declaringClass.getEnclosingClass()), (Class) pt.getRawType(), TypeToken.access$100(pt.getActualTypeArguments(), this.val$declaringClass)));
    }

    void visitWildcardType(WildcardType t) {
    }

    void visitGenericArrayType(GenericArrayType t) {
    }

    void visitClass(Class<?> cls) {
    }
}
