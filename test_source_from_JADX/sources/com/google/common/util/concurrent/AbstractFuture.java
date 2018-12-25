package com.google.common.util.concurrent;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;
import com.google.firebase.analytics.FirebaseAnalytics$Param;
import java.lang.reflect.Field;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.concurrent.locks.LockSupport;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Nullable;
import sun.misc.Unsafe;

@GwtCompatible(emulated = true)
public abstract class AbstractFuture<V> implements ListenableFuture<V> {
    private static final AtomicHelper ATOMIC_HELPER;
    private static final boolean GENERATE_CANCELLATION_CAUSES = Boolean.parseBoolean(System.getProperty("guava.concurrent.generate_cancellation_cause", "false"));
    private static final Object NULL = new Object();
    private static final long SPIN_THRESHOLD_NANOS = 1000;
    private static final Logger log = Logger.getLogger(AbstractFuture.class.getName());
    private volatile AbstractFuture$Listener listeners;
    private volatile Object value;
    private volatile AbstractFuture$Waiter waiters;

    private static abstract class AtomicHelper {
        abstract boolean casListeners(AbstractFuture<?> abstractFuture, AbstractFuture$Listener abstractFuture$Listener, AbstractFuture$Listener abstractFuture$Listener2);

        abstract boolean casValue(AbstractFuture<?> abstractFuture, Object obj, Object obj2);

        abstract boolean casWaiters(AbstractFuture<?> abstractFuture, AbstractFuture$Waiter abstractFuture$Waiter, AbstractFuture$Waiter abstractFuture$Waiter2);

        abstract void putNext(AbstractFuture$Waiter abstractFuture$Waiter, AbstractFuture$Waiter abstractFuture$Waiter2);

        abstract void putThread(AbstractFuture$Waiter abstractFuture$Waiter, Thread thread);

        private AtomicHelper() {
        }
    }

    private static final class Failure {
        static final Failure FALLBACK_INSTANCE = new Failure(new Throwable("Failure occurred while trying to finish a future.") {
            public synchronized Throwable fillInStackTrace() {
                return this;
            }
        });
        final Throwable exception;

        Failure(Throwable exception) {
            this.exception = (Throwable) Preconditions.checkNotNull(exception);
        }
    }

    private final class SetFuture implements Runnable {
        final ListenableFuture<? extends V> future;

        SetFuture(ListenableFuture<? extends V> future) {
            this.future = future;
        }

        public void run() {
            if (AbstractFuture.this.value == this) {
                AbstractFuture.this.completeWithFuture(this.future, this);
            }
        }
    }

    private static final class SafeAtomicHelper extends AtomicHelper {
        final AtomicReferenceFieldUpdater<AbstractFuture, AbstractFuture$Listener> listenersUpdater;
        final AtomicReferenceFieldUpdater<AbstractFuture, Object> valueUpdater;
        final AtomicReferenceFieldUpdater<AbstractFuture$Waiter, AbstractFuture$Waiter> waiterNextUpdater;
        final AtomicReferenceFieldUpdater<AbstractFuture$Waiter, Thread> waiterThreadUpdater;
        final AtomicReferenceFieldUpdater<AbstractFuture, AbstractFuture$Waiter> waitersUpdater;

        SafeAtomicHelper(AtomicReferenceFieldUpdater<AbstractFuture$Waiter, Thread> waiterThreadUpdater, AtomicReferenceFieldUpdater<AbstractFuture$Waiter, AbstractFuture$Waiter> waiterNextUpdater, AtomicReferenceFieldUpdater<AbstractFuture, AbstractFuture$Waiter> waitersUpdater, AtomicReferenceFieldUpdater<AbstractFuture, AbstractFuture$Listener> listenersUpdater, AtomicReferenceFieldUpdater<AbstractFuture, Object> valueUpdater) {
            super();
            this.waiterThreadUpdater = waiterThreadUpdater;
            this.waiterNextUpdater = waiterNextUpdater;
            this.waitersUpdater = waitersUpdater;
            this.listenersUpdater = listenersUpdater;
            this.valueUpdater = valueUpdater;
        }

        void putThread(AbstractFuture$Waiter waiter, Thread newValue) {
            this.waiterThreadUpdater.lazySet(waiter, newValue);
        }

        void putNext(AbstractFuture$Waiter waiter, AbstractFuture$Waiter newValue) {
            this.waiterNextUpdater.lazySet(waiter, newValue);
        }

