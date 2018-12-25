package com.google.common.util.concurrent;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList$Builder;
import com.google.common.collect.Queues;
import com.google.common.util.concurrent.AbstractFuture.TrustedFuture;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.List;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import javax.annotation.CheckReturnValue;
import javax.annotation.Nullable;

@GwtCompatible(emulated = true)
@Beta
public final class Futures extends GwtFuturesCatchingSpecialization {
    private static final AsyncFunction<ListenableFuture<Object>, Object> DEREFERENCER = new Futures$4();

    @GwtIncompatible("TODO")
    private static class ImmediateSuccessfulCheckedFuture<V, X extends Exception> extends Futures$ImmediateFuture<V> implements CheckedFuture<V, X> {
        @Nullable
        private final V value;

        ImmediateSuccessfulCheckedFuture(@Nullable V value) {
            super(null);
            this.value = value;
        }

        public V get() {
            return this.value;
        }

        public V checkedGet() {
            return this.value;
        }

        public V checkedGet(long timeout, TimeUnit unit) {
            Preconditions.checkNotNull(unit);
            return this.value;
        }
    }

    private static class ImmediateSuccessfulFuture<V> extends Futures$ImmediateFuture<V> {
        static final ImmediateSuccessfulFuture<Object> NULL = new ImmediateSuccessfulFuture(null);
        @Nullable
        private final V value;

        ImmediateSuccessfulFuture(@Nullable V value) {
            super(null);
            this.value = value;
        }

        public V get() {
            return this.value;
        }
    }

    private static abstract class AbstractCatchingFuture<V, X extends Throwable, F> extends TrustedFuture<V> implements Runnable {
        @Nullable
        Class<X> exceptionType;
        @Nullable
        F fallback;
        @Nullable
        ListenableFuture<? extends V> inputFuture;

        abstract void doFallback(F f, X x) throws Exception;

        AbstractCatchingFuture(ListenableFuture<? extends V> inputFuture, Class<X> exceptionType, F fallback) {
            this.inputFuture = (ListenableFuture) Preconditions.checkNotNull(inputFuture);
            this.exceptionType = (Class) Preconditions.checkNotNull(exceptionType);
            this.fallback = Preconditions.checkNotNull(fallback);
        }

        public final void run() {
            X throwable;
            ListenableFuture<? extends V> localInputFuture = this.inputFuture;
            Class<X> localExceptionType = this.exceptionType;
            F localFallback = this.fallback;
            int i = 0;
            int i2 = (localInputFuture == null ? 1 : 0) | (localExceptionType == null ? 1 : 0);
            if (localFallback == null) {
                i = 1;
            }
            if (((i | i2) | isCancelled()) == 0) {
                this.inputFuture = null;
                this.exceptionType = null;
                this.fallback = null;
                try {
                    set(Uninterruptibles.getUninterruptibly(localInputFuture));
                } catch (Throwable throwable2) {
                    throwable = throwable2.getCause();
                    try {
                        if (Platform.isInstanceOfThrowableClass(throwable, localExceptionType)) {
                            setException(throwable);
                        } else {
                            doFallback(localFallback, throwable);
                        }
                    } catch (Throwable e) {
                        setException(e);
                    }
                } catch (Throwable th) {
                    throwable = th;
                    if (Platform.isInstanceOfThrowableClass(throwable, localExceptionType)) {
                        setException(throwable);
                    } else {
                        doFallback(localFallback, throwable);
                    }
                }
            }
        }

        final void done() {
            maybePropagateCancellation(this.inputFuture);
            this.inputFuture = null;
            this.exceptionType = null;
            this.fallback = null;
        }
    }

    private static abstract class AbstractChainingFuture<I, O, F> extends TrustedFuture<O> implements Runnable {
        @Nullable
        F function;
        @Nullable
        ListenableFuture<? extends I> inputFuture;

        abstract void doTransform(F f, I i) throws Exception;

        AbstractChainingFuture(ListenableFuture<? extends I> inputFuture, F function) {
            this.inputFuture = (ListenableFuture) Preconditions.checkNotNull(inputFuture);
            this.function = Preconditions.checkNotNull(function);
        }

        public final void run() {
            try {
                ListenableFuture<? extends I> localInputFuture = this.inputFuture;
                F localFunction = this.function;
                int i = 1;
                int isCancelled = isCancelled() | (localInputFuture == null ? 1 : 0);
                if (localFunction != null) {
                    i = 0;
                }
                if ((isCancelled | i) == 0) {
                    this.inputFuture = null;
                    this.function = null;
                    try {
                        doTransform(localFunction, Uninterruptibles.getUninterruptibly(localInputFuture));
                    } catch (CancellationException e) {
                        cancel(false);
                    } catch (ExecutionException e2) {
                        setException(e2.getCause());
                    }
                }
            } catch (UndeclaredThrowableException e3) {
                setException(e3.getCause());
            } catch (Throwable t) {
                setException(t);
            }
        }

