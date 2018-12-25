package com.google.common.cache;

import com.google.common.base.Function;
import com.google.common.base.Stopwatch;
import com.google.common.cache.LocalCache.ReferenceEntry;
import com.google.common.cache.LocalCache.ValueReference;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.SettableFuture;
import com.google.common.util.concurrent.Uninterruptibles;
import java.lang.ref.ReferenceQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nullable;

class LocalCache$LoadingValueReference<K, V> implements ValueReference<K, V> {
    final SettableFuture<V> futureValue;
    volatile ValueReference<K, V> oldValue;
    final Stopwatch stopwatch;

    /* renamed from: com.google.common.cache.LocalCache$LoadingValueReference$1 */
    class C09001 implements Function<V, V> {
        C09001() {
        }

        public V apply(V newValue) {
            LocalCache$LoadingValueReference.this.set(newValue);
            return newValue;
        }
    }

    public LocalCache$LoadingValueReference() {
        this(LocalCache.unset());
    }

    public LocalCache$LoadingValueReference(ValueReference<K, V> oldValue) {
        this.futureValue = SettableFuture.create();
        this.stopwatch = Stopwatch.createUnstarted();
        this.oldValue = oldValue;
    }

    public boolean isLoading() {
        return true;
    }

    public boolean isActive() {
        return this.oldValue.isActive();
    }

    public int getWeight() {
        return this.oldValue.getWeight();
    }

    public boolean set(@Nullable V newValue) {
        return this.futureValue.set(newValue);
    }

    public boolean setException(Throwable t) {
        return this.futureValue.setException(t);
    }

    private ListenableFuture<V> fullyFailedFuture(Throwable t) {
        return Futures.immediateFailedFuture(t);
    }

    public void notifyNewValue(@Nullable V newValue) {
        if (newValue != null) {
            set(newValue);
        } else {
            this.oldValue = LocalCache.unset();
        }
    }

    public ListenableFuture<V> loadFuture(K key, CacheLoader<? super K, V> loader) {
        ListenableFuture<V> newValue;
        try {
            this.stopwatch.start();
            V previousValue = this.oldValue.get();
            if (previousValue == null) {
                V newValue2 = loader.load(key);
                return set(newValue2) ? this.futureValue : Futures.immediateFuture(newValue2);
            }
            newValue = loader.reload(key, previousValue);
            if (newValue == null) {
                return Futures.immediateFuture(null);
            }
            return Futures.transform(newValue, new C09001());
        } catch (Throwable t) {
            newValue = setException(t) ? this.futureValue : fullyFailedFuture(t);
            if (t instanceof InterruptedException) {
                Thread.currentThread().interrupt();
            }
            return newValue;
        }
    }

    public long elapsedNanos() {
        return this.stopwatch.elapsed(TimeUnit.NANOSECONDS);
    }

    public V waitForValue() throws ExecutionException {
        return Uninterruptibles.getUninterruptibly(this.futureValue);
    }

    public V get() {
        return this.oldValue.get();
    }

    public ValueReference<K, V> getOldValue() {
        return this.oldValue;
    }

    public ReferenceEntry<K, V> getEntry() {
        return null;
    }

    public ValueReference<K, V> copyFor(ReferenceQueue<V> referenceQueue, @Nullable V v, ReferenceEntry<K, V> referenceEntry) {
        return this;
    }
}
