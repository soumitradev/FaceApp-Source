package com.koushikdutta.async.future;

public class SimpleCancellable implements DependentCancellable {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final Cancellable COMPLETED = new C13501();
    boolean cancelled;
    boolean complete;
    private Cancellable parent;

    /* renamed from: com.koushikdutta.async.future.SimpleCancellable$1 */
    static class C13501 extends SimpleCancellable {
        C13501() {
            setComplete();
        }

        public /* bridge */ /* synthetic */ DependentCancellable setParent(Cancellable cancellable) {
            return super.setParent(cancellable);
        }
    }

    public boolean isDone() {
        return this.complete;
    }

    protected void cancelCleanup() {
    }

    protected void cleanup() {
    }

    protected void completeCleanup() {
    }

    public boolean setComplete() {
        synchronized (this) {
            if (this.cancelled) {
                return false;
            } else if (this.complete) {
                return true;
            } else {
                this.complete = true;
                this.parent = null;
                completeCleanup();
                cleanup();
                return true;
            }
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean cancel() {
        /*
        r3 = this;
        monitor-enter(r3);
        r0 = r3.complete;	 Catch:{ all -> 0x0023 }
        if (r0 == 0) goto L_0x0008;
    L_0x0005:
        r0 = 0;
        monitor-exit(r3);	 Catch:{ all -> 0x0023 }
        return r0;
    L_0x0008:
        r0 = r3.cancelled;	 Catch:{ all -> 0x0023 }
        r1 = 1;
        if (r0 == 0) goto L_0x000f;
    L_0x000d:
        monitor-exit(r3);	 Catch:{ all -> 0x0023 }
        return r1;
    L_0x000f:
        r3.cancelled = r1;	 Catch:{ all -> 0x0023 }
        r0 = r3.parent;	 Catch:{ all -> 0x0023 }
        r2 = 0;
        r3.parent = r2;	 Catch:{ all -> 0x0023 }
        monitor-exit(r3);	 Catch:{ all -> 0x0023 }
        if (r0 == 0) goto L_0x001c;
    L_0x0019:
        r0.cancel();
    L_0x001c:
        r3.cancelCleanup();
        r3.cleanup();
        return r1;
    L_0x0023:
        r0 = move-exception;
        monitor-exit(r3);	 Catch:{ all -> 0x0023 }
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.koushikdutta.async.future.SimpleCancellable.cancel():boolean");
    }

    public SimpleCancellable setParent(Cancellable parent) {
        synchronized (this) {
            if (!isDone()) {
                this.parent = parent;
            }
        }
        return this;
    }

    public boolean isCancelled() {
        boolean z;
        synchronized (this) {
            if (!this.cancelled) {
                if (this.parent == null || !this.parent.isCancelled()) {
                    z = false;
                }
            }
            z = true;
        }
        return z;
    }

    public Cancellable reset() {
        cancel();
        this.complete = false;
        this.cancelled = false;
        return this;
    }
}
