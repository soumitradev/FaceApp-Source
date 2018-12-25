package com.google.common.cache;

import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Equivalence;
import com.google.common.base.Preconditions;
import com.google.common.base.Stopwatch;
import com.google.common.base.Ticker;
import com.google.common.cache.AbstractCache.SimpleStatsCounter;
import com.google.common.cache.AbstractCache.StatsCounter;
import com.google.common.cache.CacheLoader.InvalidCacheLoadException;
import com.google.common.cache.CacheLoader.UnsupportedLoadingOperationException;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterators;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.common.primitives.Ints;
import com.google.common.util.concurrent.ExecutionError;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.UncheckedExecutionException;
import com.google.common.util.concurrent.Uninterruptibles;
import com.google.j2objc.annotations.Weak;
import java.io.Serializable;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Nullable;
import javax.annotation.concurrent.GuardedBy;

@GwtCompatible(emulated = true)
class LocalCache<K, V> extends AbstractMap<K, V> implements ConcurrentMap<K, V> {
    static final int CONTAINS_VALUE_RETRIES = 3;
    static final Queue<? extends Object> DISCARDING_QUEUE = new LocalCache$2();
    static final int DRAIN_MAX = 16;
    static final int DRAIN_THRESHOLD = 63;
    static final int MAXIMUM_CAPACITY = 1073741824;
    static final int MAX_SEGMENTS = 65536;
    static final ValueReference<Object, Object> UNSET = new LocalCache$1();
    static final Logger logger = Logger.getLogger(LocalCache.class.getName());
    final int concurrencyLevel;
    @Nullable
    final CacheLoader<? super K, V> defaultLoader;
    final LocalCache$EntryFactory entryFactory;
    Set<Entry<K, V>> entrySet;
    final long expireAfterAccessNanos;
    final long expireAfterWriteNanos;
    final StatsCounter globalStatsCounter;
    final Equivalence<Object> keyEquivalence;
    Set<K> keySet;
    final LocalCache$Strength keyStrength;
    final long maxWeight;
    final long refreshNanos;
    final RemovalListener<K, V> removalListener;
    final Queue<RemovalNotification<K, V>> removalNotificationQueue;
    final int segmentMask;
    final int segmentShift;
    final Segment<K, V>[] segments;
    final Ticker ticker;
    final Equivalence<Object> valueEquivalence;
    final LocalCache$Strength valueStrength;
    Collection<V> values;
    final Weigher<K, V> weigher;

    interface ReferenceEntry<K, V> {
        long getAccessTime();

        int getHash();

        @Nullable
        K getKey();

        @Nullable
        ReferenceEntry<K, V> getNext();

        ReferenceEntry<K, V> getNextInAccessQueue();

        ReferenceEntry<K, V> getNextInWriteQueue();

        ReferenceEntry<K, V> getPreviousInAccessQueue();

        ReferenceEntry<K, V> getPreviousInWriteQueue();

        ValueReference<K, V> getValueReference();

        long getWriteTime();

        void setAccessTime(long j);

        void setNextInAccessQueue(ReferenceEntry<K, V> referenceEntry);

        void setNextInWriteQueue(ReferenceEntry<K, V> referenceEntry);

        void setPreviousInAccessQueue(ReferenceEntry<K, V> referenceEntry);

        void setPreviousInWriteQueue(ReferenceEntry<K, V> referenceEntry);

        void setValueReference(ValueReference<K, V> valueReference);

        void setWriteTime(long j);
    }

    static class Segment<K, V> extends ReentrantLock {
        @GuardedBy("this")
        final Queue<ReferenceEntry<K, V>> accessQueue;
        volatile int count;
        final ReferenceQueue<K> keyReferenceQueue;
        @Weak
        final LocalCache<K, V> map;
        final long maxSegmentWeight;
        int modCount;
        final AtomicInteger readCount = new AtomicInteger();
        final Queue<ReferenceEntry<K, V>> recencyQueue;
        final StatsCounter statsCounter;
        volatile AtomicReferenceArray<ReferenceEntry<K, V>> table;
        int threshold;
        @GuardedBy("this")
        long totalWeight;
        final ReferenceQueue<V> valueReferenceQueue;
        @GuardedBy("this")
        final Queue<ReferenceEntry<K, V>> writeQueue;

        Segment(LocalCache<K, V> map, int initialCapacity, long maxSegmentWeight, StatsCounter statsCounter) {
            this.map = map;
            this.maxSegmentWeight = maxSegmentWeight;
            this.statsCounter = (StatsCounter) Preconditions.checkNotNull(statsCounter);
            initTable(newEntryArray(initialCapacity));
            ReferenceQueue referenceQueue = null;
            this.keyReferenceQueue = map.usesKeyReferences() ? new ReferenceQueue() : null;
            if (map.usesValueReferences()) {
                referenceQueue = new ReferenceQueue();
            }
            this.valueReferenceQueue = referenceQueue;
            this.recencyQueue = map.usesAccessQueue() ? new ConcurrentLinkedQueue() : LocalCache.discardingQueue();
            this.writeQueue = map.usesWriteQueue() ? new LocalCache$WriteQueue() : LocalCache.discardingQueue();
            this.accessQueue = map.usesAccessQueue() ? new LocalCache$AccessQueue() : LocalCache.discardingQueue();
        }

        AtomicReferenceArray<ReferenceEntry<K, V>> newEntryArray(int size) {
            return new AtomicReferenceArray(size);
        }

        void initTable(AtomicReferenceArray<ReferenceEntry<K, V>> newTable) {
            this.threshold = (newTable.length() * 3) / 4;
            if (!this.map.customWeigher() && ((long) this.threshold) == this.maxSegmentWeight) {
                this.threshold++;
            }
            this.table = newTable;
        }

        @GuardedBy("this")
        ReferenceEntry<K, V> newEntry(K key, int hash, @Nullable ReferenceEntry<K, V> next) {
            return this.map.entryFactory.newEntry(this, Preconditions.checkNotNull(key), hash, next);
        }

        @GuardedBy("this")
        ReferenceEntry<K, V> copyEntry(ReferenceEntry<K, V> original, ReferenceEntry<K, V> newNext) {
            if (original.getKey() == null) {
                return null;
            }
            ValueReference<K, V> valueReference = original.getValueReference();
            V value = valueReference.get();
            if (value == null && valueReference.isActive()) {
                return null;
            }
            ReferenceEntry<K, V> newEntry = this.map.entryFactory.copyEntry(this, original, newNext);
            newEntry.setValueReference(valueReference.copyFor(this.valueReferenceQueue, value, newEntry));
            return newEntry;
        }

        @GuardedBy("this")
        void setValue(ReferenceEntry<K, V> entry, K key, V value, long now) {
            ValueReference<K, V> previous = entry.getValueReference();
            int weight = this.map.weigher.weigh(key, value);
            Preconditions.checkState(weight >= 0, "Weights must be non-negative");
            entry.setValueReference(this.map.valueStrength.referenceValue(this, entry, value, weight));
            recordWrite(entry, weight, now);
            previous.notifyNewValue(value);
        }

        V get(K key, int hash, CacheLoader<? super K, V> loader) throws ExecutionException {
            Preconditions.checkNotNull(key);
            Preconditions.checkNotNull(loader);
            try {
                if (this.count != 0) {
                    ReferenceEntry<K, V> e = getEntry(key, hash);
                    if (e != null) {
                        long now = this.map.ticker.read();
                        V value = getLiveValue(e, now);
                        if (value != null) {
                            recordRead(e, now);
                            this.statsCounter.recordHits(1);
                            V scheduleRefresh = scheduleRefresh(e, key, hash, value, now, loader);
                            postReadCleanup();
                            return scheduleRefresh;
                        }
                        ValueReference<K, V> valueReference = e.getValueReference();
                        if (valueReference.isLoading()) {
                            V waitForLoadingValue = waitForLoadingValue(e, key, valueReference);
                            postReadCleanup();
                            return waitForLoadingValue;
                        }
                    }
                }
                V lockedGetOrLoad = lockedGetOrLoad(key, hash, loader);
                postReadCleanup();
                return lockedGetOrLoad;
            } catch (ExecutionException ee) {
                Throwable cause = ee.getCause();
                if (cause instanceof Error) {
                    throw new ExecutionError((Error) cause);
                } else if (cause instanceof RuntimeException) {
                    throw new UncheckedExecutionException(cause);
                } else {
                    throw ee;
                }
            } catch (Throwable th) {
                postReadCleanup();
            }
        }

        V lockedGetOrLoad(K key, int hash, CacheLoader<? super K, V> loader) throws ExecutionException {
            Throwable th;
            CacheLoader<? super K, V> cacheLoader;
            Throwable th2;
            ValueReference<K, V> valueReference;
            ReferenceEntry<K, V> e;
            Throwable th3;
            Segment segment = this;
            K k = key;
            int i = hash;
            ValueReference<K, V> valueReference2 = null;
            LocalCache$LoadingValueReference<K, V> loadingValueReference = null;
            boolean createNewEntry = true;
            lock();
            LocalCache$LoadingValueReference<K, V> loadingValueReference2;
            try {
                V loadSync;
                long now = segment.map.ticker.read();
                preWriteCleanup(now);
                int newCount = segment.count - 1;
                AtomicReferenceArray<ReferenceEntry<K, V>> table = segment.table;
                int index = (table.length() - 1) & i;
                ReferenceEntry<K, V> e2 = (ReferenceEntry) table.get(index);
                ReferenceEntry<K, V> first = e2;
                while (e2 != null) {
                    try {
                        K entryKey = e2.getKey();
                        if (e2.getHash() == i && entryKey != null && segment.map.keyEquivalence.equivalent(k, entryKey)) {
                            valueReference2 = e2.getValueReference();
                            try {
                                if (valueReference2.isLoading()) {
                                    createNewEntry = false;
                                    loadingValueReference2 = loadingValueReference;
                                } else {
                                    V value = valueReference2.get();
                                    if (value == null) {
                                        loadingValueReference2 = loadingValueReference;
                                        enqueueNotification(entryKey, i, valueReference2, RemovalCause.COLLECTED);
                                    } else {
                                        loadingValueReference2 = loadingValueReference;
                                        try {
                                            if (segment.map.isExpired(e2, now)) {
                                                enqueueNotification(entryKey, i, valueReference2, RemovalCause.EXPIRED);
                                            } else {
                                                recordLockedRead(e2, now);
                                                try {
                                                    segment.statsCounter.recordHits(1);
                                                    unlock();
                                                    postWriteCleanup();
                                                    return value;
                                                } catch (Throwable th4) {
                                                    th = th4;
                                                    cacheLoader = loader;
                                                    th2 = th;
                                                    unlock();
                                                    postWriteCleanup();
                                                    throw th2;
                                                }
                                            }
                                        } catch (Throwable th5) {
                                            valueReference = valueReference2;
                                            cacheLoader = loader;
                                            th2 = th5;
                                            unlock();
                                            postWriteCleanup();
                                            throw th2;
                                        }
                                    }
                                    segment.writeQueue.remove(e2);
                                    segment.accessQueue.remove(e2);
                                    segment.count = newCount;
                                }
                                if (createNewEntry) {
                                    loadingValueReference = loadingValueReference2;
                                } else {
                                    loadingValueReference = new LocalCache$LoadingValueReference();
                                    if (e2 != null) {
                                        try {
                                            e = newEntry(k, i, first);
                                            try {
                                                e.setValueReference(loadingValueReference);
                                                table.set(index, e);
                                                e2 = e;
                                            } catch (Throwable th6) {
                                                th5 = th6;
                                                cacheLoader = loader;
                                                valueReference = valueReference2;
                                                loadingValueReference2 = loadingValueReference;
                                                e2 = e;
                                                th2 = th5;
                                                unlock();
                                                postWriteCleanup();
                                                throw th2;
                                            }
                                        } catch (Throwable th7) {
                                            th5 = th7;
                                            cacheLoader = loader;
                                            valueReference = valueReference2;
                                            loadingValueReference2 = loadingValueReference;
                                            th2 = th5;
                                            unlock();
                                            postWriteCleanup();
                                            throw th2;
                                        }
                                    }
                                    e2.setValueReference(loadingValueReference);
                                }
                                unlock();
                                postWriteCleanup();
                                if (createNewEntry) {
                                    cacheLoader = loader;
                                    return waitForLoadingValue(e2, k, valueReference2);
                                }
                                try {
                                    synchronized (e2) {
                                        try {
                                            loadSync = loadSync(k, i, loadingValueReference, loader);
                                        } catch (Throwable th8) {
                                            th5 = th8;
                                        }
                                    }
                                    segment.statsCounter.recordMisses(1);
                                    return loadSync;
                                } catch (Throwable th9) {
                                    th5 = th9;
                                    cacheLoader = loader;
                                    th3 = th5;
                                    segment.statsCounter.recordMisses(1);
                                    throw th3;
                                }
                            } catch (Throwable th52) {
                                valueReference = valueReference2;
                                loadingValueReference2 = loadingValueReference;
                                cacheLoader = loader;
                                th2 = th52;
                                unlock();
                                postWriteCleanup();
                                throw th2;
                            }
                        }
                        loadingValueReference2 = loadingValueReference;
                        e2 = e2.getNext();
                        loadingValueReference = loadingValueReference2;
                    } catch (Throwable th10) {
                        th52 = th10;
                        cacheLoader = loader;
                        valueReference = valueReference2;
                    }
                }
                loadingValueReference2 = loadingValueReference;
                if (createNewEntry) {
                    loadingValueReference = loadingValueReference2;
                } else {
                    loadingValueReference = new LocalCache$LoadingValueReference();
                    if (e2 != null) {
                        e2.setValueReference(loadingValueReference);
                    } else {
                        e = newEntry(k, i, first);
                        e.setValueReference(loadingValueReference);
                        table.set(index, e);
                        e2 = e;
                    }
                }
                unlock();
                postWriteCleanup();
                if (createNewEntry) {
                    cacheLoader = loader;
                    return waitForLoadingValue(e2, k, valueReference2);
                }
                synchronized (e2) {
                    loadSync = loadSync(k, i, loadingValueReference, loader);
                }
                segment.statsCounter.recordMisses(1);
                return loadSync;
            } catch (Throwable th11) {
                th52 = th11;
                cacheLoader = loader;
                loadingValueReference2 = null;
                valueReference = null;
                Object obj = null;
                th2 = th52;
                unlock();
                postWriteCleanup();
                throw th2;
            }
        }

