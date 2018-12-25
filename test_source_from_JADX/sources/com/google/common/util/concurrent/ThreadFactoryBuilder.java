package com.google.common.util.concurrent;

import com.google.common.base.Preconditions;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.Locale;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

public final class ThreadFactoryBuilder {
    private ThreadFactory backingThreadFactory = null;
    private Boolean daemon = null;
    private String nameFormat = null;
    private Integer priority = null;
    private UncaughtExceptionHandler uncaughtExceptionHandler = null;

    public ThreadFactoryBuilder setNameFormat(String nameFormat) {
        String unused = format(nameFormat, new Object[]{Integer.valueOf(0)});
        this.nameFormat = nameFormat;
        return this;
    }

    public ThreadFactoryBuilder setDaemon(boolean daemon) {
        this.daemon = Boolean.valueOf(daemon);
        return this;
    }

    public ThreadFactoryBuilder setPriority(int priority) {
        Preconditions.checkArgument(priority >= 1, "Thread priority (%s) must be >= %s", Integer.valueOf(priority), Integer.valueOf(1));
        Preconditions.checkArgument(priority <= 10, "Thread priority (%s) must be <= %s", Integer.valueOf(priority), Integer.valueOf(10));
        this.priority = Integer.valueOf(priority);
        return this;
    }

    public ThreadFactoryBuilder setUncaughtExceptionHandler(UncaughtExceptionHandler uncaughtExceptionHandler) {
        this.uncaughtExceptionHandler = (UncaughtExceptionHandler) Preconditions.checkNotNull(uncaughtExceptionHandler);
        return this;
    }

    public ThreadFactoryBuilder setThreadFactory(ThreadFactory backingThreadFactory) {
        this.backingThreadFactory = (ThreadFactory) Preconditions.checkNotNull(backingThreadFactory);
        return this;
    }

    public ThreadFactory build() {
        return build(this);
    }

    private static ThreadFactory build(ThreadFactoryBuilder builder) {
        String nameFormat = builder.nameFormat;
        Boolean daemon = builder.daemon;
        Integer priority = builder.priority;
        UncaughtExceptionHandler uncaughtExceptionHandler = builder.uncaughtExceptionHandler;
        final ThreadFactory backingThreadFactory = builder.backingThreadFactory != null ? builder.backingThreadFactory : Executors.defaultThreadFactory();
        final AtomicLong count = nameFormat != null ? new AtomicLong(0) : null;
        final String str = nameFormat;
        final Boolean bool = daemon;
        final Integer num = priority;
        final UncaughtExceptionHandler uncaughtExceptionHandler2 = uncaughtExceptionHandler;
        return new ThreadFactory() {
            public Thread newThread(Runnable runnable) {
                Thread thread = backingThreadFactory.newThread(runnable);
                if (str != null) {
                    thread.setName(ThreadFactoryBuilder.format(str, Long.valueOf(count.getAndIncrement())));
                }
                if (bool != null) {
                    thread.setDaemon(bool.booleanValue());
                }
                if (num != null) {
                    thread.setPriority(num.intValue());
                }
                if (uncaughtExceptionHandler2 != null) {
                    thread.setUncaughtExceptionHandler(uncaughtExceptionHandler2);
                }
                return thread;
            }
        };
    }

    private static String format(String format, Object... args) {
        return String.format(Locale.ROOT, format, args);
    }
}
