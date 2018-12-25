package com.google.common.base;

import java.util.AbstractList;

class Throwables$1 extends AbstractList<StackTraceElement> {
    final /* synthetic */ Throwable val$t;

    Throwables$1(Throwable th) {
        this.val$t = th;
    }

    public StackTraceElement get(int n) {
        return (StackTraceElement) Throwables.access$200(Throwables.access$000(), Throwables.access$100(), new Object[]{this.val$t, Integer.valueOf(n)});
    }

    public int size() {
        return ((Integer) Throwables.access$200(Throwables.access$300(), Throwables.access$100(), new Object[]{this.val$t})).intValue();
    }
}
