package com.google.common.hash;

enum Funnels$UnencodedCharsFunnel implements Funnel<CharSequence> {
    INSTANCE;

    public void funnel(CharSequence from, PrimitiveSink into) {
        into.putUnencodedChars(from);
    }

    public String toString() {
        return "Funnels.unencodedCharsFunnel()";
    }
}
