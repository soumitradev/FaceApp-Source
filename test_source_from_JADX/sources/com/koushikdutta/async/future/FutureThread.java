package com.koushikdutta.async.future;

import java.util.concurrent.ExecutorService;

public class FutureThread<T> extends SimpleFuture<T> {
    public FutureThread(FutureRunnable<T> runnable) {
        this((FutureRunnable) runnable, "FutureThread");
    }

    public FutureThread(ExecutorService pool, final FutureRunnable<T> runnable) {
        pool.submit(new Runnable() {
            public void run() {
                try {
                    FutureThread.this.setComplete(runnable.run());
                } catch (Exception e) {
                    FutureThread.this.setComplete(e);
                }
            }
        });
    }

    public FutureThread(final FutureRunnable<T> runnable, String name) {
        new Thread(new Runnable() {
            public void run() {
                try {
                    FutureThread.this.setComplete(runnable.run());
                } catch (Exception e) {
                    FutureThread.this.setComplete(e);
                }
            }
        }, name).start();
    }
}
