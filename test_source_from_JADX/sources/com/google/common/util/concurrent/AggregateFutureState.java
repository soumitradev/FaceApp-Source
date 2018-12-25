package com.google.common.util.concurrent;

import com.google.common.annotations.GwtCompatible;
import com.google.common.collect.Sets;
import java.util.Set;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

@GwtCompatible(emulated = true)
abstract class AggregateFutureState {
    private static final AtomicIntegerFieldUpdater<AggregateFutureState> REMAINING_COUNT_UPDATER = AtomicIntegerFieldUpdater.newUpdater(AggregateFutureState.class, "remaining");
    private static final AtomicReferenceFieldUpdater<AggregateFutureState, Set<Throwable>> SEEN_EXCEPTIONS_UDPATER = AtomicReferenceFieldUpdater.newUpdater(AggregateFutureState.class, Set.class, "seenExceptions");
    private volatile int remaining;
    private volatile Set<Throwable> seenExceptions = null;

    abstract void addInitialException(Set<Throwable> set);

    AggregateFutureState(int remainingFutures) {
        this.remaining = remainingFutures;
    }

    final Set<Throwable> getOrInitSeenExceptions() {
        Set<Throwable> seenExceptionsLocal = this.seenExceptions;
        if (seenExceptionsLocal != null) {
            return seenExceptionsLocal;
        }
        seenExceptionsLocal = Sets.newConcurrentHashSet();
        addInitialException(seenExceptionsLocal);
        SEEN_EXCEPTIONS_UDPATER.compareAndSet(this, null, seenExceptionsLocal);
        return this.seenExceptions;
    }

    final int decrementRemainingAndGet() {
        return REMAINING_COUNT_UPDATER.decrementAndGet(this);
    }
}
