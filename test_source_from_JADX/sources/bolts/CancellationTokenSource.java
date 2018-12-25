package bolts;

import java.io.Closeable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class CancellationTokenSource implements Closeable {
    private boolean cancellationRequested;
    private boolean closed;
    private final ScheduledExecutorService executor = BoltsExecutors.scheduled();
    private final Object lock = new Object();
    private final List<CancellationTokenRegistration> registrations = new ArrayList();
    private ScheduledFuture<?> scheduledCancellation;

    /* renamed from: bolts.CancellationTokenSource$1 */
    class C03151 implements Runnable {
        C03151() {
        }

        public void run() {
            synchronized (CancellationTokenSource.this.lock) {
                CancellationTokenSource.this.scheduledCancellation = null;
            }
            CancellationTokenSource.this.cancel();
        }
    }

    public boolean isCancellationRequested() {
        boolean z;
        synchronized (this.lock) {
            throwIfClosed();
            z = this.cancellationRequested;
        }
        return z;
    }

    public CancellationToken getToken() {
        CancellationToken cancellationToken;
        synchronized (this.lock) {
            throwIfClosed();
            cancellationToken = new CancellationToken(this);
        }
        return cancellationToken;
    }

    public void cancel() {
        synchronized (this.lock) {
            throwIfClosed();
            if (this.cancellationRequested) {
                return;
            }
            cancelScheduledCancellation();
            this.cancellationRequested = true;
            ArrayList registrations = new ArrayList(this.registrations);
            notifyListeners(registrations);
        }
    }

    public void cancelAfter(long delay) {
        cancelAfter(delay, TimeUnit.MILLISECONDS);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void cancelAfter(long r6, java.util.concurrent.TimeUnit r8) {
        /*
        r5 = this;
        r0 = -1;
        r2 = (r6 > r0 ? 1 : (r6 == r0 ? 0 : -1));
        if (r2 >= 0) goto L_0x000e;
    L_0x0006:
        r0 = new java.lang.IllegalArgumentException;
        r1 = "Delay must be >= -1";
        r0.<init>(r1);
        throw r0;
    L_0x000e:
        r2 = 0;
        r4 = (r6 > r2 ? 1 : (r6 == r2 ? 0 : -1));
        if (r4 != 0) goto L_0x0018;
    L_0x0014:
        r5.cancel();
        return;
    L_0x0018:
        r2 = r5.lock;
        monitor-enter(r2);
        r3 = r5.cancellationRequested;	 Catch:{ all -> 0x0037 }
        if (r3 == 0) goto L_0x0021;
    L_0x001f:
        monitor-exit(r2);	 Catch:{ all -> 0x0037 }
        return;
    L_0x0021:
        r5.cancelScheduledCancellation();	 Catch:{ all -> 0x0037 }
        r3 = (r6 > r0 ? 1 : (r6 == r0 ? 0 : -1));
        if (r3 == 0) goto L_0x0035;
    L_0x0028:
        r0 = r5.executor;	 Catch:{ all -> 0x0037 }
        r1 = new bolts.CancellationTokenSource$1;	 Catch:{ all -> 0x0037 }
        r1.<init>();	 Catch:{ all -> 0x0037 }
        r0 = r0.schedule(r1, r6, r8);	 Catch:{ all -> 0x0037 }
        r5.scheduledCancellation = r0;	 Catch:{ all -> 0x0037 }
    L_0x0035:
        monitor-exit(r2);	 Catch:{ all -> 0x0037 }
        return;
    L_0x0037:
        r0 = move-exception;
        monitor-exit(r2);	 Catch:{ all -> 0x0037 }
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: bolts.CancellationTokenSource.cancelAfter(long, java.util.concurrent.TimeUnit):void");
    }

    public void close() {
        synchronized (this.lock) {
            if (this.closed) {
                return;
            }
            cancelScheduledCancellation();
            for (CancellationTokenRegistration registration : this.registrations) {
                registration.close();
            }
            this.registrations.clear();
            this.closed = true;
        }
    }

    CancellationTokenRegistration register(Runnable action) {
        CancellationTokenRegistration ctr;
        synchronized (this.lock) {
            throwIfClosed();
            ctr = new CancellationTokenRegistration(this, action);
            if (this.cancellationRequested) {
                ctr.runAction();
            } else {
                this.registrations.add(ctr);
            }
        }
        return ctr;
    }

    void throwIfCancellationRequested() throws CancellationException {
        synchronized (this.lock) {
            throwIfClosed();
            if (this.cancellationRequested) {
                throw new CancellationException();
            }
        }
    }

    void unregister(CancellationTokenRegistration registration) {
        synchronized (this.lock) {
            throwIfClosed();
            this.registrations.remove(registration);
        }
    }

    private void notifyListeners(List<CancellationTokenRegistration> registrations) {
        for (CancellationTokenRegistration registration : registrations) {
            registration.runAction();
        }
    }

    public String toString() {
        return String.format(Locale.US, "%s@%s[cancellationRequested=%s]", new Object[]{getClass().getName(), Integer.toHexString(hashCode()), Boolean.toString(isCancellationRequested())});
    }

    private void throwIfClosed() {
        if (this.closed) {
            throw new IllegalStateException("Object already closed");
        }
    }

    private void cancelScheduledCancellation() {
        if (this.scheduledCancellation != null) {
            this.scheduledCancellation.cancel(true);
            this.scheduledCancellation = null;
        }
    }
}
