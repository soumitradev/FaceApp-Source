package com.google.common.base;

import java.io.Serializable;
import javax.annotation.Nullable;

final class Enums$StringConverter<T extends Enum<T>> extends Converter<String, T> implements Serializable {
    private static final long serialVersionUID = 0;
    private final Class<T> enumClass;

    Enums$StringConverter(Class<T> enumClass) {
        this.enumClass = (Class) Preconditions.checkNotNull(enumClass);
    }

    protected T doForward(String value) {
        return Enum.valueOf(this.enumClass, value);
    }

    protected String doBackward(T enumValue) {
        return enumValue.name();
    }

    public boolean equals(@Nullable Object object) {
        if (!(object instanceof Enums$StringConverter)) {
            return false;
        }
        return this.enumClass.equals(((Enums$StringConverter) object).enumClass);
    }

    public int hashCode() {
        return this.enumClass.hashCode();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Enums.stringConverter(");
        stringBuilder.append(this.enumClass.getName());
        stringBuilder.append(".class)");
        return stringBuilder.toString();
    }
}
