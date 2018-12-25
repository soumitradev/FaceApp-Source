package com.google.common.reflect;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

class TypeToken$2 extends MethodInvokable<T> {
    final /* synthetic */ TypeToken this$0;

    TypeToken$2(TypeToken typeToken, Method x0) {
        this.this$0 = typeToken;
        super(x0);
    }

    Type getGenericReturnType() {
        return this.this$0.resolveType(super.getGenericReturnType()).getType();
    }

    Type[] getGenericParameterTypes() {
        return TypeToken.access$200(this.this$0, super.getGenericParameterTypes());
    }

    Type[] getGenericExceptionTypes() {
        return TypeToken.access$200(this.this$0, super.getGenericExceptionTypes());
    }

    public TypeToken<T> getOwnerType() {
        return this.this$0;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getOwnerType());
        stringBuilder.append(".");
        stringBuilder.append(super.toString());
        return stringBuilder.toString();
    }
}
