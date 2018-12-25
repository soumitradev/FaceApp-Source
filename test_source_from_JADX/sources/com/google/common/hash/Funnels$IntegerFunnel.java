package com.google.common.hash;

enum Funnels$IntegerFunnel implements Funnel<Integer> {
    INSTANCE;

    public void funnel(Integer from, PrimitiveSink into) {
        into.putInt(from.intValue());
    }

    public String toString() {
        return "Funnels.integerFunnel()";
    }
}
