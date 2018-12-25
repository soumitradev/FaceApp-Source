package io.fabric.sdk.android.services.common;

import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Logger;
import io.fabric.sdk.android.services.concurrency.internal.Backoff;
import io.fabric.sdk.android.services.concurrency.internal.RetryPolicy;
import io.fabric.sdk.android.services.concurrency.internal.RetryThreadPoolExecutor;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public final class ExecutorUtils {
    private static final long DEFAULT_TERMINATION_TIMEOUT = 2;

    private ExecutorUtils() {
    }

    public static ExecutorService buildSingleThreadExecutorService(String name) {
        ExecutorService executor = Executors.newSingleThreadExecutor(getNamedThreadFactory(name));
        addDelayedShutdownHook(name, executor);
        return executor;
    }

    public static RetryThreadPoolExecutor buildRetryThreadPoolExecutor(String name, int corePoolSize, RetryPolicy retryPolicy, Backoff backoff) {
        RetryThreadPoolExecutor executor = new RetryThreadPoolExecutor(corePoolSize, getNamedThreadFactory(name), retryPolicy, backoff);
        addDelayedShutdownHook(name, executor);
        return executor;
    }

    public static ScheduledExecutorService buildSingleThreadScheduledExecutorService(String name) {
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor(getNamedThreadFactory(name));
        addDelayedShutdownHook(name, executor);
        return executor;
    }

    public static final ThreadFactory getNamedThreadFactory(final String threadNameTemplate) {
        final AtomicLong count = new AtomicLong(1);
        return new ThreadFactory() {
            public Thread newThread(final Runnable runnable) {
                Thread thread = Executors.defaultThreadFactory().newThread(new BackgroundPriorityRunnable() {
                    public void onRun() {
                        runnable.run();
                    }
                });
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(threadNameTemplate);
                stringBuilder.append(count.getAndIncrement());
                thread.setName(stringBuilder.toString());
                return thread;
            }
        };
    }

    private static final void addDelayedShutdownHook(String serviceName, ExecutorService service) {
        addDelayedShutdownHook(serviceName, service, 2, TimeUnit.SECONDS);
    }

    public static final void addDelayedShutdownHook(String serviceName, ExecutorService service, long terminationTimeout, TimeUnit timeUnit) {
        Runtime runtime = Runtime.getRuntime();
        final String str = serviceName;
        final ExecutorService executorService = service;
        final long j = terminationTimeout;
        final TimeUnit timeUnit2 = timeUnit;
        Runnable c20752 = new BackgroundPriorityRunnable() {
            public void onRun() {
                try {
                    Logger logger = Fabric.getLogger();
                    String str = Fabric.TAG;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Executing shutdown hook for ");
                    stringBuilder.append(str);
                    logger.mo4809d(str, stringBuilder.toString());
                    executorService.shutdown();
                    if (!executorService.awaitTermination(j, timeUnit2)) {
                        logger = Fabric.getLogger();
                        str = Fabric.TAG;
                        stringBuilder = new StringBuilder();
                        stringBuilder.append(str);
                        stringBuilder.append(" did not shut down in the allocated time. Requesting immediate shutdown.");
                        logger.mo4809d(str, stringBuilder.toString());
                        executorService.shutdownNow();
                    }
                } catch (InterruptedException e) {
                    Fabric.getLogger().mo4809d(Fabric.TAG, String.format(Locale.US, "Interrupted while waiting for %s to shut down. Requesting immediate shutdown.", new Object[]{str}));
                    executorService.shutdownNow();
                }
            }
        };
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Crashlytics Shutdown Hook for ");
        stringBuilder.append(serviceName);
        runtime.addShutdownHook(new Thread(c20752, stringBuilder.toString()));
    }
}
