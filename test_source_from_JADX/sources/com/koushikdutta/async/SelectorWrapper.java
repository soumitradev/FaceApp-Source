package com.koushikdutta.async;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Set;
import java.util.concurrent.Semaphore;

public class SelectorWrapper {
    boolean isWaking;
    private Selector selector;
    Semaphore semaphore = new Semaphore(0);

    public Selector getSelector() {
        return this.selector;
    }

    public SelectorWrapper(Selector selector) {
        this.selector = selector;
    }

    public int selectNow() throws IOException {
        return this.selector.selectNow();
    }

    public void select() throws IOException {
        select(0);
    }

    public void select(long timeout) throws IOException {
        try {
            this.semaphore.drainPermits();
            this.selector.select(timeout);
        } finally {
            this.semaphore.release(Integer.MAX_VALUE);
        }
    }

    public Set<SelectionKey> keys() {
        return this.selector.keys();
    }

    public Set<SelectionKey> selectedKeys() {
        return this.selector.selectedKeys();
    }

    public void close() throws IOException {
        this.selector.close();
    }

    public boolean isOpen() {
        return this.selector.isOpen();
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void wakeupOnce() {
        /*
        r7 = this;
        r0 = r7.semaphore;
        r0 = r0.tryAcquire();
        r1 = 1;
        r0 = r0 ^ r1;
        r2 = r7.selector;
        r2.wakeup();
        if (r0 == 0) goto L_0x0010;
    L_0x000f:
        return;
    L_0x0010:
        monitor-enter(r7);
        r2 = r7.isWaking;	 Catch:{ all -> 0x0051 }
        if (r2 == 0) goto L_0x0017;
    L_0x0015:
        monitor-exit(r7);	 Catch:{ all -> 0x0051 }
        return;
    L_0x0017:
        r7.isWaking = r1;	 Catch:{ all -> 0x0051 }
        monitor-exit(r7);	 Catch:{ all -> 0x0051 }
        r1 = 0;
        r2 = 0;
    L_0x001c:
        r3 = 100;
        if (r2 >= r3) goto L_0x0048;
    L_0x0020:
        r3 = r7.semaphore;	 Catch:{ InterruptedException -> 0x0037 }
        r4 = 10;
        r6 = java.util.concurrent.TimeUnit.MILLISECONDS;	 Catch:{ InterruptedException -> 0x0037 }
        r3 = r3.tryAcquire(r4, r6);	 Catch:{ InterruptedException -> 0x0037 }
        if (r3 == 0) goto L_0x0034;
    L_0x002c:
        monitor-enter(r7);
        r7.isWaking = r1;	 Catch:{ all -> 0x0031 }
        monitor-exit(r7);	 Catch:{ all -> 0x0031 }
        return;
    L_0x0031:
        r1 = move-exception;
        monitor-exit(r7);	 Catch:{ all -> 0x0031 }
        throw r1;
    L_0x0034:
        goto L_0x0038;
    L_0x0035:
        r2 = move-exception;
        goto L_0x0040;
    L_0x0037:
        r3 = move-exception;
    L_0x0038:
        r3 = r7.selector;	 Catch:{ all -> 0x0035 }
        r3.wakeup();	 Catch:{ all -> 0x0035 }
        r2 = r2 + 1;
        goto L_0x001c;
    L_0x0040:
        monitor-enter(r7);
        r7.isWaking = r1;	 Catch:{ all -> 0x0045 }
        monitor-exit(r7);	 Catch:{ all -> 0x0045 }
        throw r2;
    L_0x0045:
        r1 = move-exception;
        monitor-exit(r7);	 Catch:{ all -> 0x0045 }
        throw r1;
    L_0x0048:
        monitor-enter(r7);
        r7.isWaking = r1;	 Catch:{ all -> 0x004e }
        monitor-exit(r7);	 Catch:{ all -> 0x004e }
        return;
    L_0x004e:
        r1 = move-exception;
        monitor-exit(r7);	 Catch:{ all -> 0x004e }
        throw r1;
    L_0x0051:
        r1 = move-exception;
        monitor-exit(r7);	 Catch:{ all -> 0x0051 }
        throw r1;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.koushikdutta.async.SelectorWrapper.wakeupOnce():void");
    }
}
