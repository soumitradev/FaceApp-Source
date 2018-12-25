package com.google.common.hash;

enum Funnels$LongFunnel implements Funnel<Long> {
    INSTANCE;

    public void funnel(Long from, PrimitiveSink into) {
        into.putLong(from.longValue());
    }

    public String toString() {
        return "Funnels.longFunnel()";
    }
}
