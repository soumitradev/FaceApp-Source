package com.google.common.util.concurrent;

import com.google.common.base.Preconditions;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.Executor;
import java.util.logging.Logger;
import javax.annotation.concurrent.GuardedBy;

final class SerializingExecutor implements Executor {
    private static final Logger log = Logger.getLogger(SerializingExecutor.class.getName());
    private final Executor executor;
    private final Object internalLock = new Object();
    @GuardedBy("internalLock")
    private boolean isWorkerRunning = false;
    @GuardedBy("internalLock")
    private final Deque<Runnable> queue = new ArrayDeque();
    @GuardedBy("internalLock")
    private int suspensions = 0;

    private final class QueueWorker implements Runnable {
        private QueueWorker() {
        }

        public void run() {
            try {
                workOnQueue();
            } catch (Error e) {
                synchronized (SerializingExecutor.this.internalLock) {
                    SerializingExecutor.this.isWorkerRunning = false;
                    throw e;
                }
            }
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private void workOnQueue() {
            /*
            r6 = this;
        L_0x0000:
            r0 = 0;
            r1 = com.google.common.util.concurrent.SerializingExecutor.this;
            r1 = r1.internalLock;
            monitor-enter(r1);
            r2 = com.google.common.util.concurrent.SerializingExecutor.this;	 Catch:{ all -> 0x0048 }
            r2 = r2.suspensions;	 Catch:{ all -> 0x0048 }
            if (r2 != 0) goto L_0x001d;
        L_0x0010:
            r2 = com.google.common.util.concurrent.SerializingExecutor.this;	 Catch:{ all -> 0x0048 }
            r2 = r2.queue;	 Catch:{ all -> 0x0048 }
            r2 = r2.poll();	 Catch:{ all -> 0x0048 }
            r2 = (java.lang.Runnable) r2;	 Catch:{ all -> 0x0048 }
            r0 = r2;
        L_0x001d:
            if (r0 != 0) goto L_0x0027;
        L_0x001f:
            r2 = com.google.common.util.concurrent.SerializingExecutor.this;	 Catch:{ all -> 0x0048 }
            r3 = 0;
            r2.isWorkerRunning = r3;	 Catch:{ all -> 0x0048 }
            monitor-exit(r1);	 Catch:{ all -> 0x0048 }
            return;
        L_0x0027:
            monitor-exit(r1);	 Catch:{ all -> 0x0048 }
            r0.run();	 Catch:{ RuntimeException -> 0x002c }
            goto L_0x0047;
        L_0x002c:
            r1 = move-exception;
            r2 = com.google.common.util.concurrent.SerializingExecutor.log;
            r3 = java.util.logging.Level.SEVERE;
            r4 = new java.lang.StringBuilder;
            r4.<init>();
            r5 = "Exception while executing runnable ";
            r4.append(r5);
            r4.append(r0);
            r4 = r4.toString();
            r2.log(r3, r4, r1);
        L_0x0047:
            goto L_0x0000;
        L_0x0048:
            r2 = move-exception;
            monitor-exit(r1);	 Catch:{ all -> 0x0048 }
            throw r2;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.common.util.concurrent.SerializingExecutor.QueueWorker.workOnQueue():void");
        }
    }

    public SerializingExecutor(Executor executor) {
        this.executor = (Executor) Preconditions.checkNotNull(executor);
    }

    public void execute(Runnable task) {
        synchronized (this.internalLock) {
            this.queue.add(task);
        }
        startQueueWorker();
    }

    public void executeFirst(Runnable task) {
        synchronized (this.internalLock) {
            this.queue.addFirst(task);
        }
        startQueueWorker();
    }

    public void suspend() {
        synchronized (this.internalLock) {
            this.suspensions++;
        }
    }

    public void resume() {
        synchronized (this.internalLock) {
            Preconditions.checkState(this.suspensions > 0);
            this.suspensions--;
        }
        startQueueWorker();
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void startQueueWorker() {
        /*
        r5 = this;
        r0 = r5.internalLock;
        monitor-enter(r0);
        r1 = r5.queue;	 Catch:{ all -> 0x0046 }
        r1 = r1.peek();	 Catch:{ all -> 0x0046 }
        if (r1 != 0) goto L_0x000d;
    L_0x000b:
        monitor-exit(r0);	 Catch:{ all -> 0x0046 }
        return;
    L_0x000d:
        r1 = r5.suspensions;	 Catch:{ all -> 0x0046 }
        if (r1 <= 0) goto L_0x0013;
    L_0x0011:
        monitor-exit(r0);	 Catch:{ all -> 0x0046 }
        return;
    L_0x0013:
        r1 = r5.isWorkerRunning;	 Catch:{ all -> 0x0046 }
        if (r1 == 0) goto L_0x0019;
    L_0x0017:
        monitor-exit(r0);	 Catch:{ all -> 0x0046 }
        return;
    L_0x0019:
        r1 = 1;
        r5.isWorkerRunning = r1;	 Catch:{ all -> 0x0046 }
        monitor-exit(r0);	 Catch:{ all -> 0x0046 }
        r0 = r1;
        r1 = 0;
        r2 = r5.executor;	 Catch:{ all -> 0x0038 }
        r3 = new com.google.common.util.concurrent.SerializingExecutor$QueueWorker;	 Catch:{ all -> 0x0038 }
        r4 = 0;
        r3.<init>();	 Catch:{ all -> 0x0038 }
        r2.execute(r3);	 Catch:{ all -> 0x0038 }
        r0 = 0;
        if (r0 == 0) goto L_0x0037;
    L_0x002d:
        r2 = r5.internalLock;
        monitor-enter(r2);
        r5.isWorkerRunning = r1;	 Catch:{ all -> 0x0034 }
        monitor-exit(r2);	 Catch:{ all -> 0x0034 }
        goto L_0x0037;
    L_0x0034:
        r1 = move-exception;
        monitor-exit(r2);	 Catch:{ all -> 0x0034 }
        throw r1;
    L_0x0037:
        return;
    L_0x0038:
        r2 = move-exception;
        if (r0 == 0) goto L_0x0045;
    L_0x003b:
        r3 = r5.internalLock;
        monitor-enter(r3);
        r5.isWorkerRunning = r1;	 Catch:{ all -> 0x0042 }
        monitor-exit(r3);	 Catch:{ all -> 0x0042 }
        goto L_0x0045;
    L_0x0042:
        r1 = move-exception;
        monitor-exit(r3);	 Catch:{ all -> 0x0042 }
        throw r1;
    L_0x0045:
        throw r2;
    L_0x0046:
        r1 = move-exception;
        monitor-exit(r0);	 Catch:{ all -> 0x0046 }
        throw r1;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.util.concurrent.SerializingExecutor.startQueueWorker():void");
    }
}
