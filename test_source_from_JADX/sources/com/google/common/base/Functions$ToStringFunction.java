package com.google.common.base;

enum Functions$ToStringFunction implements Function<Object, String> {
    INSTANCE;

    public String apply(Object o) {
        Preconditions.checkNotNull(o);
        return o.toString();
    }

    public String toString() {
        return "Functions.toStringFunction()";
    }
}
