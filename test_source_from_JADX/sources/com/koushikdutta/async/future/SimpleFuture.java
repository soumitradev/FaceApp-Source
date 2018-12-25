package com.koushikdutta.async.future;

import com.koushikdutta.async.AsyncSemaphore;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;

public class SimpleFuture<T> extends SimpleCancellable implements DependentFuture<T> {
    FutureCallback<T> callback;
    Exception exception;
    T result;
    boolean silent;
    AsyncSemaphore waiter;

    /* renamed from: com.koushikdutta.async.future.SimpleFuture$1 */
    class C11121 implements FutureCallback<T> {
        C11121() {
        }

        public void onCompleted(Exception e, T result) {
            SimpleFuture.this.setComplete(e, result);
        }
    }

    public SimpleFuture(T value) {
        setComplete((Object) value);
    }

    public SimpleFuture(Exception e) {
        setComplete(e);
    }

    public boolean cancel(boolean mayInterruptIfRunning) {
        return cancel();
    }

    private boolean cancelInternal(boolean silent) {
        if (!super.cancel()) {
            return false;
        }
        FutureCallback<T> callback;
        synchronized (this) {
            this.exception = new CancellationException();
            releaseWaiterLocked();
            callback = handleCompleteLocked();
            this.silent = silent;
        }
        handleCallbackUnlocked(callback);
        return true;
    }

    public boolean cancelSilently() {
        return cancelInternal(true);
    }

    public boolean cancel() {
        return cancelInternal(this.silent);
    }

    public T get() throws InterruptedException, ExecutionException {
        synchronized (this) {
            if (!isCancelled()) {
                if (!isDone()) {
                    AsyncSemaphore waiter = ensureWaiterLocked();
                    waiter.acquire();
                    return getResultOrThrow();
                }
            }
            T resultOrThrow = getResultOrThrow();
            return resultOrThrow;
        }
    }

    private T getResultOrThrow() throws ExecutionException {
        if (this.exception == null) {
            return this.result;
        }
        throw new ExecutionException(this.exception);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public T get(long r3, java.util.concurrent.TimeUnit r5) throws java.lang.InterruptedException, java.util.concurrent.ExecutionException, java.util.concurrent.TimeoutException {
        /*
        r2 = this;
        monitor-enter(r2);
        r0 = r2.isCancelled();	 Catch:{ all -> 0x002a }
        if (r0 != 0) goto L_0x0024;
    L_0x0007:
        r0 = r2.isDone();	 Catch:{ all -> 0x002a }
        if (r0 == 0) goto L_0x000e;
    L_0x000d:
        goto L_0x0024;
    L_0x000e:
        r0 = r2.ensureWaiterLocked();	 Catch:{ all -> 0x002a }
        monitor-exit(r2);	 Catch:{ all -> 0x002a }
        r1 = r0.tryAcquire(r3, r5);
        if (r1 != 0) goto L_0x001f;
    L_0x0019:
        r1 = new java.util.concurrent.TimeoutException;
        r1.<init>();
        throw r1;
    L_0x001f:
        r1 = r2.getResultOrThrow();
        return r1;
    L_0x0024:
        r0 = r2.getResultOrThrow();	 Catch:{ all -> 0x002a }
        monitor-exit(r2);	 Catch:{ all -> 0x002a }
        return r0;
    L_0x002a:
        r0 = move-exception;
        monitor-exit(r2);	 Catch:{ all -> 0x002a }
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.koushikdutta.async.future.SimpleFuture.get(long, java.util.concurrent.TimeUnit):T");
    }

    public boolean setComplete() {
        return setComplete(null);
    }

    private FutureCallback<T> handleCompleteLocked() {
        FutureCallback<T> callback = this.callback;
        this.callback = null;
        return callback;
    }

    private void handleCallbackUnlocked(FutureCallback<T> callback) {
        if (callback != null && !this.silent) {
            callback.onCompleted(this.exception, this.result);
        }
    }

    void releaseWaiterLocked() {
        if (this.waiter != null) {
            this.waiter.release();
            this.waiter = null;
        }
    }

    AsyncSemaphore ensureWaiterLocked() {
        if (this.waiter == null) {
            this.waiter = new AsyncSemaphore();
        }
        return this.waiter;
    }

    public boolean setComplete(Exception e) {
        return setComplete(e, null);
    }

    public boolean setComplete(T value) {
        return setComplete(null, value);
    }

    public boolean setComplete(Exception e, T value) {
        synchronized (this) {
            if (super.setComplete()) {
                this.result = value;
                this.exception = e;
                releaseWaiterLocked();
                FutureCallback<T> callback = handleCompleteLocked();
                handleCallbackUnlocked(callback);
                return true;
            }
            return false;
        }
    }

    public FutureCallback<T> getCompletionCallback() {
        return new C11121();
    }

    public SimpleFuture<T> setComplete(Future<T> future) {
        future.setCallback(getCompletionCallback());
        setParent((Cancellable) future);
        return this;
    }

    public FutureCallback<T> getCallback() {
        return this.callback;
    }

    public SimpleFuture<T> setCallback(FutureCallback<T> callback) {
        synchronized (this) {
            this.callback = callback;
            if (!isDone()) {
                if (!isCancelled()) {
                    callback = null;
                }
            }
            callback = handleCompleteLocked();
        }
        handleCallbackUnlocked(callback);
        return this;
    }

    public final <C extends FutureCallback<T>> C then(C callback) {
        if (callback instanceof DependentCancellable) {
            ((DependentCancellable) callback).setParent(this);
        }
        setCallback((FutureCallback) callback);
        return callback;
    }

    public SimpleFuture<T> setParent(Cancellable parent) {
        super.setParent(parent);
        return this;
    }

    public SimpleFuture<T> reset() {
        super.reset();
        this.result = null;
        this.exception = null;
        this.waiter = null;
        this.callback = null;
        this.silent = false;
        return this;
    }

    public Exception tryGetException() {
        return this.exception;
    }

    public T tryGet() {
        return this.result;
    }
}
