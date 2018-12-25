package com.google.common.reflect;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;

class TypeToken$4 extends TypeVisitor {
    final /* synthetic */ TypeToken this$0;

    TypeToken$4(TypeToken typeToken) {
        this.this$0 = typeToken;
    }

    void visitTypeVariable(TypeVariable<?> typeVariable) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(TypeToken.access$600(this.this$0));
        stringBuilder.append("contains a type variable and is not safe for the operation");
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    void visitWildcardType(WildcardType type) {
        visit(type.getLowerBounds());
        visit(type.getUpperBounds());
    }

    void visitParameterizedType(ParameterizedType type) {
        visit(type.getActualTypeArguments());
        visit(type.getOwnerType());
    }

    void visitGenericArrayType(GenericArrayType type) {
        visit(type.getGenericComponentType());
    }
}