        final void done() {
            maybePropagateCancellation(this.inputFuture);
            this.inputFuture = null;
            this.function = null;
        }
    }

    private static final class TimeoutFuture<V> extends TrustedFuture<V> {
        @Nullable
        ListenableFuture<V> delegateRef;
        @Nullable
        Future<?> timer;

        private static final class Fire<V> implements Runnable {
            @Nullable
            TimeoutFuture<V> timeoutFutureRef;

            Fire(TimeoutFuture<V> timeoutFuture) {
                this.timeoutFutureRef = timeoutFuture;
            }

            public void run() {
                TimeoutFuture<V> timeoutFuture = this.timeoutFutureRef;
                if (timeoutFuture != null) {
                    ListenableFuture<V> delegate = timeoutFuture.delegateRef;
                    if (delegate != null) {
                        this.timeoutFutureRef = null;
                        if (delegate.isDone()) {
                            timeoutFuture.setFuture(delegate);
                        } else {
                            try {
                                StringBuilder stringBuilder = new StringBuilder();
                                stringBuilder.append("Future timed out: ");
                                stringBuilder.append(delegate);
                                timeoutFuture.setException(new TimeoutException(stringBuilder.toString()));
                            } finally {
                                delegate.cancel(true);
                            }
                        }
                    }
                }
            }
        }

        TimeoutFuture(ListenableFuture<V> delegate) {
            this.delegateRef = (ListenableFuture) Preconditions.checkNotNull(delegate);
        }

        void done() {
            maybePropagateCancellation(this.delegateRef);
            Future<?> localTimer = this.timer;
            if (localTimer != null) {
                localTimer.cancel(false);
            }
            this.delegateRef = null;
            this.timer = null;
        }
    }

    private Futures() {
    }

    @CheckReturnValue
    @GwtIncompatible("TODO")
    public static <V, X extends Exception> CheckedFuture<V, X> makeChecked(ListenableFuture<V> future, Function<? super Exception, X> mapper) {
        return new Futures$MappingCheckedFuture((ListenableFuture) Preconditions.checkNotNull(future), mapper);
    }

    @CheckReturnValue
    public static <V> ListenableFuture<V> immediateFuture(@Nullable V value) {
        if (value == null) {
            return ImmediateSuccessfulFuture.NULL;
        }
        return new ImmediateSuccessfulFuture(value);
    }

    @CheckReturnValue
    @GwtIncompatible("TODO")
    public static <V, X extends Exception> CheckedFuture<V, X> immediateCheckedFuture(@Nullable V value) {
        return new ImmediateSuccessfulCheckedFuture(value);
    }

    @CheckReturnValue
    public static <V> ListenableFuture<V> immediateFailedFuture(Throwable throwable) {
        Preconditions.checkNotNull(throwable);
        return new Futures$ImmediateFailedFuture(throwable);
    }

    @CheckReturnValue
    @GwtIncompatible("TODO")
    public static <V> ListenableFuture<V> immediateCancelledFuture() {
        return new Futures$ImmediateCancelledFuture();
    }

    @CheckReturnValue
    @GwtIncompatible("TODO")
    public static <V, X extends Exception> CheckedFuture<V, X> immediateFailedCheckedFuture(X exception) {
        Preconditions.checkNotNull(exception);
        return new Futures$ImmediateFailedCheckedFuture(exception);
    }

    @CheckReturnValue
    @Deprecated
    public static <V> ListenableFuture<V> withFallback(ListenableFuture<? extends V> input, FutureFallback<? extends V> fallback) {
        return withFallback(input, fallback, MoreExecutors.directExecutor());
    }

    @CheckReturnValue
    @Deprecated
    public static <V> ListenableFuture<V> withFallback(ListenableFuture<? extends V> input, FutureFallback<? extends V> fallback, Executor executor) {
        return catchingAsync(input, Throwable.class, asAsyncFunction(fallback), executor);
    }

    @CheckReturnValue
    @GwtIncompatible("AVAILABLE but requires exceptionType to be Throwable.class")
    public static <V, X extends Throwable> ListenableFuture<V> catching(ListenableFuture<? extends V> input, Class<X> exceptionType, Function<? super X, ? extends V> fallback) {
        Futures$CatchingFuture<V, X> future = new Futures$CatchingFuture(input, exceptionType, fallback);
        input.addListener(future, MoreExecutors.directExecutor());
        return future;
    }

