package com.google.common.util.concurrent;

import com.google.common.annotations.GwtIncompatible;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;
import javax.annotation.concurrent.GuardedBy;

@GwtIncompatible("TODO")
final class MoreExecutors$DirectExecutorService extends AbstractListeningExecutorService {
    private final Object lock;
    @GuardedBy("lock")
    private int runningTasks;
    @GuardedBy("lock")
    private boolean shutdown;

    private MoreExecutors$DirectExecutorService() {
        this.lock = new Object();
        this.runningTasks = 0;
        this.shutdown = false;
    }

    public void execute(Runnable command) {
        startTask();
        try {
            command.run();
        } finally {
            endTask();
        }
    }

    public boolean isShutdown() {
        boolean z;
        synchronized (this.lock) {
            z = this.shutdown;
        }
        return z;
    }

    public void shutdown() {
        synchronized (this.lock) {
            this.shutdown = true;
            if (this.runningTasks == 0) {
                this.lock.notifyAll();
            }
        }
    }

    public List<Runnable> shutdownNow() {
        shutdown();
        return Collections.emptyList();
    }

    public boolean isTerminated() {
        boolean z;
        synchronized (this.lock) {
            z = this.shutdown && this.runningTasks == 0;
        }
        return z;
    }

    public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
        long nanos = unit.toNanos(timeout);
        synchronized (this.lock) {
            while (true) {
                if (this.shutdown && this.runningTasks == 0) {
                    return true;
                } else if (nanos <= 0) {
                    return false;
                } else {
                    long now = System.nanoTime();
                    TimeUnit.NANOSECONDS.timedWait(this.lock, nanos);
                    nanos -= System.nanoTime() - now;
                }
            }
        }
    }

    private void startTask() {
        synchronized (this.lock) {
            if (this.shutdown) {
                throw new RejectedExecutionException("Executor already shutdown");
            }
            this.runningTasks++;
        }
    }

    private void endTask() {
        synchronized (this.lock) {
            int numRunning = this.runningTasks - 1;
            this.runningTasks = numRunning;
            if (numRunning == 0) {
                this.lock.notifyAll();
            }
        }
    }
}
