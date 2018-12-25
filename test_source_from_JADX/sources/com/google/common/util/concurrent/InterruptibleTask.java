package com.google.common.util.concurrent;

import com.google.common.annotations.GwtCompatible;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

@GwtCompatible(emulated = true)
abstract class InterruptibleTask implements Runnable {
    private static final AtomicReferenceFieldUpdater<InterruptibleTask, Thread> RUNNER = AtomicReferenceFieldUpdater.newUpdater(InterruptibleTask.class, Thread.class, "runner");
    private volatile boolean doneInterrupting;
    private volatile Thread runner;

    abstract void runInterruptibly();

    abstract boolean wasInterrupted();

    InterruptibleTask() {
    }

    public final void run() {
        if (RUNNER.compareAndSet(this, null, Thread.currentThread())) {
            try {
                runInterruptibly();
                if (wasInterrupted()) {
                    while (!this.doneInterrupting) {
                        Thread.yield();
                    }
                }
            } catch (Throwable th) {
                if (wasInterrupted()) {
                    while (!this.doneInterrupting) {
                        Thread.yield();
                    }
                }
            }
        }
    }

    final void interruptTask() {
        Thread currentRunner = this.runner;
        if (currentRunner != null) {
            currentRunner.interrupt();
        }
        this.doneInterrupting = true;
    }
}
