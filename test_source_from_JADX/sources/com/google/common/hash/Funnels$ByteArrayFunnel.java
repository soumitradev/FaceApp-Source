package com.google.common.hash;

enum Funnels$ByteArrayFunnel implements Funnel<byte[]> {
    INSTANCE;

    public void funnel(byte[] from, PrimitiveSink into) {
        into.putBytes(from);
    }

    public String toString() {
        return "Funnels.byteArrayFunnel()";
    }
}