    @CheckReturnValue
    @GwtIncompatible("AVAILABLE but requires exceptionType to be Throwable.class")
    public static <V, X extends Throwable> ListenableFuture<V> catching(ListenableFuture<? extends V> input, Class<X> exceptionType, Function<? super X, ? extends V> fallback, Executor executor) {
        Futures$CatchingFuture<V, X> future = new Futures$CatchingFuture(input, exceptionType, fallback);
        input.addListener(future, rejectionPropagatingExecutor(executor, future));
        return future;
    }

    @GwtIncompatible("AVAILABLE but requires exceptionType to be Throwable.class")
    public static <V, X extends Throwable> ListenableFuture<V> catchingAsync(ListenableFuture<? extends V> input, Class<X> exceptionType, AsyncFunction<? super X, ? extends V> fallback) {
        Futures$AsyncCatchingFuture<V, X> future = new Futures$AsyncCatchingFuture(input, exceptionType, fallback);
        input.addListener(future, MoreExecutors.directExecutor());
        return future;
    }

    @GwtIncompatible("AVAILABLE but requires exceptionType to be Throwable.class")
    public static <V, X extends Throwable> ListenableFuture<V> catchingAsync(ListenableFuture<? extends V> input, Class<X> exceptionType, AsyncFunction<? super X, ? extends V> fallback, Executor executor) {
        Futures$AsyncCatchingFuture<V, X> future = new Futures$AsyncCatchingFuture(input, exceptionType, fallback);
        input.addListener(future, rejectionPropagatingExecutor(executor, future));
        return future;
    }

    @Deprecated
    static <V> AsyncFunction<Throwable, V> asAsyncFunction(FutureFallback<V> fallback) {
        Preconditions.checkNotNull(fallback);
        return new Futures$1(fallback);
    }

    @CheckReturnValue
    @GwtIncompatible("java.util.concurrent.ScheduledExecutorService")
    public static <V> ListenableFuture<V> withTimeout(ListenableFuture<V> delegate, long time, TimeUnit unit, ScheduledExecutorService scheduledExecutor) {
        TimeoutFuture<V> result = new TimeoutFuture(delegate);
        Fire<V> fire = new Fire(result);
        result.timer = scheduledExecutor.schedule(fire, time, unit);
        delegate.addListener(fire, MoreExecutors.directExecutor());
        return result;
    }

    @Deprecated
    public static <I, O> ListenableFuture<O> transform(ListenableFuture<I> input, AsyncFunction<? super I, ? extends O> function) {
        return transformAsync(input, function);
    }

    @Deprecated
    public static <I, O> ListenableFuture<O> transform(ListenableFuture<I> input, AsyncFunction<? super I, ? extends O> function, Executor executor) {
        return transformAsync(input, function, executor);
    }

    public static <I, O> ListenableFuture<O> transformAsync(ListenableFuture<I> input, AsyncFunction<? super I, ? extends O> function) {
        Futures$AsyncChainingFuture<I, O> output = new Futures$AsyncChainingFuture(input, function);
        input.addListener(output, MoreExecutors.directExecutor());
        return output;
    }

    public static <I, O> ListenableFuture<O> transformAsync(ListenableFuture<I> input, AsyncFunction<? super I, ? extends O> function, Executor executor) {
        Preconditions.checkNotNull(executor);
        Futures$AsyncChainingFuture<I, O> output = new Futures$AsyncChainingFuture(input, function);
        input.addListener(output, rejectionPropagatingExecutor(executor, output));
        return output;
    }

    private static Executor rejectionPropagatingExecutor(Executor delegate, AbstractFuture<?> future) {
        Preconditions.checkNotNull(delegate);
        if (delegate == MoreExecutors.directExecutor()) {
            return delegate;
        }
        return new Futures$2(delegate, future);
    }

    public static <I, O> ListenableFuture<O> transform(ListenableFuture<I> input, Function<? super I, ? extends O> function) {
        Preconditions.checkNotNull(function);
        Futures$ChainingFuture<I, O> output = new Futures$ChainingFuture(input, function);
        input.addListener(output, MoreExecutors.directExecutor());
        return output;
    }

    public static <I, O> ListenableFuture<O> transform(ListenableFuture<I> input, Function<? super I, ? extends O> function, Executor executor) {
        Preconditions.checkNotNull(function);
        Futures$ChainingFuture<I, O> output = new Futures$ChainingFuture(input, function);
        input.addListener(output, rejectionPropagatingExecutor(executor, output));
        return output;
    }

