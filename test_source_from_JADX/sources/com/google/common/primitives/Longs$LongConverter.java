package com.google.common.primitives;

import com.google.common.base.Converter;
import java.io.Serializable;

final class Longs$LongConverter extends Converter<String, Long> implements Serializable {
    static final Longs$LongConverter INSTANCE = new Longs$LongConverter();
    private static final long serialVersionUID = 1;

    private Longs$LongConverter() {
    }

    protected Long doForward(String value) {
        return Long.decode(value);
    }

    protected String doBackward(Long value) {
        return value.toString();
    }

    public String toString() {
        return "Longs.stringConverter()";
    }

    private Object readResolve() {
        return INSTANCE;
    }
}
