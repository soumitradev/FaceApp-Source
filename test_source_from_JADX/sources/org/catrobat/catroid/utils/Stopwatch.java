package org.catrobat.catroid.utils;

public class Stopwatch {
    private long start;

    public void start() {
        this.start = System.currentTimeMillis();
    }

    public long getElapsedMilliseconds() {
        return System.currentTimeMillis() - this.start;
    }
}
