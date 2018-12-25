package com.koushikdutta.async.future;

import com.koushikdutta.async.callback.CompletedCallback;
import com.koushikdutta.async.callback.ContinuationCallback;
import java.util.LinkedList;

public class Continuation extends SimpleCancellable implements ContinuationCallback, Runnable, Cancellable {
    CompletedCallback callback;
    Runnable cancelCallback;
    private boolean inNext;
    LinkedList<ContinuationCallback> mCallbacks;
    boolean started;
    private boolean waiting;

    /* renamed from: com.koushikdutta.async.future.Continuation$2 */
    class C11082 implements CompletedCallback {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        boolean mThisCompleted;

        static {
            Class cls = Continuation.class;
        }

        C11082() {
        }

        public void onCompleted(Exception ex) {
            if (!this.mThisCompleted) {
                this.mThisCompleted = true;
                Continuation.this.waiting = false;
                if (ex == null) {
                    Continuation.this.next();
                } else {
                    Continuation.this.reportCompleted(ex);
                }
            }
        }
    }

    public CompletedCallback getCallback() {
        return this.callback;
    }

    public void setCallback(CompletedCallback callback) {
        this.callback = callback;
    }

    public Runnable getCancelCallback() {
        return this.cancelCallback;
    }

    public void setCancelCallback(Runnable cancelCallback) {
        this.cancelCallback = cancelCallback;
    }

    public void setCancelCallback(final Cancellable cancel) {
        if (cancel == null) {
            this.cancelCallback = null;
        } else {
            this.cancelCallback = new Runnable() {
                public void run() {
                    cancel.cancel();
                }
            };
        }
    }

    public Continuation() {
        this(null);
    }

    public Continuation(CompletedCallback callback) {
        this(callback, null);
    }

    public Continuation(CompletedCallback callback, Runnable cancelCallback) {
        this.mCallbacks = new LinkedList();
        this.cancelCallback = cancelCallback;
        this.callback = callback;
    }

    private CompletedCallback wrap() {
        return new C11082();
    }

    void reportCompleted(Exception ex) {
        if (setComplete() && this.callback != null) {
            this.callback.onCompleted(ex);
        }
    }

    private ContinuationCallback hook(ContinuationCallback callback) {
        if (callback instanceof DependentCancellable) {
            ((DependentCancellable) callback).setParent(this);
        }
        return callback;
    }

    public Continuation add(ContinuationCallback callback) {
        this.mCallbacks.add(hook(callback));
        return this;
    }

    public Continuation insert(ContinuationCallback callback) {
        this.mCallbacks.add(0, hook(callback));
        return this;
    }

    public Continuation add(final DependentFuture future) {
        future.setParent(this);
        add(new ContinuationCallback() {
            public void onContinue(Continuation continuation, CompletedCallback next) throws Exception {
                future.get();
                next.onCompleted(null);
            }
        });
        return this;
    }

    private void next() {
        if (!this.inNext) {
            while (this.mCallbacks.size() > 0 && !this.waiting && !isDone() && !isCancelled()) {
                ContinuationCallback cb = (ContinuationCallback) this.mCallbacks.remove();
                try {
                    this.inNext = true;
                    this.waiting = true;
                    cb.onContinue(this, wrap());
                } catch (Exception e) {
                    reportCompleted(e);
                } catch (Throwable th) {
                    this.inNext = false;
                }
                this.inNext = false;
            }
            if (!this.waiting && !isDone() && !isCancelled()) {
                reportCompleted(null);
            }
        }
    }

    public boolean cancel() {
        if (!super.cancel()) {
            return false;
        }
        if (this.cancelCallback != null) {
            this.cancelCallback.run();
        }
        return true;
    }

    public Continuation start() {
        if (this.started) {
            throw new IllegalStateException("already started");
        }
        this.started = true;
        next();
        return this;
    }

    public void onContinue(Continuation continuation, CompletedCallback next) throws Exception {
        setCallback(next);
        start();
    }

    public void run() {
        start();
    }
}
