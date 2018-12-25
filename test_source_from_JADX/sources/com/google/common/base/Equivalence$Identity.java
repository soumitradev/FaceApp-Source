package com.google.common.base;

import java.io.Serializable;

final class Equivalence$Identity extends Equivalence<Object> implements Serializable {
    static final Equivalence$Identity INSTANCE = new Equivalence$Identity();
    private static final long serialVersionUID = 1;

    Equivalence$Identity() {
    }

    protected boolean doEquivalent(Object a, Object b) {
        return false;
    }

    protected int doHash(Object o) {
        return System.identityHashCode(o);
    }

    private Object readResolve() {
        return INSTANCE;
    }
}