        boolean casWaiters(AbstractFuture<?> future, AbstractFuture$Waiter expect, AbstractFuture$Waiter update) {
            return this.waitersUpdater.compareAndSet(future, expect, update);
        }

        boolean casListeners(AbstractFuture<?> future, AbstractFuture$Listener expect, AbstractFuture$Listener update) {
            return this.listenersUpdater.compareAndSet(future, expect, update);
        }

        boolean casValue(AbstractFuture<?> future, Object expect, Object update) {
            return this.valueUpdater.compareAndSet(future, expect, update);
        }
    }

    private static final class SynchronizedHelper extends AtomicHelper {
        private SynchronizedHelper() {
            super();
        }

        void putThread(AbstractFuture$Waiter waiter, Thread newValue) {
            waiter.thread = newValue;
        }

        void putNext(AbstractFuture$Waiter waiter, AbstractFuture$Waiter newValue) {
            waiter.next = newValue;
        }

        boolean casWaiters(AbstractFuture<?> future, AbstractFuture$Waiter expect, AbstractFuture$Waiter update) {
            synchronized (future) {
                if (future.waiters == expect) {
                    future.waiters = update;
                    return true;
                }
                return false;
            }
        }

        boolean casListeners(AbstractFuture<?> future, AbstractFuture$Listener expect, AbstractFuture$Listener update) {
            synchronized (future) {
                if (future.listeners == expect) {
                    future.listeners = update;
                    return true;
                }
                return false;
            }
        }

        boolean casValue(AbstractFuture<?> future, Object expect, Object update) {
            synchronized (future) {
                if (future.value == expect) {
                    future.value = update;
                    return true;
                }
                return false;
            }
        }
    }

    private static final class UnsafeAtomicHelper extends AtomicHelper {
        static final long LISTENERS_OFFSET;
        static final Unsafe UNSAFE;
        static final long VALUE_OFFSET;
        static final long WAITERS_OFFSET;
        static final long WAITER_NEXT_OFFSET;
        static final long WAITER_THREAD_OFFSET;

        /* renamed from: com.google.common.util.concurrent.AbstractFuture$UnsafeAtomicHelper$1 */
        static class C05851 implements PrivilegedExceptionAction<Unsafe> {
            C05851() {
            }

            public Unsafe run() throws Exception {
                Class<Unsafe> k = Unsafe.class;
                for (Field f : k.getDeclaredFields()) {
                    f.setAccessible(true);
                    Object x = f.get(null);
                    if (k.isInstance(x)) {
                        return (Unsafe) k.cast(x);
                    }
                }
                throw new NoSuchFieldError("the Unsafe");
            }
        }

        private UnsafeAtomicHelper() {
            super();
        }

        static {
            Unsafe unsafe;
            try {
                unsafe = Unsafe.getUnsafe();
            } catch (SecurityException e) {
                try {
                    unsafe = (Unsafe) AccessController.doPrivileged(new C05851());
                } catch (PrivilegedActionException e2) {
                    throw new RuntimeException("Could not initialize intrinsics", e2.getCause());
                }
            }
            try {
                Class<?> abstractFuture = AbstractFuture.class;
                WAITERS_OFFSET = unsafe.objectFieldOffset(abstractFuture.getDeclaredField("waiters"));
                LISTENERS_OFFSET = unsafe.objectFieldOffset(abstractFuture.getDeclaredField("listeners"));
                VALUE_OFFSET = unsafe.objectFieldOffset(abstractFuture.getDeclaredField(FirebaseAnalytics$Param.VALUE));
                WAITER_THREAD_OFFSET = unsafe.objectFieldOffset(AbstractFuture$Waiter.class.getDeclaredField("thread"));
                WAITER_NEXT_OFFSET = unsafe.objectFieldOffset(AbstractFuture$Waiter.class.getDeclaredField("next"));
                UNSAFE = unsafe;
            } catch (SecurityException tryReflectionInstead) {
                throw Throwables.propagate(tryReflectionInstead);
            }
        }

        void putThread(AbstractFuture$Waiter waiter, Thread newValue) {
            UNSAFE.putObject(waiter, WAITER_THREAD_OFFSET, newValue);
        }

        void putNext(AbstractFuture$Waiter waiter, AbstractFuture$Waiter newValue) {
            UNSAFE.putObject(waiter, WAITER_NEXT_OFFSET, newValue);
        }

