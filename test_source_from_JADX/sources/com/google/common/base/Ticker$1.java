package com.google.common.base;

class Ticker$1 extends Ticker {
    Ticker$1() {
    }

    public long read() {
        return Platform.systemNanoTime();
    }
}
