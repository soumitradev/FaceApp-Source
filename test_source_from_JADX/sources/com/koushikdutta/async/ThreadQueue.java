package com.koushikdutta.async;

import java.util.LinkedList;
import java.util.WeakHashMap;
import java.util.concurrent.Semaphore;

public class ThreadQueue extends LinkedList<Runnable> {
    private static final WeakHashMap<Thread, ThreadQueue> mThreadQueues = new WeakHashMap();
    Semaphore queueSemaphore = new Semaphore(0);
    AsyncSemaphore waiter;

    static ThreadQueue getOrCreateThreadQueue(Thread thread) {
        ThreadQueue queue;
        synchronized (mThreadQueues) {
            queue = (ThreadQueue) mThreadQueues.get(thread);
            if (queue == null) {
                queue = new ThreadQueue();
                mThreadQueues.put(thread, queue);
            }
        }
        return queue;
    }

    static void release(AsyncSemaphore semaphore) {
        synchronized (mThreadQueues) {
            for (ThreadQueue threadQueue : mThreadQueues.values()) {
                if (threadQueue.waiter == semaphore) {
                    threadQueue.queueSemaphore.release();
                }
            }
        }
    }

    public boolean add(Runnable object) {
        boolean add;
        synchronized (this) {
            add = super.add(object);
        }
        return add;
    }

    public boolean remove(Object object) {
        boolean remove;
        synchronized (this) {
            remove = super.remove(object);
        }
        return remove;
    }

    public Runnable remove() {
        synchronized (this) {
            if (isEmpty()) {
                return null;
            }
            Runnable runnable = (Runnable) super.remove();
            return runnable;
        }
    }
}