        boolean casWaiters(AbstractFuture<?> future, AbstractFuture$Waiter expect, AbstractFuture$Waiter update) {
            return UNSAFE.compareAndSwapObject(future, WAITERS_OFFSET, expect, update);
        }

        boolean casListeners(AbstractFuture<?> future, AbstractFuture$Listener expect, AbstractFuture$Listener update) {
            return UNSAFE.compareAndSwapObject(future, LISTENERS_OFFSET, expect, update);
        }

        boolean casValue(AbstractFuture<?> future, Object expect, Object update) {
            return UNSAFE.compareAndSwapObject(future, VALUE_OFFSET, expect, update);
        }
    }

    static abstract class TrustedFuture<V> extends AbstractFuture<V> {
        TrustedFuture() {
        }

        public final V get() throws InterruptedException, ExecutionException {
            return super.get();
        }

        public final V get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
            return super.get(timeout, unit);
        }

        public final boolean isDone() {
            return super.isDone();
        }

        public final boolean isCancelled() {
            return super.isCancelled();
        }

        public final void addListener(Runnable listener, Executor executor) {
            super.addListener(listener, executor);
        }
    }

    static {
        AtomicHelper helper;
        try {
            helper = new UnsafeAtomicHelper();
        } catch (Throwable atomicReferenceFieldUpdaterFailure) {
            log.log(Level.SEVERE, "UnsafeAtomicHelper is broken!", unsafeFailure);
            log.log(Level.SEVERE, "SafeAtomicHelper is broken!", atomicReferenceFieldUpdaterFailure);
            helper = new SynchronizedHelper();
        }
        ATOMIC_HELPER = helper;
        helper = LockSupport.class;
    }

    private void removeWaiter(AbstractFuture$Waiter node) {
        node.thread = null;
        while (true) {
            AbstractFuture$Waiter pred = null;
            AbstractFuture$Waiter curr = this.waiters;
            if (curr != AbstractFuture$Waiter.TOMBSTONE) {
                while (curr != null) {
                    AbstractFuture$Waiter succ = curr.next;
                    if (curr.thread != null) {
                        pred = curr;
                    } else if (pred != null) {
                        pred.next = succ;
                        if (pred.thread == null) {
                        }
                    } else if (ATOMIC_HELPER.casWaiters(this, curr, succ)) {
                    }
                    curr = succ;
                }
                return;
            }
            return;
        }
    }

    protected AbstractFuture() {
    }

    public V get(long timeout, TimeUnit unit) throws InterruptedException, TimeoutException, ExecutionException {
        AbstractFuture abstractFuture = this;
        long remainingNanos = unit.toNanos(timeout);
        if (Thread.interrupted()) {
            throw new InterruptedException();
        }
        Object localValue = abstractFuture.value;
        if (((localValue != null ? 1 : 0) & (!(localValue instanceof SetFuture) ? 1 : 0)) != 0) {
            return getDoneValue(localValue);
        }
        long endNanos = remainingNanos > 0 ? System.nanoTime() + remainingNanos : 0;
        if (remainingNanos >= SPIN_THRESHOLD_NANOS) {
            AbstractFuture$Waiter oldHead = abstractFuture.waiters;
            if (oldHead != AbstractFuture$Waiter.TOMBSTONE) {
                AbstractFuture$Waiter node = new AbstractFuture$Waiter();
                while (true) {
                    node.setNext(oldHead);
                    if (ATOMIC_HELPER.casWaiters(abstractFuture, oldHead, node)) {
                        break;
                    }
                    oldHead = abstractFuture.waiters;
                    if (oldHead == AbstractFuture$Waiter.TOMBSTONE) {
                        break;
                    }
                }
                while (true) {
                    LockSupport.parkNanos(abstractFuture, remainingNanos);
                    if (Thread.interrupted()) {
                        removeWaiter(node);
                        throw new InterruptedException();
                    }
                    localValue = abstractFuture.value;
                    if (((localValue != null ? 1 : 0) & (!(localValue instanceof SetFuture) ? 1 : 0)) != 0) {
                        return getDoneValue(localValue);
                    }
                    remainingNanos = endNanos - System.nanoTime();
                    if (remainingNanos < SPIN_THRESHOLD_NANOS) {
                        break;
                    }
                }
                removeWaiter(node);
            }
            return getDoneValue(abstractFuture.value);
        }
        while (remainingNanos > 0) {
            localValue = abstractFuture.value;
            if (((localValue != null ? 1 : 0) & (!(localValue instanceof SetFuture) ? 1 : 0)) != 0) {
                return getDoneValue(localValue);
            }
            if (Thread.interrupted()) {
                throw new InterruptedException();
            }
            remainingNanos = endNanos - System.nanoTime();
        }
        throw new TimeoutException();
    }

    public V get() throws InterruptedException, ExecutionException {
        if (Thread.interrupted()) {
            throw new InterruptedException();
        }
        Object localValue = this.value;
        if (((localValue != null ? 1 : 0) & (!(localValue instanceof SetFuture) ? 1 : 0)) != 0) {
            return getDoneValue(localValue);
        }
        AbstractFuture$Waiter oldHead = this.waiters;
        if (oldHead != AbstractFuture$Waiter.TOMBSTONE) {
            AbstractFuture$Waiter node = new AbstractFuture$Waiter();
            do {
                node.setNext(oldHead);
                if (ATOMIC_HELPER.casWaiters(this, oldHead, node)) {
                    do {
                        LockSupport.park(this);
                        if (Thread.interrupted()) {
                            removeWaiter(node);
                            throw new InterruptedException();
                        }
                        localValue = this.value;
                    } while (((localValue != null ? 1 : 0) & (!(localValue instanceof SetFuture) ? 1 : 0)) == 0);
                    return getDoneValue(localValue);
                }
                oldHead = this.waiters;
            } while (oldHead != AbstractFuture$Waiter.TOMBSTONE);
        }
        return getDoneValue(this.value);
    }

    private V getDoneValue(Object obj) throws ExecutionException {
        if (obj instanceof AbstractFuture$Cancellation) {
            throw cancellationExceptionWithCause("Task was cancelled.", ((AbstractFuture$Cancellation) obj).cause);
        } else if (obj instanceof Failure) {
            throw new ExecutionException(((Failure) obj).exception);
        } else if (obj == NULL) {
            return null;
        } else {
            return obj;
        }
    }

    public boolean isDone() {
        Object localValue = this.value;
        int i = 0;
        int i2 = localValue != null ? 1 : 0;
        if (!(localValue instanceof SetFuture)) {
            i = 1;
        }
        return i & i2;
    }

    public boolean isCancelled() {
        return this.value instanceof AbstractFuture$Cancellation;
    }

    public boolean cancel(boolean mayInterruptIfRunning) {
        Object localValue = this.value;
        if (((localValue == null ? 1 : 0) | (localValue instanceof SetFuture)) == 0) {
            return false;
        }
        Object valueToSet = new AbstractFuture$Cancellation(mayInterruptIfRunning, GENERATE_CANCELLATION_CAUSES ? newCancellationCause() : null);
        while (!ATOMIC_HELPER.casValue(this, localValue, valueToSet)) {
            localValue = this.value;
            if (!(localValue instanceof SetFuture)) {
            }
        }
        if (mayInterruptIfRunning) {
            interruptTask();
        }
        complete();
        if (localValue instanceof SetFuture) {
            ((SetFuture) localValue).future.cancel(mayInterruptIfRunning);
        }
        return true;
        return false;
    }

    private Throwable newCancellationCause() {
        return new CancellationException("Future.cancel() was called.");
    }

    protected void interruptTask() {
    }

    protected final boolean wasInterrupted() {
        Object localValue = this.value;
        return (localValue instanceof AbstractFuture$Cancellation) && ((AbstractFuture$Cancellation) localValue).wasInterrupted;
    }

    public void addListener(Runnable listener, Executor executor) {
        Preconditions.checkNotNull(listener, "Runnable was null.");
        Preconditions.checkNotNull(executor, "Executor was null.");
        AbstractFuture$Listener oldHead = this.listeners;
        if (oldHead != AbstractFuture$Listener.TOMBSTONE) {
            AbstractFuture$Listener newNode = new AbstractFuture$Listener(listener, executor);
            do {
                newNode.next = oldHead;
                if (!ATOMIC_HELPER.casListeners(this, oldHead, newNode)) {
                    oldHead = this.listeners;
                } else {
                    return;
                }
            } while (oldHead != AbstractFuture$Listener.TOMBSTONE);
        }
        executeListener(listener, executor);
    }

    protected boolean set(@Nullable V value) {
        if (!ATOMIC_HELPER.casValue(this, null, value == null ? NULL : value)) {
            return false;
        }
        complete();
        return true;
    }

    protected boolean setException(Throwable throwable) {
        if (!ATOMIC_HELPER.casValue(this, null, new Failure((Throwable) Preconditions.checkNotNull(throwable)))) {
            return false;
        }
        complete();
        return true;
    }

    @Beta
    protected boolean setFuture(ListenableFuture<? extends V> future) {
        SetFuture valueToSet;
        Failure failure;
        Preconditions.checkNotNull(future);
        Object localValue = this.value;
        if (localValue == null) {
            if (future.isDone()) {
                return completeWithFuture(future, null);
            }
            valueToSet = new SetFuture(future);
            if (ATOMIC_HELPER.casValue(this, null, valueToSet)) {
                try {
                    future.addListener(valueToSet, MoreExecutors.directExecutor());
                } catch (Throwable th) {
                    failure = Failure.FALLBACK_INSTANCE;
                }
                return true;
            }
            localValue = this.value;
        }
        if (localValue instanceof AbstractFuture$Cancellation) {
            future.cancel(((AbstractFuture$Cancellation) localValue).wasInterrupted);
        }
        return false;
        ATOMIC_HELPER.casValue(this, valueToSet, failure);
        return true;
    }

    private boolean completeWithFuture(ListenableFuture<? extends V> future, Object expected) {
        Object valueToSet;
        if (future instanceof TrustedFuture) {
            valueToSet = ((AbstractFuture) future).value;
        } else {
            try {
                V v = Uninterruptibles.getUninterruptibly(future);
                valueToSet = v == null ? NULL : v;
            } catch (ExecutionException exception) {
                valueToSet = new Failure(exception.getCause());
            } catch (CancellationException cancellation) {
                valueToSet = new AbstractFuture$Cancellation(false, cancellation);
            } catch (Throwable t) {
                valueToSet = new Failure(t);
            }
        }
        if (!ATOMIC_HELPER.casValue(this, expected, valueToSet)) {
            return false;
        }
        complete();
        return true;
    }

    private void complete() {
        for (AbstractFuture$Waiter currentWaiter = clearWaiters(); currentWaiter != null; currentWaiter = currentWaiter.next) {
            currentWaiter.unpark();
        }
        AbstractFuture$Listener currentListener = clearListeners();
        AbstractFuture$Listener reversedList = null;
        while (currentListener != null) {
            AbstractFuture$Listener tmp = currentListener;
            currentListener = currentListener.next;
            tmp.next = reversedList;
            reversedList = tmp;
        }
        while (reversedList != null) {
            executeListener(reversedList.task, reversedList.executor);
            reversedList = reversedList.next;
        }
        done();
    }

    void done() {
    }

    final Throwable trustedGetException() {
        return ((Failure) this.value).exception;
    }

    final void maybePropagateCancellation(@Nullable Future<?> related) {
        if (((related != null ? 1 : 0) & isCancelled()) != 0) {
            related.cancel(wasInterrupted());
        }
    }

    private AbstractFuture$Waiter clearWaiters() {
        AbstractFuture$Waiter head;
        do {
            head = this.waiters;
        } while (!ATOMIC_HELPER.casWaiters(this, head, AbstractFuture$Waiter.TOMBSTONE));
        return head;
    }

    private AbstractFuture$Listener clearListeners() {
        AbstractFuture$Listener head;
        do {
            head = this.listeners;
        } while (!ATOMIC_HELPER.casListeners(this, head, AbstractFuture$Listener.TOMBSTONE));
        return head;
    }

    private static void executeListener(Runnable runnable, Executor executor) {
        try {
            executor.execute(runnable);
        } catch (RuntimeException e) {
            Logger logger = log;
            Level level = Level.SEVERE;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("RuntimeException while executing runnable ");
            stringBuilder.append(runnable);
            stringBuilder.append(" with executor ");
            stringBuilder.append(executor);
            logger.log(level, stringBuilder.toString(), e);
        }
    }

    static final CancellationException cancellationExceptionWithCause(@Nullable String message, @Nullable Throwable cause) {
        CancellationException exception = new CancellationException(message);
        exception.initCause(cause);
        return exception;
    }
}
