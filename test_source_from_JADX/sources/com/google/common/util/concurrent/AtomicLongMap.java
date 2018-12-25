package com.google.common.util.concurrent;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@GwtCompatible
public final class AtomicLongMap<K> {
    private transient Map<K, Long> asMap;
    private final ConcurrentHashMap<K, AtomicLong> map;

    /* renamed from: com.google.common.util.concurrent.AtomicLongMap$1 */
    class C10191 implements Function<AtomicLong, Long> {
        C10191() {
        }

        public Long apply(AtomicLong atomic) {
            return Long.valueOf(atomic.get());
        }
    }

    private AtomicLongMap(ConcurrentHashMap<K, AtomicLong> map) {
        this.map = (ConcurrentHashMap) Preconditions.checkNotNull(map);
    }

    public static <K> AtomicLongMap<K> create() {
        return new AtomicLongMap(new ConcurrentHashMap());
    }

    public static <K> AtomicLongMap<K> create(Map<? extends K, ? extends Long> m) {
        AtomicLongMap<K> result = create();
        result.putAll(m);
        return result;
    }

    public long get(K key) {
        AtomicLong atomic = (AtomicLong) this.map.get(key);
        return atomic == null ? 0 : atomic.get();
    }

    public long incrementAndGet(K key) {
        return addAndGet(key, 1);
    }

    public long decrementAndGet(K key) {
        return addAndGet(key, -1);
    }

    public long addAndGet(K key, long delta) {
        AtomicLong atomic;
        do {
            atomic = (AtomicLong) this.map.get(key);
            if (atomic == null) {
                atomic = (AtomicLong) this.map.putIfAbsent(key, new AtomicLong(delta));
                if (atomic == null) {
                    return delta;
                }
            }
            while (true) {
                long oldValue = atomic.get();
                if (oldValue != 0) {
                    long newValue = oldValue + delta;
                    if (atomic.compareAndSet(oldValue, newValue)) {
                        return newValue;
                    }
                }
            }
        } while (!this.map.replace(key, atomic, new AtomicLong(delta)));
        return delta;
    }

    public long getAndIncrement(K key) {
        return getAndAdd(key, 1);
    }

    public long getAndDecrement(K key) {
        return getAndAdd(key, -1);
    }

    public long getAndAdd(K key, long delta) {
        AtomicLong atomic;
        do {
            atomic = (AtomicLong) this.map.get(key);
            if (atomic == null) {
                atomic = (AtomicLong) this.map.putIfAbsent(key, new AtomicLong(delta));
                if (atomic == null) {
                    return 0;
                }
            }
            while (true) {
                long oldValue = atomic.get();
                if (oldValue != 0) {
                    if (atomic.compareAndSet(oldValue, oldValue + delta)) {
                        return oldValue;
                    }
                }
            }
        } while (!this.map.replace(key, atomic, new AtomicLong(delta)));
        return 0;
    }

    public long put(K key, long newValue) {
        AtomicLong atomic;
        do {
            atomic = (AtomicLong) this.map.get(key);
            if (atomic == null) {
                atomic = (AtomicLong) this.map.putIfAbsent(key, new AtomicLong(newValue));
                if (atomic == null) {
                    return 0;
                }
            }
            while (true) {
                long oldValue = atomic.get();
                if (oldValue != 0) {
                    if (atomic.compareAndSet(oldValue, newValue)) {
                        return oldValue;
                    }
                }
            }
        } while (!this.map.replace(key, atomic, new AtomicLong(newValue)));
        return 0;
    }

    public void putAll(Map<? extends K, ? extends Long> m) {
        for (Entry<? extends K, ? extends Long> entry : m.entrySet()) {
            put(entry.getKey(), ((Long) entry.getValue()).longValue());
        }
    }

    public long remove(K key) {
        AtomicLong atomic = (AtomicLong) this.map.get(key);
        if (atomic == null) {
            return 0;
        }
        long oldValue;
        while (true) {
            oldValue = atomic.get();
            if (oldValue == 0) {
                break;
            } else if (atomic.compareAndSet(oldValue, 0)) {
                break;
            }
        }
        this.map.remove(key, atomic);
        return oldValue;
    }

    public void removeAllZeros() {
        Iterator<Entry<K, AtomicLong>> entryIterator = this.map.entrySet().iterator();
        while (entryIterator.hasNext()) {
            AtomicLong atomic = (AtomicLong) ((Entry) entryIterator.next()).getValue();
            if (atomic != null && atomic.get() == 0) {
                entryIterator.remove();
            }
        }
    }

    public long sum() {
        long sum = 0;
        for (AtomicLong value : this.map.values()) {
            sum += value.get();
        }
        return sum;
    }

    public Map<K, Long> asMap() {
        Map<K, Long> result = this.asMap;
        if (result != null) {
            return result;
        }
        Map<K, Long> createAsMap = createAsMap();
        this.asMap = createAsMap;
        return createAsMap;
    }

    private Map<K, Long> createAsMap() {
        return Collections.unmodifiableMap(Maps.transformValues(this.map, new C10191()));
    }

    public boolean containsKey(Object key) {
        return this.map.containsKey(key);
    }

    public int size() {
        return this.map.size();
    }

    public boolean isEmpty() {
        return this.map.isEmpty();
    }

    public void clear() {
        this.map.clear();
    }

    public String toString() {
        return this.map.toString();
    }

    long putIfAbsent(K key, long newValue) {
        AtomicLong atomic;
        do {
            atomic = (AtomicLong) this.map.get(key);
            if (atomic == null) {
                atomic = (AtomicLong) this.map.putIfAbsent(key, new AtomicLong(newValue));
                if (atomic == null) {
                    return 0;
                }
            }
            long oldValue = atomic.get();
            if (oldValue != 0) {
                return oldValue;
            }
        } while (!this.map.replace(key, atomic, new AtomicLong(newValue)));
        return 0;
    }

    boolean replace(K key, long expectedOldValue, long newValue) {
        boolean z = false;
        if (expectedOldValue == 0) {
            if (putIfAbsent(key, newValue) == 0) {
                z = true;
            }
            return z;
        }
        AtomicLong atomic = (AtomicLong) this.map.get(key);
        if (atomic != null) {
            z = atomic.compareAndSet(expectedOldValue, newValue);
        }
        return z;
    }

    boolean remove(K key, long value) {
        AtomicLong atomic = (AtomicLong) this.map.get(key);
        if (atomic == null) {
            return false;
        }
        long oldValue = atomic.get();
        if (oldValue != value) {
            return false;
        }
        if (oldValue != 0) {
            if (!atomic.compareAndSet(oldValue, 0)) {
                return false;
            }
        }
        this.map.remove(key, atomic);
        return true;
    }
}