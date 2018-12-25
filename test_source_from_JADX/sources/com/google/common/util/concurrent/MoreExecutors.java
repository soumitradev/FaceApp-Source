package com.google.common.util.concurrent;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Preconditions;
import com.google.common.base.Supplier;
import com.google.common.base.Throwables;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@GwtCompatible(emulated = true)
public final class MoreExecutors {
    private MoreExecutors() {
    }

    @GwtIncompatible("TODO")
    @Beta
    public static ExecutorService getExitingExecutorService(ThreadPoolExecutor executor, long terminationTimeout, TimeUnit timeUnit) {
        return new MoreExecutors$Application().getExitingExecutorService(executor, terminationTimeout, timeUnit);
    }

    @GwtIncompatible("TODO")
    @Beta
    public static ScheduledExecutorService getExitingScheduledExecutorService(ScheduledThreadPoolExecutor executor, long terminationTimeout, TimeUnit timeUnit) {
        return new MoreExecutors$Application().getExitingScheduledExecutorService(executor, terminationTimeout, timeUnit);
    }

    @GwtIncompatible("TODO")
    @Beta
    public static void addDelayedShutdownHook(ExecutorService service, long terminationTimeout, TimeUnit timeUnit) {
        new MoreExecutors$Application().addDelayedShutdownHook(service, terminationTimeout, timeUnit);
    }

    @GwtIncompatible("concurrency")
    @Beta
    public static ExecutorService getExitingExecutorService(ThreadPoolExecutor executor) {
        return new MoreExecutors$Application().getExitingExecutorService(executor);
    }

    @GwtIncompatible("TODO")
    @Beta
    public static ScheduledExecutorService getExitingScheduledExecutorService(ScheduledThreadPoolExecutor executor) {
        return new MoreExecutors$Application().getExitingScheduledExecutorService(executor);
    }

    @GwtIncompatible("TODO")
    private static void useDaemonThreadFactory(ThreadPoolExecutor executor) {
        executor.setThreadFactory(new ThreadFactoryBuilder().setDaemon(true).setThreadFactory(executor.getThreadFactory()).build());
    }

    @GwtIncompatible("TODO")
    @Deprecated
    public static ListeningExecutorService sameThreadExecutor() {
        return new MoreExecutors$DirectExecutorService(null);
    }

    @GwtIncompatible("TODO")
    public static ListeningExecutorService newDirectExecutorService() {
        return new MoreExecutors$DirectExecutorService(null);
    }

    public static Executor directExecutor() {
        return MoreExecutors$DirectExecutor.INSTANCE;
    }

    @GwtIncompatible("TODO")
    public static ListeningExecutorService listeningDecorator(ExecutorService delegate) {
        if (delegate instanceof ListeningExecutorService) {
            return (ListeningExecutorService) delegate;
        }
        return delegate instanceof ScheduledExecutorService ? new MoreExecutors$ScheduledListeningDecorator((ScheduledExecutorService) delegate) : new MoreExecutors$ListeningDecorator(delegate);
    }

