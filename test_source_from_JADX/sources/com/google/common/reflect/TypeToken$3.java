package com.google.common.reflect;

import com.google.common.base.Joiner;
import java.lang.reflect.Constructor;
import java.lang.reflect.Type;
import org.catrobat.catroid.common.Constants;

class TypeToken$3 extends ConstructorInvokable<T> {
    final /* synthetic */ TypeToken this$0;

    TypeToken$3(TypeToken typeToken, Constructor x0) {
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
        stringBuilder.append(Constants.OPENING_BRACE);
        stringBuilder.append(Joiner.on(", ").join(getGenericParameterTypes()));
        stringBuilder.append(")");
        return stringBuilder.toString();
    }
}
