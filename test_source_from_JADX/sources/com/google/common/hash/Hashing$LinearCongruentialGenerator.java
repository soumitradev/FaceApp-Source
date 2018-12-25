package com.google.common.hash;

final class Hashing$LinearCongruentialGenerator {
    private long state;

    public Hashing$LinearCongruentialGenerator(long seed) {
        this.state = seed;
    }

    public double nextDouble() {
        this.state = (this.state * 2862933555777941757L) + 1;
        return ((double) (((int) (this.state >>> 33)) + 1)) / 2.147483648E9d;
    }
}
