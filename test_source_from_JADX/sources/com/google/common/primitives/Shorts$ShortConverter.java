package com.google.common.primitives;

import com.google.common.base.Converter;
import java.io.Serializable;

final class Shorts$ShortConverter extends Converter<String, Short> implements Serializable {
    static final Shorts$ShortConverter INSTANCE = new Shorts$ShortConverter();
    private static final long serialVersionUID = 1;

    private Shorts$ShortConverter() {
    }

    protected Short doForward(String value) {
        return Short.decode(value);
    }

    protected String doBackward(Short value) {
        return value.toString();
    }

    public String toString() {
        return "Shorts.stringConverter()";
    }

    private Object readResolve() {
        return INSTANCE;
    }
}
