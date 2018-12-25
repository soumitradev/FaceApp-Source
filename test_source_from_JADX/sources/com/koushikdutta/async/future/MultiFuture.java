package com.koushikdutta.async.future;

import java.util.ArrayList;
import java.util.Iterator;

public class MultiFuture<T> extends SimpleFuture<T> {
    final FutureCallback<T> callback = new C11111();
    ArrayList<FutureCallback<T>> callbacks;

    /* renamed from: com.koushikdutta.async.future.MultiFuture$1 */
    class C11111 implements FutureCallback<T> {
        C11111() {
        }

        public void onCompleted(Exception e, T result) {
            synchronized (MultiFuture.this) {
                ArrayList<FutureCallback<T>> callbacks = MultiFuture.this.callbacks;
                MultiFuture.this.callbacks = null;
            }
            if (callbacks != null) {
                Iterator it = callbacks.iterator();
                while (it.hasNext()) {
                    ((FutureCallback) it.next()).onCompleted(e, result);
                }
            }
        }
    }

    public MultiFuture<T> setCallback(FutureCallback<T> callback) {
        synchronized (this) {
            if (this.callbacks == null) {
                this.callbacks = new ArrayList();
            }
            this.callbacks.add(callback);
        }
        super.setCallback(this.callback);
        return this;
    }
}