        V waitForLoadingValue(ReferenceEntry<K, V> e, K key, ValueReference<K, V> valueReference) throws ExecutionException {
            if (valueReference.isLoading()) {
                Preconditions.checkState(Thread.holdsLock(e) ^ true, "Recursive load of: %s", new Object[]{key});
                try {
                    V value = valueReference.waitForValue();
                    if (value == null) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("CacheLoader returned null for key ");
                        stringBuilder.append(key);
                        stringBuilder.append(".");
                        throw new InvalidCacheLoadException(stringBuilder.toString());
                    }
                    recordRead(e, this.map.ticker.read());
                    return value;
                } finally {
                    this.statsCounter.recordMisses(1);
                }
            } else {
                throw new AssertionError();
            }
        }

        V loadSync(K key, int hash, LocalCache$LoadingValueReference<K, V> loadingValueReference, CacheLoader<? super K, V> loader) throws ExecutionException {
            return getAndRecordStats(key, hash, loadingValueReference, loadingValueReference.loadFuture(key, loader));
        }

        ListenableFuture<V> loadAsync(K key, int hash, LocalCache$LoadingValueReference<K, V> loadingValueReference, CacheLoader<? super K, V> loader) {
            ListenableFuture<V> loadingFuture = loadingValueReference.loadFuture(key, loader);
            loadingFuture.addListener(new LocalCache$Segment$1(this, key, hash, loadingValueReference, loadingFuture), MoreExecutors.directExecutor());
            return loadingFuture;
        }

