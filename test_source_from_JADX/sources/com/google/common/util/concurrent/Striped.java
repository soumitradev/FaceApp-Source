package com.google.common.util.concurrent;

import com.google.common.annotations.Beta;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.MapMaker;
import com.google.common.math.IntMath;
import com.google.common.primitives.Ints;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Beta
public abstract class Striped<L> {
    private static final int ALL_SET = -1;
    private static final int LARGE_LAZY_CUTOFF = 1024;
    private static final Supplier<ReadWriteLock> READ_WRITE_LOCK_SUPPLIER = new C10355();

    private static class PaddedLock extends ReentrantLock {
        long unused1;
        long unused2;
        long unused3;

        PaddedLock() {
            super(false);
        }
    }

    private static class PaddedSemaphore extends Semaphore {
        long unused1;
        long unused2;
        long unused3;

        PaddedSemaphore(int permits) {
            super(permits, false);
        }
    }

    /* renamed from: com.google.common.util.concurrent.Striped$1 */
    static class C10311 implements Supplier<Lock> {
        C10311() {
        }

        public Lock get() {
            return new PaddedLock();
        }
    }

    /* renamed from: com.google.common.util.concurrent.Striped$2 */
    static class C10322 implements Supplier<Lock> {
        C10322() {
        }

        public Lock get() {
            return new ReentrantLock(false);
        }
    }

    /* renamed from: com.google.common.util.concurrent.Striped$5 */
    static class C10355 implements Supplier<ReadWriteLock> {
        C10355() {
        }

        public ReadWriteLock get() {
            return new ReentrantReadWriteLock();
        }
    }

    private static abstract class PowerOfTwoStriped<L> extends Striped<L> {
        final int mask;

        PowerOfTwoStriped(int stripes) {
            super();
            Preconditions.checkArgument(stripes > 0, "Stripes must be positive");
            this.mask = stripes > Ints.MAX_POWER_OF_TWO ? -1 : Striped.ceilToPowerOfTwo(stripes) - 1;
        }

        final int indexFor(Object key) {
            return this.mask & Striped.smear(key.hashCode());
        }

        public final L get(Object key) {
            return getAt(indexFor(key));
        }
    }

    private static class CompactStriped<L> extends PowerOfTwoStriped<L> {
        private final Object[] array;

        private CompactStriped(int stripes, Supplier<L> supplier) {
            super(stripes);
            Preconditions.checkArgument(stripes <= Ints.MAX_POWER_OF_TWO, "Stripes must be <= 2^30)");
            this.array = new Object[(this.mask + 1)];
            for (int i = 0; i < this.array.length; i++) {
                this.array[i] = supplier.get();
            }
        }

        public L getAt(int index) {
            return this.array[index];
        }

        public int size() {
            return this.array.length;
        }
    }

    @VisibleForTesting
    static class LargeLazyStriped<L> extends PowerOfTwoStriped<L> {
        final ConcurrentMap<Integer, L> locks;
        final int size;
        final Supplier<L> supplier;

        LargeLazyStriped(int stripes, Supplier<L> supplier) {
            super(stripes);
            this.size = this.mask == -1 ? Integer.MAX_VALUE : this.mask + 1;
            this.supplier = supplier;
            this.locks = new MapMaker().weakValues().makeMap();
        }

        public L getAt(int index) {
            if (this.size != Integer.MAX_VALUE) {
                Preconditions.checkElementIndex(index, size());
            }
            L existing = this.locks.get(Integer.valueOf(index));
            if (existing != null) {
                return existing;
            }
            L created = this.supplier.get();
            return MoreObjects.firstNonNull(this.locks.putIfAbsent(Integer.valueOf(index), created), created);
        }

        public int size() {
            return this.size;
        }
    }

    @VisibleForTesting
    static class SmallLazyStriped<L> extends PowerOfTwoStriped<L> {
        final AtomicReferenceArray<ArrayReference<? extends L>> locks;
        final ReferenceQueue<L> queue = new ReferenceQueue();
        final int size;
        final Supplier<L> supplier;

        private static final class ArrayReference<L> extends WeakReference<L> {
            final int index;

