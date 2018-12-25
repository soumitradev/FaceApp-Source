package com.google.common.util.concurrent;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import java.util.concurrent.Executor;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.concurrent.GuardedBy;

public final class ExecutionList {
    @VisibleForTesting
    static final Logger log = Logger.getLogger(ExecutionList.class.getName());
    @GuardedBy("this")
    private boolean executed;
    @GuardedBy("this")
    private ExecutionList$RunnableExecutorPair runnables;

    public void add(Runnable runnable, Executor executor) {
        Preconditions.checkNotNull(runnable, "Runnable was null.");
        Preconditions.checkNotNull(executor, "Executor was null.");
        synchronized (this) {
            if (this.executed) {
                executeListener(runnable, executor);
                return;
            }
            this.runnables = new ExecutionList$RunnableExecutorPair(runnable, executor, this.runnables);
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void execute() {
        /*
        r5 = this;
        monitor-enter(r5);
        r0 = 0;
        r1 = r5.executed;	 Catch:{ all -> 0x002c }
        if (r1 == 0) goto L_0x0008;
    L_0x0006:
        monitor-exit(r5);	 Catch:{ all -> 0x002c }
        return;
    L_0x0008:
        r1 = 1;
        r5.executed = r1;	 Catch:{ all -> 0x002c }
        r1 = r5.runnables;	 Catch:{ all -> 0x002c }
        r5.runnables = r0;	 Catch:{ all -> 0x0027 }
        monitor-exit(r5);	 Catch:{ all -> 0x0027 }
    L_0x0011:
        if (r1 == 0) goto L_0x001a;
    L_0x0013:
        r2 = r1;
        r1 = r1.next;
        r2.next = r0;
        r0 = r2;
        goto L_0x0011;
    L_0x001a:
        if (r0 == 0) goto L_0x0026;
    L_0x001c:
        r2 = r0.runnable;
        r3 = r0.executor;
        executeListener(r2, r3);
        r0 = r0.next;
        goto L_0x001a;
    L_0x0026:
        return;
    L_0x0027:
        r0 = move-exception;
        r4 = r1;
        r1 = r0;
        r0 = r4;
        goto L_0x002d;
    L_0x002c:
        r1 = move-exception;
    L_0x002d:
        monitor-exit(r5);	 Catch:{ all -> 0x002c }
        throw r1;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.util.concurrent.ExecutionList.execute():void");
    }

    private static void executeListener(Runnable runnable, Executor executor) {
        try {
            executor.execute(runnable);
        } catch (RuntimeException e) {
            Logger logger = log;
            Level level = Level.SEVERE;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("RuntimeException while executing runnable ");
            stringBuilder.append(runnable);
            stringBuilder.append(" with executor ");
            stringBuilder.append(executor);
            logger.log(level, stringBuilder.toString(), e);
        }
    }
}
