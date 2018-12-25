package com.google.common.base;

import java.io.Serializable;

final class Equivalence$Equals extends Equivalence<Object> implements Serializable {
    static final Equivalence$Equals INSTANCE = new Equivalence$Equals();
    private static final long serialVersionUID = 1;

    Equivalence$Equals() {
    }

    protected boolean doEquivalent(Object a, Object b) {
        return a.equals(b);
    }

    protected int doHash(Object o) {
        return o.hashCode();
    }

    private Object readResolve() {
        return INSTANCE;
    }
}