            ArrayReference(L referent, int index, ReferenceQueue<L> queue) {
                super(referent, queue);
                this.index = index;
            }
        }

        SmallLazyStriped(int stripes, Supplier<L> supplier) {
            super(stripes);
            this.size = this.mask == -1 ? Integer.MAX_VALUE : this.mask + 1;
            this.locks = new AtomicReferenceArray(this.size);
            this.supplier = supplier;
        }

        public L getAt(int index) {
            if (this.size != Integer.MAX_VALUE) {
                Preconditions.checkElementIndex(index, size());
            }
            ArrayReference<? extends L> existingRef = (ArrayReference) this.locks.get(index);
            L existing = existingRef == null ? null : existingRef.get();
            if (existing != null) {
                return existing;
            }
            L created = this.supplier.get();
            ArrayReference<L> newRef = new ArrayReference(created, index, this.queue);
            while (!this.locks.compareAndSet(index, existingRef, newRef)) {
                existingRef = (ArrayReference) this.locks.get(index);
                existing = existingRef == null ? null : existingRef.get();
                if (existing != null) {
                    return existing;
                }
            }
            drainQueue();
            return created;
        }

        private void drainQueue() {
            while (true) {
                Reference<? extends L> poll = this.queue.poll();
                Reference<? extends L> ref = poll;
                if (poll != null) {
                    ArrayReference<? extends L> arrayRef = (ArrayReference) ref;
                    this.locks.compareAndSet(arrayRef.index, arrayRef, null);
                } else {
                    return;
                }
            }
        }

        public int size() {
            return this.size;
        }
    }

    public abstract L get(Object obj);

    public abstract L getAt(int i);

    abstract int indexFor(Object obj);

    public abstract int size();

    private Striped() {
    }

    public Iterable<L> bulkGet(Iterable<?> keys) {
        Object[] array = Iterables.toArray(keys, Object.class);
        if (array.length == 0) {
            return ImmutableList.of();
        }
        int i;
        int[] stripes = new int[array.length];
        for (i = 0; i < array.length; i++) {
            stripes[i] = indexFor(array[i]);
        }
        Arrays.sort(stripes);
        i = stripes[0];
        array[0] = getAt(i);
        for (int i2 = 1; i2 < array.length; i2++) {
            int currentStripe = stripes[i2];
            if (currentStripe == i) {
                array[i2] = array[i2 - 1];
            } else {
                array[i2] = getAt(currentStripe);
                i = currentStripe;
            }
        }
        return Collections.unmodifiableList(Arrays.asList(array));
    }

    public static Striped<Lock> lock(int stripes) {
        return new CompactStriped(stripes, new C10311());
    }

    public static Striped<Lock> lazyWeakLock(int stripes) {
        return lazy(stripes, new C10322());
    }

    private static <L> Striped<L> lazy(int stripes, Supplier<L> supplier) {
        return stripes < 1024 ? new SmallLazyStriped(stripes, supplier) : new LargeLazyStriped(stripes, supplier);
    }

    public static Striped<Semaphore> semaphore(int stripes, final int permits) {
        return new CompactStriped(stripes, new Supplier<Semaphore>() {
            public Semaphore get() {
                return new PaddedSemaphore(permits);
            }
        });
    }

    public static Striped<Semaphore> lazyWeakSemaphore(int stripes, final int permits) {
        return lazy(stripes, new Supplier<Semaphore>() {
            public Semaphore get() {
                return new Semaphore(permits, false);
            }
        });
    }

    public static Striped<ReadWriteLock> readWriteLock(int stripes) {
        return new CompactStriped(stripes, READ_WRITE_LOCK_SUPPLIER);
    }

    public static Striped<ReadWriteLock> lazyWeakReadWriteLock(int stripes) {
        return lazy(stripes, READ_WRITE_LOCK_SUPPLIER);
    }

    private static int ceilToPowerOfTwo(int x) {
        return 1 << IntMath.log2(x, RoundingMode.CEILING);
    }

    private static int smear(int hashCode) {
        hashCode ^= (hashCode >>> 20) ^ (hashCode >>> 12);
        return ((hashCode >>> 7) ^ hashCode) ^ (hashCode >>> 4);
    }
}