    @CheckReturnValue
    @GwtIncompatible("TODO")
    public static <I, O> Future<O> lazyTransform(Future<I> input, Function<? super I, ? extends O> function) {
        Preconditions.checkNotNull(input);
        Preconditions.checkNotNull(function);
        return new Futures$3(input, function);
    }

    @CheckReturnValue
    public static <V> ListenableFuture<V> dereference(ListenableFuture<? extends ListenableFuture<? extends V>> nested) {
        return transformAsync(nested, DEREFERENCER);
    }

    @CheckReturnValue
    @SafeVarargs
    @Beta
    public static <V> ListenableFuture<List<V>> allAsList(ListenableFuture<? extends V>... futures) {
        return new Futures$ListFuture(ImmutableList.copyOf((Object[]) futures), true);
    }

    @CheckReturnValue
    @Beta
    public static <V> ListenableFuture<List<V>> allAsList(Iterable<? extends ListenableFuture<? extends V>> futures) {
        return new Futures$ListFuture(ImmutableList.copyOf((Iterable) futures), true);
    }

    @CheckReturnValue
    @GwtIncompatible("TODO")
    public static <V> ListenableFuture<V> nonCancellationPropagating(ListenableFuture<V> future) {
        return new Futures$NonCancellationPropagatingFuture(future);
    }

    @CheckReturnValue
    @SafeVarargs
    @Beta
    public static <V> ListenableFuture<List<V>> successfulAsList(ListenableFuture<? extends V>... futures) {
        return new Futures$ListFuture(ImmutableList.copyOf((Object[]) futures), false);
    }

    @CheckReturnValue
    @Beta
    public static <V> ListenableFuture<List<V>> successfulAsList(Iterable<? extends ListenableFuture<? extends V>> futures) {
        return new Futures$ListFuture(ImmutableList.copyOf((Iterable) futures), false);
    }

    @CheckReturnValue
    @GwtIncompatible("TODO")
    @Beta
    public static <T> ImmutableList<ListenableFuture<T>> inCompletionOrder(Iterable<? extends ListenableFuture<? extends T>> futures) {
        ConcurrentLinkedQueue<SettableFuture<T>> delegates = Queues.newConcurrentLinkedQueue();
        ImmutableList$Builder<ListenableFuture<T>> listBuilder = ImmutableList.builder();
        SerializingExecutor executor = new SerializingExecutor(MoreExecutors.directExecutor());
        for (ListenableFuture<? extends T> future : futures) {
            SettableFuture<T> delegate = SettableFuture.create();
            delegates.add(delegate);
            future.addListener(new Futures$5(delegates, future), executor);
            listBuilder.add(delegate);
        }
        return listBuilder.build();
    }

    public static <V> void addCallback(ListenableFuture<V> future, FutureCallback<? super V> callback) {
        addCallback(future, callback, MoreExecutors.directExecutor());
    }

    public static <V> void addCallback(ListenableFuture<V> future, FutureCallback<? super V> callback, Executor executor) {
        Preconditions.checkNotNull(callback);
        future.addListener(new Futures$6(future, callback), executor);
    }

    @GwtIncompatible("reflection")
    @Deprecated
    public static <V, X extends Exception> V get(Future<V> future, Class<X> exceptionClass) throws Exception {
        return getChecked(future, exceptionClass);
    }

    @GwtIncompatible("reflection")
    @Deprecated
    public static <V, X extends Exception> V get(Future<V> future, long timeout, TimeUnit unit, Class<X> exceptionClass) throws Exception {
        return getChecked(future, exceptionClass, timeout, unit);
    }

    @GwtIncompatible("reflection")
    public static <V, X extends Exception> V getChecked(Future<V> future, Class<X> exceptionClass) throws Exception {
        return FuturesGetChecked.getChecked(future, exceptionClass);
    }

    @GwtIncompatible("reflection")
    public static <V, X extends Exception> V getChecked(Future<V> future, Class<X> exceptionClass, long timeout, TimeUnit unit) throws Exception {
        return FuturesGetChecked.getChecked(future, exceptionClass, timeout, unit);
    }

    @GwtIncompatible("TODO")
    public static <V> V getUnchecked(Future<V> future) {
        Preconditions.checkNotNull(future);
        try {
            return Uninterruptibles.getUninterruptibly(future);
        } catch (ExecutionException e) {
            wrapAndThrowUnchecked(e.getCause());
            throw new AssertionError();
        }
    }

    @GwtIncompatible("TODO")
    private static void wrapAndThrowUnchecked(Throwable cause) {
        if (cause instanceof Error) {
            throw new ExecutionError((Error) cause);
        }
        throw new UncheckedExecutionException(cause);
    }
}