    @GwtIncompatible("TODO")
    public static ListeningScheduledExecutorService listeningDecorator(ScheduledExecutorService delegate) {
        return delegate instanceof ListeningScheduledExecutorService ? (ListeningScheduledExecutorService) delegate : new MoreExecutors$ScheduledListeningDecorator(delegate);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static <T> T invokeAnyImpl(com.google.common.util.concurrent.ListeningExecutorService r23, java.util.Collection<? extends java.util.concurrent.Callable<T>> r24, boolean r25, long r26) throws java.lang.InterruptedException, java.util.concurrent.ExecutionException, java.util.concurrent.TimeoutException {
        /*
        r1 = r23;
        com.google.common.base.Preconditions.checkNotNull(r23);
        r3 = r24.size();
        if (r3 <= 0) goto L_0x000d;
    L_0x000b:
        r5 = 1;
        goto L_0x000e;
    L_0x000d:
        r5 = 0;
    L_0x000e:
        com.google.common.base.Preconditions.checkArgument(r5);
        r5 = com.google.common.collect.Lists.newArrayListWithCapacity(r3);
        r6 = com.google.common.collect.Queues.newLinkedBlockingQueue();
        r7 = 0;
        if (r25 == 0) goto L_0x0028;
    L_0x001c:
        r8 = java.lang.System.nanoTime();	 Catch:{ all -> 0x0021 }
        goto L_0x002a;
    L_0x0021:
        r0 = move-exception;
        r11 = r26;
        r1 = r0;
        r7 = r3;
        goto L_0x00d3;
    L_0x0028:
        r8 = 0;
    L_0x002a:
        r10 = r24.iterator();	 Catch:{ all -> 0x0021 }
        r11 = r10.next();	 Catch:{ all -> 0x0021 }
        r11 = (java.util.concurrent.Callable) r11;	 Catch:{ all -> 0x0021 }
        r11 = submitAndAddQueueListener(r1, r11, r6);	 Catch:{ all -> 0x0021 }
        r5.add(r11);	 Catch:{ all -> 0x0021 }
        r3 = r3 + -1;
        r11 = r26;
        r13 = r8;
        r8 = r7;
        r7 = r3;
        r3 = 1;
    L_0x0043:
        r9 = r6.poll();	 Catch:{ all -> 0x00d1 }
        r9 = (java.util.concurrent.Future) r9;	 Catch:{ all -> 0x00d1 }
        if (r9 != 0) goto L_0x0096;
    L_0x004b:
        if (r7 <= 0) goto L_0x005f;
    L_0x004d:
        r7 = r7 + -1;
        r15 = r10.next();	 Catch:{ all -> 0x00d1 }
        r15 = (java.util.concurrent.Callable) r15;	 Catch:{ all -> 0x00d1 }
        r15 = submitAndAddQueueListener(r1, r15, r6);	 Catch:{ all -> 0x00d1 }
        r5.add(r15);	 Catch:{ all -> 0x00d1 }
        r3 = r3 + 1;
        goto L_0x0096;
    L_0x005f:
        if (r3 != 0) goto L_0x006c;
        if (r8 != 0) goto L_0x006b;
    L_0x0064:
        r9 = new java.util.concurrent.ExecutionException;	 Catch:{ all -> 0x00d1 }
        r15 = 0;
        r9.<init>(r15);	 Catch:{ all -> 0x00d1 }
        r8 = r9;
    L_0x006b:
        throw r8;	 Catch:{ all -> 0x00d1 }
    L_0x006c:
        if (r25 == 0) goto L_0x008f;
    L_0x006e:
        r15 = java.util.concurrent.TimeUnit.NANOSECONDS;	 Catch:{ all -> 0x00d1 }
        r15 = r6.poll(r11, r15);	 Catch:{ all -> 0x00d1 }
        r15 = (java.util.concurrent.Future) r15;	 Catch:{ all -> 0x00d1 }
        r9 = r15;
        if (r9 != 0) goto L_0x007f;
    L_0x0079:
        r15 = new java.util.concurrent.TimeoutException;	 Catch:{ all -> 0x00d1 }
        r15.<init>();	 Catch:{ all -> 0x00d1 }
        throw r15;	 Catch:{ all -> 0x00d1 }
    L_0x007f:
        r15 = java.lang.System.nanoTime();	 Catch:{ all -> 0x00d1 }
        r17 = 0;
        r17 = r15 - r13;
        r19 = r11 - r17;
        r11 = r15;
        r13 = r11;
        r11 = r19;
        goto L_0x0096;
    L_0x008f:
        r15 = r6.take();	 Catch:{ all -> 0x00d1 }
        r15 = (java.util.concurrent.Future) r15;	 Catch:{ all -> 0x00d1 }
        r9 = r15;
    L_0x0096:
        if (r9 == 0) goto L_0x00cd;
    L_0x0098:
        r3 = r3 + -1;
        r15 = r9.get();	 Catch:{ ExecutionException -> 0x00c8, RuntimeException -> 0x00bf }
        r16 = r5.iterator();
    L_0x00a2:
        r21 = r16;
        r4 = r21;
        r16 = r4.hasNext();
        if (r16 == 0) goto L_0x00be;
    L_0x00ac:
        r16 = r4.next();
        r1 = r16;
        r1 = (java.util.concurrent.Future) r1;
        r2 = 1;
        r1.cancel(r2);
        r16 = r4;
        r1 = r23;
        goto L_0x00a2;
    L_0x00be:
        return r15;
    L_0x00bf:
        r0 = move-exception;
        r1 = r0;
        r2 = new java.util.concurrent.ExecutionException;	 Catch:{ all -> 0x00d1 }
        r2.<init>(r1);	 Catch:{ all -> 0x00d1 }
        r1 = r2;
        goto L_0x00cc;
    L_0x00c8:
        r0 = move-exception;
        r1 = r0;
    L_0x00cc:
        r8 = r1;
    L_0x00cd:
        r1 = r23;
        goto L_0x0043;
    L_0x00d1:
        r0 = move-exception;
        r1 = r0;
    L_0x00d3:
        r2 = r5.iterator();
    L_0x00d7:
        r3 = r2.hasNext();
        if (r3 == 0) goto L_0x00e8;
    L_0x00dd:
        r3 = r2.next();
        r3 = (java.util.concurrent.Future) r3;
        r4 = 1;
        r3.cancel(r4);
        goto L_0x00d7;
    L_0x00e8:
        throw r1;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.common.util.concurrent.MoreExecutors.invokeAnyImpl(com.google.common.util.concurrent.ListeningExecutorService, java.util.Collection, boolean, long):T");
    }

    @GwtIncompatible("TODO")
    private static <T> ListenableFuture<T> submitAndAddQueueListener(ListeningExecutorService executorService, Callable<T> task, BlockingQueue<Future<T>> queue) {
        ListenableFuture<T> future = executorService.submit(task);
        future.addListener(new MoreExecutors$1(queue, future), directExecutor());
        return future;
    }

    @GwtIncompatible("concurrency")
    @Beta
    public static ThreadFactory platformThreadFactory() {
        if (!isAppEngine()) {
            return Executors.defaultThreadFactory();
        }
        try {
            return (ThreadFactory) Class.forName("com.google.appengine.api.ThreadManager").getMethod("currentRequestThreadFactory", new Class[0]).invoke(null, new Object[0]);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Couldn't invoke ThreadManager.currentRequestThreadFactory", e);
        } catch (ClassNotFoundException e2) {
            throw new RuntimeException("Couldn't invoke ThreadManager.currentRequestThreadFactory", e2);
        } catch (NoSuchMethodException e3) {
            throw new RuntimeException("Couldn't invoke ThreadManager.currentRequestThreadFactory", e3);
        } catch (InvocationTargetException e4) {
            throw Throwables.propagate(e4.getCause());
        }
    }

    @GwtIncompatible("TODO")
    private static boolean isAppEngine() {
        boolean z = false;
        if (System.getProperty("com.google.appengine.runtime.environment") == null) {
            return false;
        }
        try {
            if (Class.forName("com.google.apphosting.api.ApiProxy").getMethod("getCurrentEnvironment", new Class[0]).invoke(null, new Object[0]) != null) {
                z = true;
            }
            return z;
        } catch (ClassNotFoundException e) {
            return false;
        } catch (InvocationTargetException e2) {
            return false;
        } catch (IllegalAccessException e3) {
            return false;
        } catch (NoSuchMethodException e4) {
            return false;
        }
    }

    @GwtIncompatible("concurrency")
    static Thread newThread(String name, Runnable runnable) {
        Preconditions.checkNotNull(name);
        Preconditions.checkNotNull(runnable);
        Thread result = platformThreadFactory().newThread(runnable);
        try {
            result.setName(name);
        } catch (SecurityException e) {
        }
        return result;
    }

    @GwtIncompatible("concurrency")
    static Executor renamingDecorator(Executor executor, Supplier<String> nameSupplier) {
        Preconditions.checkNotNull(executor);
        Preconditions.checkNotNull(nameSupplier);
        if (isAppEngine()) {
            return executor;
        }
        return new MoreExecutors$2(executor, nameSupplier);
    }

    @GwtIncompatible("concurrency")
    static ExecutorService renamingDecorator(ExecutorService service, Supplier<String> nameSupplier) {
        Preconditions.checkNotNull(service);
        Preconditions.checkNotNull(nameSupplier);
        if (isAppEngine()) {
            return service;
        }
        return new MoreExecutors$3(service, nameSupplier);
    }

    @GwtIncompatible("concurrency")
    static ScheduledExecutorService renamingDecorator(ScheduledExecutorService service, Supplier<String> nameSupplier) {
        Preconditions.checkNotNull(service);
        Preconditions.checkNotNull(nameSupplier);
        if (isAppEngine()) {
            return service;
        }
        return new MoreExecutors$4(service, nameSupplier);
    }

    @GwtIncompatible("concurrency")
    @Beta
    public static boolean shutdownAndAwaitTermination(ExecutorService service, long timeout, TimeUnit unit) {
        Preconditions.checkNotNull(unit);
        service.shutdown();
        try {
            long halfTimeoutNanos = TimeUnit.NANOSECONDS.convert(timeout, unit) / 2;
            if (!service.awaitTermination(halfTimeoutNanos, TimeUnit.NANOSECONDS)) {
                service.shutdownNow();
                service.awaitTermination(halfTimeoutNanos, TimeUnit.NANOSECONDS);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            service.shutdownNow();
        }
        return service.isTerminated();
    }
}