        V getAndRecordStats(K key, int hash, LocalCache$LoadingValueReference<K, V> loadingValueReference, ListenableFuture<V> newValue) throws ExecutionException {
            V value = null;
            try {
                value = Uninterruptibles.getUninterruptibly(newValue);
                if (value == null) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("CacheLoader returned null for key ");
                    stringBuilder.append(key);
                    stringBuilder.append(".");
                    throw new InvalidCacheLoadException(stringBuilder.toString());
                }
                this.statsCounter.recordLoadSuccess(loadingValueReference.elapsedNanos());
                storeLoadedValue(key, hash, loadingValueReference, value);
                return value;
            } finally {
                if (value == null) {
                    this.statsCounter.recordLoadException(loadingValueReference.elapsedNanos());
                    removeLoadingValue(key, hash, loadingValueReference);
                }
            }
        }

        V scheduleRefresh(ReferenceEntry<K, V> entry, K key, int hash, V oldValue, long now, CacheLoader<? super K, V> loader) {
            if (this.map.refreshes() && now - entry.getWriteTime() > this.map.refreshNanos && !entry.getValueReference().isLoading()) {
                V newValue = refresh(key, hash, loader, true);
                if (newValue != null) {
                    return newValue;
                }
            }
            return oldValue;
        }

        @Nullable
        V refresh(K key, int hash, CacheLoader<? super K, V> loader, boolean checkTime) {
            LocalCache$LoadingValueReference<K, V> loadingValueReference = insertLoadingValueReference(key, hash, checkTime);
            if (loadingValueReference == null) {
                return null;
            }
            ListenableFuture<V> result = loadAsync(key, hash, loadingValueReference, loader);
            if (result.isDone()) {
                try {
                    return Uninterruptibles.getUninterruptibly(result);
                } catch (Throwable th) {
                }
            }
            return null;
        }

        @Nullable
        LocalCache$LoadingValueReference<K, V> insertLoadingValueReference(K key, int hash, boolean checkTime) {
            Segment segment = this;
            K k = key;
            int i = hash;
            ReferenceEntry<K, V> e = null;
            lock();
            try {
                long now = segment.map.ticker.read();
                preWriteCleanup(now);
                AtomicReferenceArray<ReferenceEntry<K, V>> table = segment.table;
                int index = (table.length() - 1) & i;
                ReferenceEntry<K, V> first = (ReferenceEntry) table.get(index);
                e = first;
                while (e != null) {
                    K entryKey = e.getKey();
                    if (e.getHash() == i && entryKey != null && segment.map.keyEquivalence.equivalent(k, entryKey)) {
                        LocalCache$LoadingValueReference<K, V> loadingValueReference;
                        ValueReference<K, V> valueReference = e.getValueReference();
                        if (!valueReference.isLoading()) {
                            if (!checkTime || now - e.getWriteTime() >= segment.map.refreshNanos) {
                                segment.modCount++;
                                loadingValueReference = new LocalCache$LoadingValueReference(valueReference);
                                e.setValueReference(loadingValueReference);
                                unlock();
                                postWriteCleanup();
                                return loadingValueReference;
                            }
                        }
                        loadingValueReference = null;
                        unlock();
                        postWriteCleanup();
                        return loadingValueReference;
                    }
                    e = e.getNext();
                }
                segment.modCount++;
                LocalCache$LoadingValueReference<K, V> loadingValueReference2 = new LocalCache$LoadingValueReference();
                e = newEntry(k, i, first);
                e.setValueReference(loadingValueReference2);
                table.set(index, e);
                unlock();
                postWriteCleanup();
                return loadingValueReference2;
            } catch (Throwable th) {
                Throwable e2 = th;
                unlock();
                postWriteCleanup();
            }
        }

        void tryDrainReferenceQueues() {
            if (tryLock()) {
                try {
                    drainReferenceQueues();
                } finally {
                    unlock();
                }
            }
        }

        @GuardedBy("this")
        void drainReferenceQueues() {
            if (this.map.usesKeyReferences()) {
                drainKeyReferenceQueue();
            }
            if (this.map.usesValueReferences()) {
                drainValueReferenceQueue();
            }
        }

        @GuardedBy("this")
        void drainKeyReferenceQueue() {
            int i = 0;
            while (true) {
                Reference<? extends K> poll = this.keyReferenceQueue.poll();
                Reference<? extends K> ref = poll;
                if (poll != null) {
                    this.map.reclaimKey((ReferenceEntry) ref);
                    i++;
                    if (i == 16) {
                        return;
                    }
                } else {
                    return;
                }
            }
        }

        @GuardedBy("this")
        void drainValueReferenceQueue() {
            int i = 0;
            while (true) {
                Reference<? extends V> poll = this.valueReferenceQueue.poll();
                Reference<? extends V> ref = poll;
                if (poll != null) {
                    this.map.reclaimValue((ValueReference) ref);
                    i++;
                    if (i == 16) {
                        return;
                    }
                } else {
                    return;
                }
            }
        }

        void clearReferenceQueues() {
            if (this.map.usesKeyReferences()) {
                clearKeyReferenceQueue();
            }
            if (this.map.usesValueReferences()) {
                clearValueReferenceQueue();
            }
        }

        void clearKeyReferenceQueue() {
            while (this.keyReferenceQueue.poll() != null) {
            }
        }

        void clearValueReferenceQueue() {
            while (this.valueReferenceQueue.poll() != null) {
            }
        }

        void recordRead(ReferenceEntry<K, V> entry, long now) {
            if (this.map.recordsAccess()) {
                entry.setAccessTime(now);
            }
            this.recencyQueue.add(entry);
        }

        @GuardedBy("this")
        void recordLockedRead(ReferenceEntry<K, V> entry, long now) {
            if (this.map.recordsAccess()) {
                entry.setAccessTime(now);
            }
            this.accessQueue.add(entry);
        }

        @GuardedBy("this")
        void recordWrite(ReferenceEntry<K, V> entry, int weight, long now) {
            drainRecencyQueue();
            this.totalWeight += (long) weight;
            if (this.map.recordsAccess()) {
                entry.setAccessTime(now);
            }
            if (this.map.recordsWrite()) {
                entry.setWriteTime(now);
            }
            this.accessQueue.add(entry);
            this.writeQueue.add(entry);
        }

        @GuardedBy("this")
        void drainRecencyQueue() {
            while (true) {
                ReferenceEntry<K, V> referenceEntry = (ReferenceEntry) this.recencyQueue.poll();
                ReferenceEntry<K, V> e = referenceEntry;
                if (referenceEntry == null) {
                    return;
                }
                if (this.accessQueue.contains(e)) {
                    this.accessQueue.add(e);
                }
            }
        }

        void tryExpireEntries(long now) {
            if (tryLock()) {
                try {
                    expireEntries(now);
                } finally {
                    unlock();
                }
            }
        }

        @GuardedBy("this")
        void expireEntries(long now) {
            drainRecencyQueue();
            ReferenceEntry<K, V> e;
            do {
                ReferenceEntry<K, V> referenceEntry = (ReferenceEntry) this.writeQueue.peek();
                e = referenceEntry;
                if (referenceEntry == null || !this.map.isExpired(e, now)) {
                    do {
                        referenceEntry = (ReferenceEntry) this.accessQueue.peek();
                        e = referenceEntry;
                        if (referenceEntry == null || !this.map.isExpired(e, now)) {
                            return;
                        }
                    } while (removeEntry(e, e.getHash(), RemovalCause.EXPIRED));
                    throw new AssertionError();
                }
            } while (removeEntry(e, e.getHash(), RemovalCause.EXPIRED));
            throw new AssertionError();
        }

        @GuardedBy("this")
        void enqueueNotification(ReferenceEntry<K, V> entry, RemovalCause cause) {
            enqueueNotification(entry.getKey(), entry.getHash(), entry.getValueReference(), cause);
        }

        @GuardedBy("this")
        void enqueueNotification(@Nullable K key, int hash, ValueReference<K, V> valueReference, RemovalCause cause) {
            this.totalWeight -= (long) valueReference.getWeight();
            if (cause.wasEvicted()) {
                this.statsCounter.recordEviction();
            }
            if (this.map.removalNotificationQueue != LocalCache.DISCARDING_QUEUE) {
                this.map.removalNotificationQueue.offer(RemovalNotification.create(key, valueReference.get(), cause));
            }
        }

        @GuardedBy("this")
        void evictEntries(ReferenceEntry<K, V> newest) {
            if (this.map.evictsBySize()) {
                drainRecencyQueue();
                if (((long) newest.getValueReference().getWeight()) <= this.maxSegmentWeight || removeEntry(newest, newest.getHash(), RemovalCause.SIZE)) {
                    while (this.totalWeight > this.maxSegmentWeight) {
                        ReferenceEntry<K, V> e = getNextEvictable();
                        if (!removeEntry(e, e.getHash(), RemovalCause.SIZE)) {
                            throw new AssertionError();
                        }
                    }
                    return;
                }
                throw new AssertionError();
            }
        }

        @GuardedBy("this")
        ReferenceEntry<K, V> getNextEvictable() {
            for (ReferenceEntry<K, V> e : this.accessQueue) {
                if (e.getValueReference().getWeight() > 0) {
                    return e;
                }
            }
            throw new AssertionError();
        }

        ReferenceEntry<K, V> getFirst(int hash) {
            AtomicReferenceArray<ReferenceEntry<K, V>> table = this.table;
            return (ReferenceEntry) table.get((table.length() - 1) & hash);
        }

        @Nullable
        ReferenceEntry<K, V> getEntry(Object key, int hash) {
            for (ReferenceEntry<K, V> e = getFirst(hash); e != null; e = e.getNext()) {
                if (e.getHash() == hash) {
                    K entryKey = e.getKey();
                    if (entryKey == null) {
                        tryDrainReferenceQueues();
                    } else if (this.map.keyEquivalence.equivalent(key, entryKey)) {
                        return e;
                    }
                }
            }
            return null;
        }

        @Nullable
        ReferenceEntry<K, V> getLiveEntry(Object key, int hash, long now) {
            ReferenceEntry<K, V> e = getEntry(key, hash);
            if (e == null) {
                return null;
            }
            if (!this.map.isExpired(e, now)) {
                return e;
            }
            tryExpireEntries(now);
            return null;
        }

        V getLiveValue(ReferenceEntry<K, V> entry, long now) {
            if (entry.getKey() == null) {
                tryDrainReferenceQueues();
                return null;
            }
            V value = entry.getValueReference().get();
            if (value == null) {
                tryDrainReferenceQueues();
                return null;
            } else if (!this.map.isExpired(entry, now)) {
                return value;
            } else {
                tryExpireEntries(now);
                return null;
            }
        }

        @Nullable
        V get(Object key, int hash) {
            try {
                V v = null;
                if (this.count != 0) {
                    long now = this.map.ticker.read();
                    ReferenceEntry<K, V> e = getLiveEntry(key, hash, now);
                    if (e != null) {
                        V value = e.getValueReference().get();
                        if (value != null) {
                            recordRead(e, now);
                            v = scheduleRefresh(e, e.getKey(), hash, value, now, this.map.defaultLoader);
                        } else {
                            tryDrainReferenceQueues();
                        }
                    }
                    postReadCleanup();
                    return v;
                }
                postReadCleanup();
                return v;
            } catch (Throwable th) {
                postReadCleanup();
            }
        }

        boolean containsKey(Object key, int hash) {
            try {
                boolean z = false;
                if (this.count != 0) {
                    ReferenceEntry<K, V> e = getLiveEntry(key, hash, this.map.ticker.read());
                    if (e != null) {
                        if (e.getValueReference().get() != null) {
                            z = true;
                        }
                    }
                }
                postReadCleanup();
                return z;
            } catch (Throwable th) {
                postReadCleanup();
            }
        }

        @VisibleForTesting
        boolean containsValue(Object value) {
            try {
                if (this.count != 0) {
                    long now = this.map.ticker.read();
                    AtomicReferenceArray<ReferenceEntry<K, V>> table = this.table;
                    int length = table.length();
                    for (int i = 0; i < length; i++) {
                        for (ReferenceEntry<K, V> e = (ReferenceEntry) table.get(i); e != null; e = e.getNext()) {
                            V entryValue = getLiveValue(e, now);
                            if (entryValue != null) {
                                if (this.map.valueEquivalence.equivalent(value, entryValue)) {
                                    return true;
                                }
                            }
                        }
                    }
                }
                postReadCleanup();
                return false;
            } finally {
                postReadCleanup();
            }
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        @javax.annotation.Nullable
        V put(K r23, int r24, V r25, boolean r26) {
            /*
            r22 = this;
            r7 = r22;
            r8 = r23;
            r9 = r24;
            r22.lock();
            r1 = r7.map;	 Catch:{ all -> 0x0104 }
            r1 = r1.ticker;	 Catch:{ all -> 0x0104 }
            r1 = r1.read();	 Catch:{ all -> 0x0104 }
            r10 = r1;
            r7.preWriteCleanup(r10);	 Catch:{ all -> 0x0104 }
            r1 = r7.count;	 Catch:{ all -> 0x0104 }
            r1 = r1 + 1;
            r2 = r7.threshold;	 Catch:{ all -> 0x0104 }
            if (r1 <= r2) goto L_0x0024;
        L_0x001d:
            r22.expand();	 Catch:{ all -> 0x0104 }
            r2 = r7.count;	 Catch:{ all -> 0x0104 }
            r1 = r2 + 1;
        L_0x0024:
            r12 = r1;
            r1 = r7.table;	 Catch:{ all -> 0x0104 }
            r13 = r1;
            r1 = r13.length();	 Catch:{ all -> 0x0104 }
            r1 = r1 + -1;
            r14 = r9 & r1;
            r1 = r13.get(r14);	 Catch:{ all -> 0x0104 }
            r1 = (com.google.common.cache.LocalCache.ReferenceEntry) r1;	 Catch:{ all -> 0x0104 }
            r15 = r1;
        L_0x0038:
            r5 = r1;
            r16 = 0;
            if (r5 == 0) goto L_0x00e1;
        L_0x003d:
            r1 = r5.getKey();	 Catch:{ all -> 0x0104 }
            r6 = r1;
            r1 = r5.getHash();	 Catch:{ all -> 0x0104 }
            if (r1 != r9) goto L_0x00d6;
        L_0x0048:
            if (r6 == 0) goto L_0x00d6;
        L_0x004a:
            r1 = r7.map;	 Catch:{ all -> 0x0104 }
            r1 = r1.keyEquivalence;	 Catch:{ all -> 0x0104 }
            r1 = r1.equivalent(r8, r6);	 Catch:{ all -> 0x0104 }
            if (r1 == 0) goto L_0x00d6;
        L_0x0054:
            r1 = r5.getValueReference();	 Catch:{ all -> 0x0104 }
            r4 = r1;
            r1 = r4.get();	 Catch:{ all -> 0x0104 }
            r17 = r1;
            if (r17 != 0) goto L_0x00a9;
        L_0x0061:
            r1 = r7.modCount;	 Catch:{ all -> 0x0104 }
            r1 = r1 + 1;
            r7.modCount = r1;	 Catch:{ all -> 0x0104 }
            r1 = r4.isActive();	 Catch:{ all -> 0x0104 }
            if (r1 == 0) goto L_0x0085;
        L_0x006d:
            r1 = com.google.common.cache.RemovalCause.COLLECTED;	 Catch:{ all -> 0x0104 }
            r7.enqueueNotification(r8, r9, r4, r1);	 Catch:{ all -> 0x0104 }
            r1 = r7;
            r2 = r5;
            r3 = r8;
            r18 = r12;
            r12 = r4;
            r4 = r25;
            r19 = r5;
            r20 = r6;
            r5 = r10;
            r1.setValue(r2, r3, r4, r5);	 Catch:{ all -> 0x0104 }
            r1 = r7.count;	 Catch:{ all -> 0x0104 }
            goto L_0x009a;
        L_0x0085:
            r19 = r5;
            r20 = r6;
            r18 = r12;
            r12 = r4;
            r1 = r7;
            r2 = r19;
            r3 = r8;
            r4 = r25;
            r5 = r10;
            r1.setValue(r2, r3, r4, r5);	 Catch:{ all -> 0x0104 }
            r1 = r7.count;	 Catch:{ all -> 0x0104 }
            r1 = r1 + 1;
        L_0x009a:
            r7.count = r1;	 Catch:{ all -> 0x0104 }
            r5 = r19;
            r7.evictEntries(r5);	 Catch:{ all -> 0x0104 }
        L_0x00a2:
            r22.unlock();
            r22.postWriteCleanup();
            return r16;
        L_0x00a9:
            r20 = r6;
            r18 = r12;
            r12 = r4;
            if (r26 == 0) goto L_0x00bb;
        L_0x00b0:
            r7.recordLockedRead(r5, r10);	 Catch:{ all -> 0x0104 }
        L_0x00b4:
            r22.unlock();
            r22.postWriteCleanup();
            return r17;
        L_0x00bb:
            r1 = r7.modCount;	 Catch:{ all -> 0x0104 }
            r1 = r1 + 1;
            r7.modCount = r1;	 Catch:{ all -> 0x0104 }
            r1 = com.google.common.cache.RemovalCause.REPLACED;	 Catch:{ all -> 0x0104 }
            r7.enqueueNotification(r8, r9, r12, r1);	 Catch:{ all -> 0x0104 }
            r1 = r7;
            r2 = r5;
            r3 = r8;
            r4 = r25;
            r21 = r12;
            r12 = r5;
            r5 = r10;
            r1.setValue(r2, r3, r4, r5);	 Catch:{ all -> 0x0104 }
            r7.evictEntries(r12);	 Catch:{ all -> 0x0104 }
            goto L_0x00b4;
        L_0x00d6:
            r18 = r12;
            r12 = r5;
            r1 = r12.getNext();	 Catch:{ all -> 0x0104 }
            r12 = r18;
            goto L_0x0038;
        L_0x00e1:
            r18 = r12;
            r1 = r7.modCount;	 Catch:{ all -> 0x0104 }
            r1 = r1 + 1;
            r7.modCount = r1;	 Catch:{ all -> 0x0104 }
            r1 = r7.newEntry(r8, r9, r15);	 Catch:{ all -> 0x0104 }
            r12 = r1;
            r1 = r7;
            r2 = r12;
            r3 = r8;
            r4 = r25;
            r5 = r10;
            r1.setValue(r2, r3, r4, r5);	 Catch:{ all -> 0x0104 }
            r13.set(r14, r12);	 Catch:{ all -> 0x0104 }
            r1 = r7.count;	 Catch:{ all -> 0x0104 }
            r1 = r1 + 1;
            r7.count = r1;	 Catch:{ all -> 0x0104 }
            r7.evictEntries(r12);	 Catch:{ all -> 0x0104 }
            goto L_0x00a2;
        L_0x0104:
            r0 = move-exception;
            r1 = r0;
            r22.unlock();
            r22.postWriteCleanup();
            throw r1;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.common.cache.LocalCache.Segment.put(java.lang.Object, int, java.lang.Object, boolean):V");
        }

        @GuardedBy("this")
        void expand() {
            AtomicReferenceArray<ReferenceEntry<K, V>> oldTable = this.table;
            int oldCapacity = oldTable.length();
            if (oldCapacity < 1073741824) {
                int newCount = this.count;
                AtomicReferenceArray<ReferenceEntry<K, V>> newTable = newEntryArray(oldCapacity << 1);
                this.threshold = (newTable.length() * 3) / 4;
                int newMask = newTable.length() - 1;
                for (int oldIndex = 0; oldIndex < oldCapacity; oldIndex++) {
                    ReferenceEntry<K, V> head = (ReferenceEntry) oldTable.get(oldIndex);
                    if (head != null) {
                        ReferenceEntry<K, V> next = head.getNext();
                        int headIndex = head.getHash() & newMask;
                        if (next == null) {
                            newTable.set(headIndex, head);
                        } else {
                            int newIndex;
                            int tailIndex = headIndex;
                            ReferenceEntry<K, V> tail = head;
                            for (ReferenceEntry<K, V> e = next; e != null; e = e.getNext()) {
                                newIndex = e.getHash() & newMask;
                                if (newIndex != tailIndex) {
                                    tailIndex = newIndex;
                                    tail = e;
                                }
                            }
                            newTable.set(tailIndex, tail);
                            int newCount2 = newCount;
                            for (ReferenceEntry<K, V> e2 = head; e2 != tail; e2 = e2.getNext()) {
                                newIndex = e2.getHash() & newMask;
                                ReferenceEntry<K, V> newFirst = copyEntry(e2, (ReferenceEntry) newTable.get(newIndex));
                                if (newFirst != null) {
                                    newTable.set(newIndex, newFirst);
                                } else {
                                    removeCollectedEntry(e2);
                                    newCount2--;
                                }
                            }
                            newCount = newCount2;
                        }
                    }
                }
                this.table = newTable;
                this.count = newCount;
            }
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        boolean replace(K r24, int r25, V r26, V r27) {
            /*
            r23 = this;
            r9 = r23;
            r10 = r24;
            r11 = r25;
            r23.lock();
            r1 = r9.map;	 Catch:{ all -> 0x00e5 }
            r1 = r1.ticker;	 Catch:{ all -> 0x00e5 }
            r1 = r1.read();	 Catch:{ all -> 0x00e5 }
            r12 = r1;
            r9.preWriteCleanup(r12);	 Catch:{ all -> 0x00e5 }
            r1 = r9.table;	 Catch:{ all -> 0x00e5 }
            r14 = r1;
            r1 = r14.length();	 Catch:{ all -> 0x00e5 }
            r15 = 1;
            r1 = r1 - r15;
            r8 = r11 & r1;
            r1 = r14.get(r8);	 Catch:{ all -> 0x00e5 }
            r2 = r1;
            r2 = (com.google.common.cache.LocalCache.ReferenceEntry) r2;	 Catch:{ all -> 0x00e5 }
            r1 = r2;
        L_0x0028:
            r7 = r1;
            r16 = 0;
            if (r7 == 0) goto L_0x00e2;
        L_0x002d:
            r1 = r7.getKey();	 Catch:{ all -> 0x00e5 }
            r6 = r1;
            r1 = r7.getHash();	 Catch:{ all -> 0x00e5 }
            if (r1 != r11) goto L_0x00d7;
        L_0x0038:
            if (r6 == 0) goto L_0x00d7;
        L_0x003a:
            r1 = r9.map;	 Catch:{ all -> 0x00e5 }
            r1 = r1.keyEquivalence;	 Catch:{ all -> 0x00e5 }
            r1 = r1.equivalent(r10, r6);	 Catch:{ all -> 0x00e5 }
            if (r1 == 0) goto L_0x00d7;
        L_0x0044:
            r1 = r7.getValueReference();	 Catch:{ all -> 0x00e5 }
            r5 = r1;
            r1 = r5.get();	 Catch:{ all -> 0x00e5 }
            r4 = r1;
            if (r4 != 0) goto L_0x008d;
        L_0x0050:
            r1 = r5.isActive();	 Catch:{ all -> 0x00e5 }
            if (r1 == 0) goto L_0x007e;
        L_0x0056:
            r1 = r9.count;	 Catch:{ all -> 0x00e5 }
            r17 = r1 + -1;
            r1 = r9.modCount;	 Catch:{ all -> 0x00e5 }
            r1 = r1 + r15;
            r9.modCount = r1;	 Catch:{ all -> 0x00e5 }
            r18 = com.google.common.cache.RemovalCause.COLLECTED;	 Catch:{ all -> 0x00e5 }
            r1 = r9;
            r3 = r7;
            r19 = r4;
            r4 = r6;
            r20 = r5;
            r5 = r11;
            r21 = r6;
            r6 = r20;
            r22 = r7;
            r7 = r18;
            r1 = r1.removeValueFromChain(r2, r3, r4, r5, r6, r7);	 Catch:{ all -> 0x00e5 }
            r3 = r9.count;	 Catch:{ all -> 0x00e5 }
            r3 = r3 - r15;
            r14.set(r8, r1);	 Catch:{ all -> 0x00e5 }
            r9.count = r3;	 Catch:{ all -> 0x00e5 }
            goto L_0x0086;
        L_0x007e:
            r19 = r4;
            r20 = r5;
            r21 = r6;
            r22 = r7;
        L_0x0086:
            r23.unlock();
            r23.postWriteCleanup();
            return r16;
        L_0x008d:
            r19 = r4;
            r20 = r5;
            r21 = r6;
            r22 = r7;
            r1 = r9.map;	 Catch:{ all -> 0x00e5 }
            r1 = r1.valueEquivalence;	 Catch:{ all -> 0x00e5 }
            r7 = r26;
            r6 = r19;
            r1 = r1.equivalent(r7, r6);	 Catch:{ all -> 0x00e5 }
            if (r1 == 0) goto L_0x00cb;
        L_0x00a3:
            r1 = r9.modCount;	 Catch:{ all -> 0x00e5 }
            r1 = r1 + r15;
            r9.modCount = r1;	 Catch:{ all -> 0x00e5 }
            r1 = com.google.common.cache.RemovalCause.REPLACED;	 Catch:{ all -> 0x00e5 }
            r5 = r20;
            r9.enqueueNotification(r10, r11, r5, r1);	 Catch:{ all -> 0x00e5 }
            r3 = r9;
            r4 = r22;
            r1 = r5;
            r5 = r10;
            r17 = r6;
            r6 = r27;
            r18 = r8;
            r7 = r12;
            r3.setValue(r4, r5, r6, r7);	 Catch:{ all -> 0x00e5 }
            r3 = r22;
            r9.evictEntries(r3);	 Catch:{ all -> 0x00e5 }
            r23.unlock();
            r23.postWriteCleanup();
            return r15;
        L_0x00cb:
            r17 = r6;
            r18 = r8;
            r1 = r20;
            r3 = r22;
            r9.recordLockedRead(r3, r12);	 Catch:{ all -> 0x00e5 }
            goto L_0x0086;
        L_0x00d7:
            r3 = r7;
            r18 = r8;
            r1 = r3.getNext();	 Catch:{ all -> 0x00e5 }
            r8 = r18;
            goto L_0x0028;
        L_0x00e2:
            r18 = r8;
            goto L_0x0086;
        L_0x00e5:
            r0 = move-exception;
            r1 = r0;
            r23.unlock();
            r23.postWriteCleanup();
            throw r1;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.common.cache.LocalCache.Segment.replace(java.lang.Object, int, java.lang.Object, java.lang.Object):boolean");
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        @javax.annotation.Nullable
        V replace(K r23, int r24, V r25) {
            /*
            r22 = this;
            r9 = r22;
            r10 = r23;
            r11 = r24;
            r22.lock();
            r1 = r9.map;	 Catch:{ all -> 0x00b7 }
            r1 = r1.ticker;	 Catch:{ all -> 0x00b7 }
            r1 = r1.read();	 Catch:{ all -> 0x00b7 }
            r12 = r1;
            r9.preWriteCleanup(r12);	 Catch:{ all -> 0x00b7 }
            r1 = r9.table;	 Catch:{ all -> 0x00b7 }
            r14 = r1;
            r1 = r14.length();	 Catch:{ all -> 0x00b7 }
            r1 = r1 + -1;
            r15 = r11 & r1;
            r1 = r14.get(r15);	 Catch:{ all -> 0x00b7 }
            r2 = r1;
            r2 = (com.google.common.cache.LocalCache.ReferenceEntry) r2;	 Catch:{ all -> 0x00b7 }
            r1 = r2;
        L_0x0028:
            r8 = r1;
            r16 = 0;
            if (r8 == 0) goto L_0x00b6;
        L_0x002d:
            r1 = r8.getKey();	 Catch:{ all -> 0x00b7 }
            r7 = r1;
            r1 = r8.getHash();	 Catch:{ all -> 0x00b7 }
            if (r1 != r11) goto L_0x00ae;
        L_0x0038:
            if (r7 == 0) goto L_0x00ae;
        L_0x003a:
            r1 = r9.map;	 Catch:{ all -> 0x00b7 }
            r1 = r1.keyEquivalence;	 Catch:{ all -> 0x00b7 }
            r1 = r1.equivalent(r10, r7);	 Catch:{ all -> 0x00b7 }
            if (r1 == 0) goto L_0x00ae;
        L_0x0044:
            r1 = r8.getValueReference();	 Catch:{ all -> 0x00b7 }
            r6 = r1;
            r1 = r6.get();	 Catch:{ all -> 0x00b7 }
            r17 = r1;
            if (r17 != 0) goto L_0x0086;
        L_0x0051:
            r1 = r6.isActive();	 Catch:{ all -> 0x00b7 }
            if (r1 == 0) goto L_0x007b;
        L_0x0057:
            r1 = r9.count;	 Catch:{ all -> 0x00b7 }
            r18 = r1 + -1;
            r1 = r9.modCount;	 Catch:{ all -> 0x00b7 }
            r1 = r1 + 1;
            r9.modCount = r1;	 Catch:{ all -> 0x00b7 }
            r19 = com.google.common.cache.RemovalCause.COLLECTED;	 Catch:{ all -> 0x00b7 }
            r1 = r9;
            r3 = r8;
            r4 = r7;
            r5 = r11;
            r20 = r6;
            r21 = r7;
            r7 = r19;
            r1 = r1.removeValueFromChain(r2, r3, r4, r5, r6, r7);	 Catch:{ all -> 0x00b7 }
            r3 = r9.count;	 Catch:{ all -> 0x00b7 }
            r3 = r3 + -1;
            r14.set(r15, r1);	 Catch:{ all -> 0x00b7 }
            r9.count = r3;	 Catch:{ all -> 0x00b7 }
            goto L_0x007f;
        L_0x007b:
            r20 = r6;
            r21 = r7;
        L_0x007f:
            r22.unlock();
            r22.postWriteCleanup();
            return r16;
        L_0x0086:
            r20 = r6;
            r21 = r7;
            r1 = r9.modCount;	 Catch:{ all -> 0x00b7 }
            r1 = r1 + 1;
            r9.modCount = r1;	 Catch:{ all -> 0x00b7 }
            r1 = com.google.common.cache.RemovalCause.REPLACED;	 Catch:{ all -> 0x00b7 }
            r7 = r20;
            r9.enqueueNotification(r10, r11, r7, r1);	 Catch:{ all -> 0x00b7 }
            r3 = r9;
            r4 = r8;
            r5 = r10;
            r6 = r25;
            r16 = r7;
            r1 = r8;
            r7 = r12;
            r3.setValue(r4, r5, r6, r7);	 Catch:{ all -> 0x00b7 }
            r9.evictEntries(r1);	 Catch:{ all -> 0x00b7 }
            r22.unlock();
            r22.postWriteCleanup();
            return r17;
        L_0x00ae:
            r1 = r8;
            r3 = r1.getNext();	 Catch:{ all -> 0x00b7 }
            r1 = r3;
            goto L_0x0028;
        L_0x00b6:
            goto L_0x007f;
        L_0x00b7:
            r0 = move-exception;
            r1 = r0;
            r22.unlock();
            r22.postWriteCleanup();
            throw r1;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.common.cache.LocalCache.Segment.replace(java.lang.Object, int, java.lang.Object):V");
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        @javax.annotation.Nullable
        V remove(java.lang.Object r20, int r21) {
            /*
            r19 = this;
            r8 = r19;
            r9 = r21;
            r19.lock();
            r1 = r8.map;	 Catch:{ all -> 0x0098 }
            r1 = r1.ticker;	 Catch:{ all -> 0x0098 }
            r1 = r1.read();	 Catch:{ all -> 0x0098 }
            r10 = r1;
            r8.preWriteCleanup(r10);	 Catch:{ all -> 0x0098 }
            r1 = r8.count;	 Catch:{ all -> 0x0098 }
            r12 = r1 + -1;
            r1 = r8.table;	 Catch:{ all -> 0x0098 }
            r13 = r1;
            r1 = r13.length();	 Catch:{ all -> 0x0098 }
            r1 = r1 + -1;
            r14 = r9 & r1;
            r1 = r13.get(r14);	 Catch:{ all -> 0x0098 }
            r2 = r1;
            r2 = (com.google.common.cache.LocalCache.ReferenceEntry) r2;	 Catch:{ all -> 0x0098 }
            r1 = r2;
        L_0x002a:
            r15 = r1;
            r1 = 0;
            if (r15 == 0) goto L_0x0097;
        L_0x002e:
            r3 = r15.getKey();	 Catch:{ all -> 0x0098 }
            r6 = r3;
            r3 = r15.getHash();	 Catch:{ all -> 0x0098 }
            if (r3 != r9) goto L_0x0092;
        L_0x0039:
            if (r6 == 0) goto L_0x0092;
        L_0x003b:
            r3 = r8.map;	 Catch:{ all -> 0x0098 }
            r3 = r3.keyEquivalence;	 Catch:{ all -> 0x0098 }
            r5 = r20;
            r3 = r3.equivalent(r5, r6);	 Catch:{ all -> 0x0098 }
            if (r3 == 0) goto L_0x0092;
        L_0x0047:
            r3 = r15.getValueReference();	 Catch:{ all -> 0x0098 }
            r4 = r3;
            r3 = r4.get();	 Catch:{ all -> 0x0098 }
            r16 = r3;
            if (r16 == 0) goto L_0x0058;
        L_0x0054:
            r1 = com.google.common.cache.RemovalCause.EXPLICIT;	 Catch:{ all -> 0x0098 }
        L_0x0056:
            r7 = r1;
            goto L_0x0061;
        L_0x0058:
            r3 = r4.isActive();	 Catch:{ all -> 0x0098 }
            if (r3 == 0) goto L_0x0087;
        L_0x005e:
            r1 = com.google.common.cache.RemovalCause.COLLECTED;	 Catch:{ all -> 0x0098 }
            goto L_0x0056;
            r1 = r8.modCount;	 Catch:{ all -> 0x0098 }
            r1 = r1 + 1;
            r8.modCount = r1;	 Catch:{ all -> 0x0098 }
            r1 = r8;
            r3 = r15;
            r17 = r4;
            r4 = r6;
            r5 = r9;
            r18 = r6;
            r6 = r17;
            r1 = r1.removeValueFromChain(r2, r3, r4, r5, r6, r7);	 Catch:{ all -> 0x0098 }
            r3 = r8.count;	 Catch:{ all -> 0x0098 }
            r3 = r3 + -1;
            r13.set(r14, r1);	 Catch:{ all -> 0x0098 }
            r8.count = r3;	 Catch:{ all -> 0x0098 }
            r19.unlock();
            r19.postWriteCleanup();
            return r16;
        L_0x0087:
            r17 = r4;
            r18 = r6;
        L_0x008b:
            r19.unlock();
            r19.postWriteCleanup();
            return r1;
        L_0x0092:
            r1 = r15.getNext();	 Catch:{ all -> 0x0098 }
            goto L_0x002a;
        L_0x0097:
            goto L_0x008b;
        L_0x0098:
            r0 = move-exception;
            r1 = r0;
            r19.unlock();
            r19.postWriteCleanup();
            throw r1;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.common.cache.LocalCache.Segment.remove(java.lang.Object, int):V");
        }

        boolean storeLoadedValue(K key, int hash, LocalCache$LoadingValueReference<K, V> oldValueReference, V newValue) {
            Throwable th;
            Segment segment = this;
            K k = key;
            int i = hash;
            ValueReference<K, V> valueReference = oldValueReference;
            lock();
            V table;
            try {
                ReferenceEntry<K, V> e;
                AtomicReferenceArray<ReferenceEntry<K, V>> table2;
                ReferenceEntry<K, V> newEntry;
                long now = segment.map.ticker.read();
                preWriteCleanup(now);
                int i2 = 1;
                int newCount = segment.count + 1;
                if (newCount > segment.threshold) {
                    expand();
                    newCount = segment.count + 1;
                }
                int newCount2 = newCount;
                AtomicReferenceArray<ReferenceEntry<K, V>> table3 = segment.table;
                int index = i & (table3.length() - 1);
                ReferenceEntry<K, V> e2 = (ReferenceEntry) table3.get(index);
                ReferenceEntry<K, V> first = e2;
                while (true) {
                    e = e2;
                    int index2;
                    ReferenceEntry<K, V> first2;
                    if (e != null) {
                        K entryKey = e.getKey();
                        if (e.getHash() == i && entryKey != null && segment.map.keyEquivalence.equivalent(k, entryKey)) {
                            break;
                        }
                        index2 = index;
                        first2 = first;
                        table2 = table3;
                        table = newValue;
                        try {
                            e2 = e.getNext();
                            first = first2;
                            table3 = table2;
                            index = index2;
                            Object first3 = oldValueReference;
                            i2 = 1;
                        } catch (Throwable th2) {
                            th = th2;
                        }
                    } else {
                        index2 = index;
                        first2 = first;
                        table2 = table3;
                        table = newValue;
                        segment.modCount++;
                        newEntry = newEntry(k, i, first2);
                        setValue(newEntry, k, table, now);
                        table2.set(index2, newEntry);
                        segment.count = newCount2;
                        evictEntries(newEntry);
                        unlock();
                        postWriteCleanup();
                        return true;
                    }
                }
                ValueReference<K, V> valueReference2 = e.getValueReference();
                V entryValue = valueReference2.get();
                if (valueReference == valueReference2) {
                    table3 = newValue;
                } else if (entryValue != null || valueReference2 == LocalCache.UNSET) {
                    enqueueNotification(k, i, new LocalCache$WeightedStrongValueReference(newValue, 0), RemovalCause.REPLACED);
                    unlock();
                    postWriteCleanup();
                    return false;
                } else {
                    table2 = table3;
                    table3 = newValue;
                }
                segment.modCount += i2;
                if (oldValueReference.isActive()) {
                    enqueueNotification(k, i, valueReference, entryValue == null ? RemovalCause.COLLECTED : RemovalCause.REPLACED);
                    newCount2--;
                }
                newEntry = e;
                setValue(e, k, table3, now);
                segment.count = newCount2;
                evictEntries(newEntry);
                unlock();
                postWriteCleanup();
                return true;
            } catch (Throwable th3) {
                th = th3;
                table = newValue;
                Throwable th4 = th;
                unlock();
                postWriteCleanup();
                throw th4;
            }
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        boolean remove(java.lang.Object r24, int r25, java.lang.Object r26) {
            /*
            r23 = this;
            r8 = r23;
            r9 = r25;
            r23.lock();
            r1 = r8.map;	 Catch:{ all -> 0x00c3 }
            r1 = r1.ticker;	 Catch:{ all -> 0x00c3 }
            r1 = r1.read();	 Catch:{ all -> 0x00c3 }
            r10 = r1;
            r8.preWriteCleanup(r10);	 Catch:{ all -> 0x00c3 }
            r1 = r8.count;	 Catch:{ all -> 0x00c3 }
            r12 = 1;
            r13 = r1 + -1;
            r1 = r8.table;	 Catch:{ all -> 0x00c3 }
            r14 = r1;
            r1 = r14.length();	 Catch:{ all -> 0x00c3 }
            r1 = r1 - r12;
            r15 = r9 & r1;
            r1 = r14.get(r15);	 Catch:{ all -> 0x00c3 }
            r2 = r1;
            r2 = (com.google.common.cache.LocalCache.ReferenceEntry) r2;	 Catch:{ all -> 0x00c3 }
            r1 = r2;
        L_0x002a:
            r7 = r1;
            r16 = 0;
            if (r7 == 0) goto L_0x00c0;
        L_0x002f:
            r1 = r7.getKey();	 Catch:{ all -> 0x00c3 }
            r6 = r1;
            r1 = r7.getHash();	 Catch:{ all -> 0x00c3 }
            if (r1 != r9) goto L_0x00b0;
        L_0x003a:
            if (r6 == 0) goto L_0x00b0;
        L_0x003c:
            r1 = r8.map;	 Catch:{ all -> 0x00c3 }
            r1 = r1.keyEquivalence;	 Catch:{ all -> 0x00c3 }
            r5 = r24;
            r1 = r1.equivalent(r5, r6);	 Catch:{ all -> 0x00c3 }
            if (r1 == 0) goto L_0x00b0;
        L_0x0048:
            r1 = r7.getValueReference();	 Catch:{ all -> 0x00c3 }
            r4 = r1;
            r1 = r4.get();	 Catch:{ all -> 0x00c3 }
            r3 = r1;
            r1 = r8.map;	 Catch:{ all -> 0x00c3 }
            r1 = r1.valueEquivalence;	 Catch:{ all -> 0x00c3 }
            r12 = r26;
            r1 = r1.equivalent(r12, r3);	 Catch:{ all -> 0x00be }
            if (r1 == 0) goto L_0x0061;
        L_0x005e:
            r1 = com.google.common.cache.RemovalCause.EXPLICIT;	 Catch:{ all -> 0x00be }
        L_0x0060:
            goto L_0x006c;
        L_0x0061:
            if (r3 != 0) goto L_0x00a8;
        L_0x0063:
            r1 = r4.isActive();	 Catch:{ all -> 0x00be }
            if (r1 == 0) goto L_0x00a8;
        L_0x0069:
            r1 = com.google.common.cache.RemovalCause.COLLECTED;	 Catch:{ all -> 0x00be }
            goto L_0x0060;
            r18 = r1;
            r1 = r8.modCount;	 Catch:{ all -> 0x00be }
            r17 = 1;
            r1 = r1 + 1;
            r8.modCount = r1;	 Catch:{ all -> 0x00be }
            r19 = r18;
            r1 = r8;
            r18 = r3;
            r3 = r7;
            r20 = r4;
            r4 = r6;
            r5 = r9;
            r21 = r6;
            r6 = r20;
            r9 = r7;
            r7 = r19;
            r1 = r1.removeValueFromChain(r2, r3, r4, r5, r6, r7);	 Catch:{ all -> 0x00be }
            r3 = r8.count;	 Catch:{ all -> 0x00be }
            r17 = 1;
            r3 = r3 + -1;
            r14.set(r15, r1);	 Catch:{ all -> 0x00be }
            r8.count = r3;	 Catch:{ all -> 0x00be }
            r4 = com.google.common.cache.RemovalCause.EXPLICIT;	 Catch:{ all -> 0x00be }
            r5 = r19;
            if (r5 != r4) goto L_0x00a0;
        L_0x009d:
            r16 = 1;
            goto L_0x00a1;
        L_0x00a1:
            r23.unlock();
            r23.postWriteCleanup();
            return r16;
        L_0x00a8:
            r18 = r3;
            r20 = r4;
            r21 = r6;
            r9 = r7;
            goto L_0x00a1;
        L_0x00b0:
            r12 = r26;
            r9 = r7;
            r17 = 1;
            r1 = r9.getNext();	 Catch:{ all -> 0x00be }
            r9 = r25;
            r12 = 1;
            goto L_0x002a;
        L_0x00be:
            r0 = move-exception;
            goto L_0x00c6;
        L_0x00c0:
            r12 = r26;
            goto L_0x00a1;
        L_0x00c3:
            r0 = move-exception;
            r12 = r26;
        L_0x00c6:
            r1 = r0;
            r23.unlock();
            r23.postWriteCleanup();
            throw r1;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.common.cache.LocalCache.Segment.remove(java.lang.Object, int, java.lang.Object):boolean");
        }

        void clear() {
            if (this.count != 0) {
                lock();
                try {
                    int i;
                    AtomicReferenceArray<ReferenceEntry<K, V>> table = this.table;
                    for (i = 0; i < table.length(); i++) {
                        for (ReferenceEntry<K, V> e = (ReferenceEntry) table.get(i); e != null; e = e.getNext()) {
                            if (e.getValueReference().isActive()) {
                                enqueueNotification(e, RemovalCause.EXPLICIT);
                            }
                        }
                    }
                    for (i = 0; i < table.length(); i++) {
                        table.set(i, null);
                    }
                    clearReferenceQueues();
                    this.writeQueue.clear();
                    this.accessQueue.clear();
                    this.readCount.set(0);
                    this.modCount++;
                    this.count = 0;
                } finally {
                    unlock();
                    postWriteCleanup();
                }
            }
        }

        @GuardedBy("this")
        @Nullable
        ReferenceEntry<K, V> removeValueFromChain(ReferenceEntry<K, V> first, ReferenceEntry<K, V> entry, @Nullable K key, int hash, ValueReference<K, V> valueReference, RemovalCause cause) {
            enqueueNotification(key, hash, valueReference, cause);
            this.writeQueue.remove(entry);
            this.accessQueue.remove(entry);
            if (!valueReference.isLoading()) {
                return removeEntryFromChain(first, entry);
            }
            valueReference.notifyNewValue(null);
            return first;
        }

        @GuardedBy("this")
        @Nullable
        ReferenceEntry<K, V> removeEntryFromChain(ReferenceEntry<K, V> first, ReferenceEntry<K, V> entry) {
            int newCount = this.count;
            ReferenceEntry<K, V> newFirst = entry.getNext();
            int newCount2 = newCount;
            for (ReferenceEntry<K, V> e = first; e != entry; e = e.getNext()) {
                ReferenceEntry<K, V> next = copyEntry(e, newFirst);
                if (next != null) {
                    newFirst = next;
                } else {
                    removeCollectedEntry(e);
                    newCount2--;
                }
            }
            this.count = newCount2;
            return newFirst;
        }

        @GuardedBy("this")
        void removeCollectedEntry(ReferenceEntry<K, V> entry) {
            enqueueNotification(entry, RemovalCause.COLLECTED);
            this.writeQueue.remove(entry);
            this.accessQueue.remove(entry);
        }

        boolean reclaimKey(ReferenceEntry<K, V> entry, int hash) {
            lock();
            try {
                boolean z = true;
                int newCount = this.count - 1;
                AtomicReferenceArray<ReferenceEntry<K, V>> table = this.table;
                int index = (table.length() - 1) & hash;
                ReferenceEntry<K, V> first = (ReferenceEntry) table.get(index);
                for (ReferenceEntry<K, V> e = first; e != null; e = e.getNext()) {
                    if (e == entry) {
                        this.modCount++;
                        ReferenceEntry<K, V> newFirst = removeValueFromChain(first, e, e.getKey(), hash, e.getValueReference(), RemovalCause.COLLECTED);
                        int newCount2 = this.count - 1;
                        table.set(index, newFirst);
                        this.count = newCount2;
                        break;
                    }
                }
                z = false;
                unlock();
                postWriteCleanup();
                return z;
            } catch (Throwable th) {
                unlock();
                postWriteCleanup();
            }
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        boolean reclaimValue(K r19, int r20, com.google.common.cache.LocalCache.ValueReference<K, V> r21) {
            /*
            r18 = this;
            r8 = r18;
            r9 = r20;
            r18.lock();
            r1 = r8.count;	 Catch:{ all -> 0x0091 }
            r10 = 1;
            r11 = r1 + -1;
            r1 = r8.table;	 Catch:{ all -> 0x0091 }
            r12 = r1;
            r1 = r12.length();	 Catch:{ all -> 0x0091 }
            r1 = r1 - r10;
            r13 = r9 & r1;
            r1 = r12.get(r13);	 Catch:{ all -> 0x0091 }
            r2 = r1;
            r2 = (com.google.common.cache.LocalCache.ReferenceEntry) r2;	 Catch:{ all -> 0x0091 }
            r1 = r2;
        L_0x001e:
            r14 = r1;
            r1 = 0;
            if (r14 == 0) goto L_0x0083;
        L_0x0022:
            r3 = r14.getKey();	 Catch:{ all -> 0x0091 }
            r15 = r3;
            r3 = r14.getHash();	 Catch:{ all -> 0x0091 }
            if (r3 != r9) goto L_0x007e;
        L_0x002d:
            if (r15 == 0) goto L_0x007e;
        L_0x002f:
            r3 = r8.map;	 Catch:{ all -> 0x0091 }
            r3 = r3.keyEquivalence;	 Catch:{ all -> 0x0091 }
            r7 = r19;
            r3 = r3.equivalent(r7, r15);	 Catch:{ all -> 0x0091 }
            if (r3 == 0) goto L_0x007e;
        L_0x003b:
            r3 = r14.getValueReference();	 Catch:{ all -> 0x0091 }
            r6 = r3;
            r5 = r21;
            if (r6 != r5) goto L_0x006f;
        L_0x0044:
            r1 = r8.modCount;	 Catch:{ all -> 0x0091 }
            r1 = r1 + r10;
            r8.modCount = r1;	 Catch:{ all -> 0x0091 }
            r16 = com.google.common.cache.RemovalCause.COLLECTED;	 Catch:{ all -> 0x0091 }
            r1 = r8;
            r3 = r14;
            r4 = r15;
            r5 = r9;
            r17 = r6;
            r6 = r21;
            r7 = r16;
            r1 = r1.removeValueFromChain(r2, r3, r4, r5, r6, r7);	 Catch:{ all -> 0x0091 }
            r3 = r8.count;	 Catch:{ all -> 0x0091 }
            r3 = r3 - r10;
            r12.set(r13, r1);	 Catch:{ all -> 0x0091 }
            r8.count = r3;	 Catch:{ all -> 0x0091 }
            r18.unlock();
            r4 = r18.isHeldByCurrentThread();
            if (r4 != 0) goto L_0x006e;
        L_0x006b:
            r18.postWriteCleanup();
        L_0x006e:
            return r10;
        L_0x006f:
            r17 = r6;
            r18.unlock();
            r3 = r18.isHeldByCurrentThread();
            if (r3 != 0) goto L_0x007d;
        L_0x007a:
            r18.postWriteCleanup();
        L_0x007d:
            return r1;
        L_0x007e:
            r1 = r14.getNext();	 Catch:{ all -> 0x0091 }
            goto L_0x001e;
            r18.unlock();
            r3 = r18.isHeldByCurrentThread();
            if (r3 != 0) goto L_0x0090;
        L_0x008d:
            r18.postWriteCleanup();
        L_0x0090:
            return r1;
        L_0x0091:
            r0 = move-exception;
            r1 = r0;
            r18.unlock();
            r2 = r18.isHeldByCurrentThread();
            if (r2 != 0) goto L_0x009f;
        L_0x009c:
            r18.postWriteCleanup();
        L_0x009f:
            throw r1;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.common.cache.LocalCache.Segment.reclaimValue(java.lang.Object, int, com.google.common.cache.LocalCache$ValueReference):boolean");
        }

        boolean removeLoadingValue(K key, int hash, LocalCache$LoadingValueReference<K, V> valueReference) {
            lock();
            try {
                AtomicReferenceArray<ReferenceEntry<K, V>> table = this.table;
                int index = (table.length() - 1) & hash;
                ReferenceEntry<K, V> first = (ReferenceEntry) table.get(index);
                ReferenceEntry<K, V> e = first;
                while (e != null) {
                    K entryKey = e.getKey();
                    if (e.getHash() != hash || entryKey == null || !this.map.keyEquivalence.equivalent(key, entryKey)) {
                        e = e.getNext();
                    } else if (e.getValueReference() == valueReference) {
                        if (valueReference.isActive()) {
                            e.setValueReference(valueReference.getOldValue());
                        } else {
                            table.set(index, removeEntryFromChain(first, e));
                        }
                        unlock();
                        postWriteCleanup();
                        return true;
                    } else {
                        unlock();
                        postWriteCleanup();
                        return false;
                    }
                }
                unlock();
                postWriteCleanup();
                return false;
            } catch (Throwable th) {
                unlock();
                postWriteCleanup();
            }
        }

        @GuardedBy("this")
        boolean removeEntry(ReferenceEntry<K, V> entry, int hash, RemovalCause cause) {
            int newCount = this.count - 1;
            AtomicReferenceArray<ReferenceEntry<K, V>> table = this.table;
            int index = hash & (table.length() - 1);
            ReferenceEntry<K, V> first = (ReferenceEntry) table.get(index);
            ReferenceEntry<K, V> e = first;
            while (true) {
                ReferenceEntry<K, V> e2 = e;
                if (e2 == null) {
                    ReferenceEntry<K, V> referenceEntry = entry;
                    return false;
                } else if (e2 == entry) {
                    r7.modCount++;
                    e = removeValueFromChain(first, e2, e2.getKey(), hash, e2.getValueReference(), cause);
                    int newCount2 = r7.count - 1;
                    table.set(index, e);
                    r7.count = newCount2;
                    return true;
                } else {
                    e = e2.getNext();
                }
            }
        }

        void postReadCleanup() {
            if ((this.readCount.incrementAndGet() & 63) == 0) {
                cleanUp();
            }
        }

        @GuardedBy("this")
        void preWriteCleanup(long now) {
            runLockedCleanup(now);
        }

        void postWriteCleanup() {
            runUnlockedCleanup();
        }

        void cleanUp() {
            runLockedCleanup(this.map.ticker.read());
            runUnlockedCleanup();
        }

        void runLockedCleanup(long now) {
            if (tryLock()) {
                try {
                    drainReferenceQueues();
                    expireEntries(now);
                    this.readCount.set(0);
                } finally {
                    unlock();
                }
            }
        }

        void runUnlockedCleanup() {
            if (!isHeldByCurrentThread()) {
                this.map.processPendingNotifications();
            }
        }
    }

    interface ValueReference<K, V> {
        ValueReference<K, V> copyFor(ReferenceQueue<V> referenceQueue, @Nullable V v, ReferenceEntry<K, V> referenceEntry);

        @Nullable
        V get();

        @Nullable
        ReferenceEntry<K, V> getEntry();

        int getWeight();

        boolean isActive();

        boolean isLoading();

        void notifyNewValue(@Nullable V v);

        V waitForValue() throws ExecutionException;
    }

    static class LocalManualCache<K, V> implements Cache<K, V>, Serializable {
        private static final long serialVersionUID = 1;
        final LocalCache<K, V> localCache;

        LocalManualCache(CacheBuilder<? super K, ? super V> builder) {
            this(new LocalCache(builder, null));
        }

        private LocalManualCache(LocalCache<K, V> localCache) {
            this.localCache = localCache;
        }

        @Nullable
        public V getIfPresent(Object key) {
            return this.localCache.getIfPresent(key);
        }

        public V get(K key, Callable<? extends V> valueLoader) throws ExecutionException {
            Preconditions.checkNotNull(valueLoader);
            return this.localCache.get(key, new LocalCache$LocalManualCache$1(this, valueLoader));
        }

        public ImmutableMap<K, V> getAllPresent(Iterable<?> keys) {
            return this.localCache.getAllPresent(keys);
        }

        public void put(K key, V value) {
            this.localCache.put(key, value);
        }

        public void putAll(Map<? extends K, ? extends V> m) {
            this.localCache.putAll(m);
        }

        public void invalidate(Object key) {
            Preconditions.checkNotNull(key);
            this.localCache.remove(key);
        }

        public void invalidateAll(Iterable<?> keys) {
            this.localCache.invalidateAll(keys);
        }

        public void invalidateAll() {
            this.localCache.clear();
        }

        public long size() {
            return this.localCache.longSize();
        }

        public ConcurrentMap<K, V> asMap() {
            return this.localCache;
        }

        public CacheStats stats() {
            SimpleStatsCounter aggregator = new SimpleStatsCounter();
            aggregator.incrementBy(this.localCache.globalStatsCounter);
            for (Segment<K, V> segment : this.localCache.segments) {
                aggregator.incrementBy(segment.statsCounter);
            }
            return aggregator.snapshot();
        }

        public void cleanUp() {
            this.localCache.cleanUp();
        }

        Object writeReplace() {
            return new LocalCache$ManualSerializationProxy(this.localCache);
        }
    }

    LocalCache(CacheBuilder<? super K, ? super V> builder, @Nullable CacheLoader<? super K, V> loader) {
        this.concurrencyLevel = Math.min(builder.getConcurrencyLevel(), 65536);
        this.keyStrength = builder.getKeyStrength();
        this.valueStrength = builder.getValueStrength();
        this.keyEquivalence = builder.getKeyEquivalence();
        this.valueEquivalence = builder.getValueEquivalence();
        this.maxWeight = builder.getMaximumWeight();
        this.weigher = builder.getWeigher();
        this.expireAfterAccessNanos = builder.getExpireAfterAccessNanos();
        this.expireAfterWriteNanos = builder.getExpireAfterWriteNanos();
        this.refreshNanos = builder.getRefreshNanos();
        this.removalListener = builder.getRemovalListener();
        r0.removalNotificationQueue = this.removalListener == CacheBuilder$NullListener.INSTANCE ? discardingQueue() : new ConcurrentLinkedQueue();
        r0.ticker = builder.getTicker(recordsTime());
        r0.entryFactory = LocalCache$EntryFactory.getFactory(r0.keyStrength, usesAccessEntries(), usesWriteEntries());
        r0.globalStatsCounter = (StatsCounter) builder.getStatsCounterSupplier().get();
        r0.defaultLoader = loader;
        int initialCapacity = Math.min(builder.getInitialCapacity(), 1073741824);
        if (evictsBySize() && !customWeigher()) {
            initialCapacity = Math.min(initialCapacity, (int) r0.maxWeight);
        }
        int segmentSize = 1;
        int segmentShift = 0;
        int segmentCount = 1;
        while (segmentCount < r0.concurrencyLevel && (!evictsBySize() || ((long) (segmentCount * 20)) <= r0.maxWeight)) {
            segmentShift++;
            segmentCount <<= 1;
        }
        r0.segmentShift = 32 - segmentShift;
        r0.segmentMask = segmentCount - 1;
        r0.segments = newSegmentArray(segmentCount);
        int segmentCapacity = initialCapacity / segmentCount;
        if (segmentCapacity * segmentCount < initialCapacity) {
            segmentCapacity++;
        }
        while (segmentSize < segmentCapacity) {
            segmentSize <<= 1;
        }
        int i = 0;
        int i2;
        if (evictsBySize()) {
            long maxSegmentWeight = (r0.maxWeight / ((long) segmentCount)) + 1;
            long remainder = r0.maxWeight % ((long) segmentCount);
            while (true) {
                i2 = i;
                if (i2 < r0.segments.length) {
                    if (((long) i2) == remainder) {
                        maxSegmentWeight--;
                    }
                    r0.segments[i2] = createSegment(segmentSize, maxSegmentWeight, (StatsCounter) builder.getStatsCounterSupplier().get());
                    i = i2 + 1;
                } else {
                    return;
                }
            }
        }
        while (true) {
            i2 = i;
            if (i2 < r0.segments.length) {
                r0.segments[i2] = createSegment(segmentSize, -1, (StatsCounter) builder.getStatsCounterSupplier().get());
                i = i2 + 1;
            } else {
                return;
            }
        }
    }

    boolean evictsBySize() {
        return this.maxWeight >= 0;
    }

    boolean customWeigher() {
        return this.weigher != CacheBuilder$OneWeigher.INSTANCE;
    }

    boolean expires() {
        if (!expiresAfterWrite()) {
            if (!expiresAfterAccess()) {
                return false;
            }
        }
        return true;
    }

    boolean expiresAfterWrite() {
        return this.expireAfterWriteNanos > 0;
    }

    boolean expiresAfterAccess() {
        return this.expireAfterAccessNanos > 0;
    }

    boolean refreshes() {
        return this.refreshNanos > 0;
    }

    boolean usesAccessQueue() {
        if (!expiresAfterAccess()) {
            if (!evictsBySize()) {
                return false;
            }
        }
        return true;
    }

    boolean usesWriteQueue() {
        return expiresAfterWrite();
    }

    boolean recordsWrite() {
        if (!expiresAfterWrite()) {
            if (!refreshes()) {
                return false;
            }
        }
        return true;
    }

    boolean recordsAccess() {
        return expiresAfterAccess();
    }

    boolean recordsTime() {
        if (!recordsWrite()) {
            if (!recordsAccess()) {
                return false;
            }
        }
        return true;
    }

    boolean usesWriteEntries() {
        if (!usesWriteQueue()) {
            if (!recordsWrite()) {
                return false;
            }
        }
        return true;
    }

    boolean usesAccessEntries() {
        if (!usesAccessQueue()) {
            if (!recordsAccess()) {
                return false;
            }
        }
        return true;
    }

    boolean usesKeyReferences() {
        return this.keyStrength != LocalCache$Strength.STRONG;
    }

    boolean usesValueReferences() {
        return this.valueStrength != LocalCache$Strength.STRONG;
    }

    static <K, V> ValueReference<K, V> unset() {
        return UNSET;
    }

    static <K, V> ReferenceEntry<K, V> nullEntry() {
        return LocalCache$NullEntry.INSTANCE;
    }

    static <E> Queue<E> discardingQueue() {
        return DISCARDING_QUEUE;
    }

    static int rehash(int h) {
        h += (h << 15) ^ -12931;
        h ^= h >>> 10;
        h += h << 3;
        h ^= h >>> 6;
        h += (h << 2) + (h << 14);
        return (h >>> 16) ^ h;
    }

    @VisibleForTesting
    ReferenceEntry<K, V> newEntry(K key, int hash, @Nullable ReferenceEntry<K, V> next) {
        Segment<K, V> segment = segmentFor(hash);
        segment.lock();
        try {
            ReferenceEntry<K, V> newEntry = segment.newEntry(key, hash, next);
            return newEntry;
        } finally {
            segment.unlock();
        }
    }

    @VisibleForTesting
    ReferenceEntry<K, V> copyEntry(ReferenceEntry<K, V> original, ReferenceEntry<K, V> newNext) {
        return segmentFor(original.getHash()).copyEntry(original, newNext);
    }

    @VisibleForTesting
    ValueReference<K, V> newValueReference(ReferenceEntry<K, V> entry, V value, int weight) {
        return this.valueStrength.referenceValue(segmentFor(entry.getHash()), entry, Preconditions.checkNotNull(value), weight);
    }

    int hash(@Nullable Object key) {
        return rehash(this.keyEquivalence.hash(key));
    }

    void reclaimValue(ValueReference<K, V> valueReference) {
        ReferenceEntry<K, V> entry = valueReference.getEntry();
        int hash = entry.getHash();
        segmentFor(hash).reclaimValue(entry.getKey(), hash, valueReference);
    }

    void reclaimKey(ReferenceEntry<K, V> entry) {
        int hash = entry.getHash();
        segmentFor(hash).reclaimKey(entry, hash);
    }

    @VisibleForTesting
    boolean isLive(ReferenceEntry<K, V> entry, long now) {
        return segmentFor(entry.getHash()).getLiveValue(entry, now) != null;
    }

    Segment<K, V> segmentFor(int hash) {
        return this.segments[(hash >>> this.segmentShift) & this.segmentMask];
    }

    Segment<K, V> createSegment(int initialCapacity, long maxSegmentWeight, StatsCounter statsCounter) {
        return new Segment(this, initialCapacity, maxSegmentWeight, statsCounter);
    }

    @Nullable
    V getLiveValue(ReferenceEntry<K, V> entry, long now) {
        if (entry.getKey() == null) {
            return null;
        }
        V value = entry.getValueReference().get();
        if (value == null || isExpired(entry, now)) {
            return null;
        }
        return value;
    }

    boolean isExpired(ReferenceEntry<K, V> entry, long now) {
        Preconditions.checkNotNull(entry);
        if (expiresAfterAccess() && now - entry.getAccessTime() >= this.expireAfterAccessNanos) {
            return true;
        }
        if (!expiresAfterWrite() || now - entry.getWriteTime() < this.expireAfterWriteNanos) {
            return false;
        }
        return true;
    }

    static <K, V> void connectAccessOrder(ReferenceEntry<K, V> previous, ReferenceEntry<K, V> next) {
        previous.setNextInAccessQueue(next);
        next.setPreviousInAccessQueue(previous);
    }

    static <K, V> void nullifyAccessOrder(ReferenceEntry<K, V> nulled) {
        ReferenceEntry<K, V> nullEntry = nullEntry();
        nulled.setNextInAccessQueue(nullEntry);
        nulled.setPreviousInAccessQueue(nullEntry);
    }

    static <K, V> void connectWriteOrder(ReferenceEntry<K, V> previous, ReferenceEntry<K, V> next) {
        previous.setNextInWriteQueue(next);
        next.setPreviousInWriteQueue(previous);
    }

    static <K, V> void nullifyWriteOrder(ReferenceEntry<K, V> nulled) {
        ReferenceEntry<K, V> nullEntry = nullEntry();
        nulled.setNextInWriteQueue(nullEntry);
        nulled.setPreviousInWriteQueue(nullEntry);
    }

    void processPendingNotifications() {
        while (true) {
            RemovalNotification<K, V> removalNotification = (RemovalNotification) this.removalNotificationQueue.poll();
            RemovalNotification<K, V> notification = removalNotification;
            if (removalNotification != null) {
                try {
                    this.removalListener.onRemoval(notification);
                } catch (Throwable e) {
                    logger.log(Level.WARNING, "Exception thrown by removal listener", e);
                }
            } else {
                return;
            }
        }
    }

    final Segment<K, V>[] newSegmentArray(int ssize) {
        return new Segment[ssize];
    }

    public void cleanUp() {
        for (Segment<?, ?> segment : this.segments) {
            segment.cleanUp();
        }
    }

    public boolean isEmpty() {
        Segment<K, V>[] segments = this.segments;
        long sum = 0;
        int i = 0;
        while (i < segments.length) {
            if (segments[i].count != 0) {
                return false;
            }
            i++;
            sum += (long) segments[i].modCount;
        }
        if (sum != 0) {
            long sum2 = sum;
            int i2 = 0;
            while (i2 < segments.length) {
                if (segments[i2].count != 0) {
                    return false;
                }
                i2++;
                sum2 -= (long) segments[i2].modCount;
            }
            if (sum2 != 0) {
                return false;
            }
        }
        return true;
    }

    long longSize() {
        Segment<K, V>[] segments = this.segments;
        long sum = 0;
        int i = 0;
        while (i < segments.length) {
            i++;
            sum += (long) Math.max(0, segments[i].count);
        }
        return sum;
    }

    public int size() {
        return Ints.saturatedCast(longSize());
    }

    @Nullable
    public V get(@Nullable Object key) {
        if (key == null) {
            return null;
        }
        int hash = hash(key);
        return segmentFor(hash).get(key, hash);
    }

    @Nullable
    public V getIfPresent(Object key) {
        int hash = hash(Preconditions.checkNotNull(key));
        V value = segmentFor(hash).get(key, hash);
        if (value == null) {
            this.globalStatsCounter.recordMisses(1);
        } else {
            this.globalStatsCounter.recordHits(1);
        }
        return value;
    }

    V get(K key, CacheLoader<? super K, V> loader) throws ExecutionException {
        int hash = hash(Preconditions.checkNotNull(key));
        return segmentFor(hash).get(key, hash, loader);
    }

    V getOrLoad(K key) throws ExecutionException {
        return get(key, this.defaultLoader);
    }

    ImmutableMap<K, V> getAllPresent(Iterable<?> keys) {
        int hits = 0;
        int misses = 0;
        Map result = Maps.newLinkedHashMap();
        for (K key : keys) {
            V value = get(key);
            if (value == null) {
                misses++;
            } else {
                result.put(key, value);
                hits++;
            }
        }
        this.globalStatsCounter.recordHits(hits);
        this.globalStatsCounter.recordMisses(misses);
        return ImmutableMap.copyOf(result);
    }

    ImmutableMap<K, V> getAll(Iterable<? extends K> keys) throws ExecutionException {
        int hits = 0;
        int misses = 0;
        Map result = Maps.newLinkedHashMap();
        Set<K> keysToLoad = Sets.newLinkedHashSet();
        for (K key : keys) {
            V value = get(key);
            if (!result.containsKey(key)) {
                result.put(key, value);
                if (value == null) {
                    misses++;
                    keysToLoad.add(key);
                } else {
                    hits++;
                }
            }
        }
        try {
            if (!keysToLoad.isEmpty()) {
                Map<K, V> newEntries = loadAll(keysToLoad, this.defaultLoader);
                for (K key2 : keysToLoad) {
                    V value2 = newEntries.get(key2);
                    if (value2 == null) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("loadAll failed to return a value for ");
                        stringBuilder.append(key2);
                        throw new InvalidCacheLoadException(stringBuilder.toString());
                    }
                    result.put(key2, value2);
                }
            }
        } catch (UnsupportedLoadingOperationException e) {
            for (K key22 : keysToLoad) {
                misses--;
                result.put(key22, get(key22, this.defaultLoader));
            }
        } catch (Throwable th) {
            this.globalStatsCounter.recordHits(hits);
            this.globalStatsCounter.recordMisses(misses);
        }
        ImmutableMap<K, V> copyOf = ImmutableMap.copyOf(result);
        this.globalStatsCounter.recordHits(hits);
        this.globalStatsCounter.recordMisses(misses);
        return copyOf;
    }

    @Nullable
    Map<K, V> loadAll(Set<? extends K> keys, CacheLoader<? super K, V> loader) throws ExecutionException {
        Preconditions.checkNotNull(loader);
        Preconditions.checkNotNull(keys);
        Stopwatch stopwatch = Stopwatch.createStarted();
        try {
            Map<K, V> result = loader.loadAll(keys);
            if (!true) {
                this.globalStatsCounter.recordLoadException(stopwatch.elapsed(TimeUnit.NANOSECONDS));
            }
            if (result == null) {
                this.globalStatsCounter.recordLoadException(stopwatch.elapsed(TimeUnit.NANOSECONDS));
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(loader);
                stringBuilder.append(" returned null map from loadAll");
                throw new InvalidCacheLoadException(stringBuilder.toString());
            }
            stopwatch.stop();
            boolean nullsPresent = false;
            for (Entry<K, V> entry : result.entrySet()) {
                K key = entry.getKey();
                V value = entry.getValue();
                if (key != null) {
                    if (value != null) {
                        put(key, value);
                    }
                }
                nullsPresent = true;
            }
            if (nullsPresent) {
                this.globalStatsCounter.recordLoadException(stopwatch.elapsed(TimeUnit.NANOSECONDS));
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append(loader);
                stringBuilder2.append(" returned null keys or values from loadAll");
                throw new InvalidCacheLoadException(stringBuilder2.toString());
            }
            this.globalStatsCounter.recordLoadSuccess(stopwatch.elapsed(TimeUnit.NANOSECONDS));
            return result;
        } catch (UnsupportedLoadingOperationException e) {
            throw e;
        } catch (InterruptedException e2) {
            Thread.currentThread().interrupt();
            throw new ExecutionException(e2);
        } catch (RuntimeException e3) {
            throw new UncheckedExecutionException(e3);
        } catch (Exception e4) {
            throw new ExecutionException(e4);
        } catch (Error e5) {
            throw new ExecutionError(e5);
        } catch (Throwable th) {
            if (!false) {
                this.globalStatsCounter.recordLoadException(stopwatch.elapsed(TimeUnit.NANOSECONDS));
            }
        }
    }

    ReferenceEntry<K, V> getEntry(@Nullable Object key) {
        if (key == null) {
            return null;
        }
        int hash = hash(key);
        return segmentFor(hash).getEntry(key, hash);
    }

    void refresh(K key) {
        int hash = hash(Preconditions.checkNotNull(key));
        segmentFor(hash).refresh(key, hash, this.defaultLoader, false);
    }

    public boolean containsKey(@Nullable Object key) {
        if (key == null) {
            return false;
        }
        int hash = hash(key);
        return segmentFor(hash).containsKey(key, hash);
    }

    public boolean containsValue(@Nullable Object value) {
        LocalCache localCache = this;
        Object obj = value;
        if (obj == null) {
            return false;
        }
        Segment<K, V>[] segments;
        long now = localCache.ticker.read();
        Segment<K, V>[] segments2 = localCache.segments;
        long last = -1;
        int i = 0;
        while (i < 3) {
            long now2;
            Segment[] arr$ = segments2;
            int len$ = arr$.length;
            long sum = 0;
            int i$ = 0;
            while (i$ < len$) {
                Segment[] arr$2;
                Segment<K, V> segment = arr$[i$];
                int unused = segment.count;
                AtomicReferenceArray<ReferenceEntry<K, V>> table = segment.table;
                int j = 0;
                while (true) {
                    segments = segments2;
                    arr$2 = arr$;
                    int j2 = j;
                    if (j2 >= table.length()) {
                        break;
                    }
                    ReferenceEntry<K, V> e = (ReferenceEntry) table.get(j2);
                    while (e != null) {
                        AtomicReferenceArray<ReferenceEntry<K, V>> table2 = table;
                        table = segment.getLiveValue(e, now);
                        if (table != null) {
                            now2 = now;
                            if (localCache.valueEquivalence.equivalent(obj, table)) {
                                return true;
                            }
                        } else {
                            now2 = now;
                        }
                        e = e.getNext();
                        table = table2;
                        now = now2;
                    }
                    now2 = now;
                    j = j2 + 1;
                    segments2 = segments;
                    arr$ = arr$2;
                }
                i$++;
                sum += (long) segment.modCount;
                segments2 = segments;
                arr$ = arr$2;
                now = now;
            }
            now2 = now;
            segments = segments2;
            if (sum == last) {
                break;
            }
            last = sum;
            i++;
            segments2 = segments;
            now = now2;
        }
        segments = segments2;
        return false;
    }

    public V put(K key, V value) {
        Preconditions.checkNotNull(key);
        Preconditions.checkNotNull(value);
        int hash = hash(key);
        return segmentFor(hash).put(key, hash, value, false);
    }

    public V putIfAbsent(K key, V value) {
        Preconditions.checkNotNull(key);
        Preconditions.checkNotNull(value);
        int hash = hash(key);
        return segmentFor(hash).put(key, hash, value, true);
    }

    public void putAll(Map<? extends K, ? extends V> m) {
        for (Entry<? extends K, ? extends V> e : m.entrySet()) {
            put(e.getKey(), e.getValue());
        }
    }

    public V remove(@Nullable Object key) {
        if (key == null) {
            return null;
        }
        int hash = hash(key);
        return segmentFor(hash).remove(key, hash);
    }

    public boolean remove(@Nullable Object key, @Nullable Object value) {
        if (key != null) {
            if (value != null) {
                int hash = hash(key);
                return segmentFor(hash).remove(key, hash, value);
            }
        }
        return false;
    }

    public boolean replace(K key, @Nullable V oldValue, V newValue) {
        Preconditions.checkNotNull(key);
        Preconditions.checkNotNull(newValue);
        if (oldValue == null) {
            return false;
        }
        int hash = hash(key);
        return segmentFor(hash).replace(key, hash, oldValue, newValue);
    }

    public V replace(K key, V value) {
        Preconditions.checkNotNull(key);
        Preconditions.checkNotNull(value);
        int hash = hash(key);
        return segmentFor(hash).replace(key, hash, value);
    }

    public void clear() {
        for (Segment<K, V> segment : this.segments) {
            segment.clear();
        }
    }

    void invalidateAll(Iterable<?> keys) {
        for (Object key : keys) {
            remove(key);
        }
    }

    public Set<K> keySet() {
        Set<K> ks = this.keySet;
        if (ks != null) {
            return ks;
        }
        Set<K> localCache$KeySet = new LocalCache$KeySet(this, this);
        this.keySet = localCache$KeySet;
        return localCache$KeySet;
    }

    public Collection<V> values() {
        Collection<V> vs = this.values;
        if (vs != null) {
            return vs;
        }
        Collection<V> localCache$Values = new LocalCache$Values(this, this);
        this.values = localCache$Values;
        return localCache$Values;
    }

    @GwtIncompatible("Not supported.")
    public Set<Entry<K, V>> entrySet() {
        Set<Entry<K, V>> es = this.entrySet;
        if (es != null) {
            return es;
        }
        Set<Entry<K, V>> localCache$EntrySet = new LocalCache$EntrySet(this, this);
        this.entrySet = localCache$EntrySet;
        return localCache$EntrySet;
    }

    private static <E> ArrayList<E> toArrayList(Collection<E> c) {
        ArrayList<E> result = new ArrayList(c.size());
        Iterators.addAll(result, c.iterator());
        return result;
    }
}
