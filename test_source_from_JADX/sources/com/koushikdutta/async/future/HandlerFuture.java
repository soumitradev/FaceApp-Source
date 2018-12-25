package com.koushikdutta.async.future;

import android.os.Handler;
import android.os.Looper;

public class HandlerFuture<T> extends SimpleFuture<T> {
    Handler handler;

    public HandlerFuture() {
        Looper looper = Looper.myLooper();
        if (looper == null) {
            looper = Looper.getMainLooper();
        }
        this.handler = new Handler(looper);
    }

    public SimpleFuture<T> setCallback(final FutureCallback<T> callback) {
        return super.setCallback(new FutureCallback<T>() {
            public void onCompleted(final Exception e, final T result) {
                if (Looper.myLooper() == HandlerFuture.this.handler.getLooper()) {
                    callback.onCompleted(e, result);
                } else {
                    HandlerFuture.this.handler.post(new Runnable() {
                        public void run() {
                            C11101.this.onCompleted(e, result);
                        }
                    });
                }
            }
        });
    }
}
