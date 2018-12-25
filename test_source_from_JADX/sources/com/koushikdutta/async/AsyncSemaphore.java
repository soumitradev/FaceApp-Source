package com.koushikdutta.async;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class AsyncSemaphore {
    Semaphore semaphore = new Semaphore(0);

    public void acquire() throws InterruptedException {
        ThreadQueue threadQueue = ThreadQueue.getOrCreateThreadQueue(Thread.currentThread());
        AsyncSemaphore last = threadQueue.waiter;
        threadQueue.waiter = this;
        Semaphore queueSemaphore = threadQueue.queueSemaphore;
        if (!this.semaphore.tryAcquire()) {
            while (true) {
                try {
                    Runnable run = threadQueue.remove();
                    if (run == null) {
                        queueSemaphore.acquire(Math.max(1, queueSemaphore.availablePermits()));
                        if (this.semaphore.tryAcquire()) {
                            threadQueue.waiter = last;
                            return;
                        }
                    } else {
                        run.run();
                    }
                } finally {
                    threadQueue.waiter = last;
                }
            }
        }
    }

    public boolean tryAcquire(long timeout, TimeUnit timeunit) throws InterruptedException {
        long timeoutMs = TimeUnit.MILLISECONDS.convert(timeout, timeunit);
        ThreadQueue threadQueue = ThreadQueue.getOrCreateThreadQueue(Thread.currentThread());
        AsyncSemaphore last = threadQueue.waiter;
        threadQueue.waiter = this;
        Semaphore queueSemaphore = threadQueue.queueSemaphore;
        if (r1.semaphore.tryAcquire()) {
            threadQueue.waiter = last;
            return true;
        }
        long start = System.currentTimeMillis();
        while (true) {
            Runnable run = threadQueue.remove();
            if (run != null) {
                try {
                    run.run();
                } catch (Throwable th) {
                    Throwable th2 = th;
                    threadQueue.waiter = last;
                }
            } else if (!queueSemaphore.tryAcquire(Math.max(1, queueSemaphore.availablePermits()), timeoutMs, TimeUnit.MILLISECONDS)) {
                threadQueue.waiter = last;
                return false;
            } else if (r1.semaphore.tryAcquire()) {
                threadQueue.waiter = last;
                return true;
            } else if (System.currentTimeMillis() - start >= timeoutMs) {
                threadQueue.waiter = last;
                return false;
            }
        }
    }

    public void release() {
        this.semaphore.release();
        ThreadQueue.release(this);
    }
}
