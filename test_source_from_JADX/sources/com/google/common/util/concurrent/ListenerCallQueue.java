package com.google.common.util.concurrent;

import com.google.common.base.Preconditions;
import com.google.common.collect.Queues;
import java.util.Queue;
import java.util.concurrent.Executor;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.concurrent.GuardedBy;

final class ListenerCallQueue<L> implements Runnable {
    private static final Logger logger = Logger.getLogger(ListenerCallQueue.class.getName());
    private final Executor executor;
    @GuardedBy("this")
    private boolean isThreadScheduled;
    private final L listener;
    @GuardedBy("this")
    private final Queue<Callback<L>> waitQueue = Queues.newArrayDeque();

    static abstract class Callback<L> {
        private final String methodCall;

        abstract void call(L l);

        Callback(String methodCall) {
            this.methodCall = methodCall;
        }

        void enqueueOn(Iterable<ListenerCallQueue<L>> queues) {
            for (ListenerCallQueue<L> queue : queues) {
                queue.add(this);
            }
        }
    }

    ListenerCallQueue(L listener, Executor executor) {
        this.listener = Preconditions.checkNotNull(listener);
        this.executor = (Executor) Preconditions.checkNotNull(executor);
    }

    synchronized void add(Callback<L> callback) {
        this.waitQueue.add(callback);
    }

    void execute() {
        boolean scheduleTaskRunner = false;
        synchronized (this) {
            if (!this.isThreadScheduled) {
                this.isThreadScheduled = true;
                scheduleTaskRunner = true;
            }
        }
        if (scheduleTaskRunner) {
            try {
                this.executor.execute(this);
            } catch (RuntimeException e) {
                synchronized (this) {
                    this.isThreadScheduled = false;
                    Logger logger = logger;
                    Level level = Level.SEVERE;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Exception while running callbacks for ");
                    stringBuilder.append(this.listener);
                    stringBuilder.append(" on ");
                    stringBuilder.append(this.executor);
                    logger.log(level, stringBuilder.toString(), e);
                    throw e;
                }
            }
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void run() {
        /*
        r8 = this;
        r0 = 0;
        r1 = 1;
    L_0x0002:
        r2 = 0;
        monitor-enter(r8);	 Catch:{ all -> 0x0055 }
        r3 = r8.isThreadScheduled;	 Catch:{ all -> 0x0052 }
        com.google.common.base.Preconditions.checkState(r3);	 Catch:{ all -> 0x0052 }
        r3 = r8.waitQueue;	 Catch:{ all -> 0x0052 }
        r3 = r3.poll();	 Catch:{ all -> 0x0052 }
        r3 = (com.google.common.util.concurrent.ListenerCallQueue.Callback) r3;	 Catch:{ all -> 0x0052 }
        r0 = r3;
        if (r0 != 0) goto L_0x0023;
    L_0x0014:
        r8.isThreadScheduled = r2;	 Catch:{ all -> 0x0052 }
        r1 = 0;
        monitor-exit(r8);	 Catch:{ all -> 0x0052 }
        if (r1 == 0) goto L_0x0022;
    L_0x001a:
        monitor-enter(r8);
        r8.isThreadScheduled = r2;	 Catch:{ all -> 0x001f }
        monitor-exit(r8);	 Catch:{ all -> 0x001f }
        goto L_0x0022;
    L_0x001f:
        r0 = move-exception;
        monitor-exit(r8);	 Catch:{ all -> 0x001f }
        throw r0;
    L_0x0022:
        return;
    L_0x0023:
        monitor-exit(r8);	 Catch:{ all -> 0x0052 }
        r3 = r8.listener;	 Catch:{ RuntimeException -> 0x002a }
        r0.call(r3);	 Catch:{ RuntimeException -> 0x002a }
        goto L_0x0051;
    L_0x002a:
        r3 = move-exception;
        r4 = logger;	 Catch:{ all -> 0x0055 }
        r5 = java.util.logging.Level.SEVERE;	 Catch:{ all -> 0x0055 }
        r6 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0055 }
        r6.<init>();	 Catch:{ all -> 0x0055 }
        r7 = "Exception while executing callback: ";
        r6.append(r7);	 Catch:{ all -> 0x0055 }
        r7 = r8.listener;	 Catch:{ all -> 0x0055 }
        r6.append(r7);	 Catch:{ all -> 0x0055 }
        r7 = ".";
        r6.append(r7);	 Catch:{ all -> 0x0055 }
        r7 = r0.methodCall;	 Catch:{ all -> 0x0055 }
        r6.append(r7);	 Catch:{ all -> 0x0055 }
        r6 = r6.toString();	 Catch:{ all -> 0x0055 }
        r4.log(r5, r6, r3);	 Catch:{ all -> 0x0055 }
    L_0x0051:
        goto L_0x0002;
    L_0x0052:
        r3 = move-exception;
        monitor-exit(r8);	 Catch:{ all -> 0x0052 }
        throw r3;	 Catch:{ all -> 0x0055 }
    L_0x0055:
        r0 = move-exception;
        if (r1 == 0) goto L_0x0060;
    L_0x0058:
        monitor-enter(r8);
        r8.isThreadScheduled = r2;	 Catch:{ all -> 0x005d }
        monitor-exit(r8);	 Catch:{ all -> 0x005d }
        goto L_0x0060;
    L_0x005d:
        r0 = move-exception;
        monitor-exit(r8);	 Catch:{ all -> 0x005d }
        throw r0;
    L_0x0060:
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.util.concurrent.ListenerCallQueue.run():void");
    }
}
