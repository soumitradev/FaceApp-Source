package com.koushikdutta.async.util;

import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

public class LruCache<K, V> {
    private int createCount;
    private int evictionCount;
    private int hitCount;
    private final LinkedHashMap<K, V> map;
    private long maxSize;
    private int missCount;
    private int putCount;
    private long size;

    public LruCache(long maxSize) {
        if (maxSize <= 0) {
            throw new IllegalArgumentException("maxSize <= 0");
        }
        this.maxSize = maxSize;
        this.map = new LinkedHashMap(0, 0.75f, true);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final V get(K r9) {
        /*
        r8 = this;
        if (r9 != 0) goto L_0x000a;
    L_0x0002:
        r0 = new java.lang.NullPointerException;
        r1 = "key == null";
        r0.<init>(r1);
        throw r0;
    L_0x000a:
        monitor-enter(r8);
        r0 = r8.map;	 Catch:{ all -> 0x005c }
        r0 = r0.get(r9);	 Catch:{ all -> 0x005c }
        if (r0 == 0) goto L_0x001b;
    L_0x0013:
        r1 = r8.hitCount;	 Catch:{ all -> 0x005c }
        r1 = r1 + 1;
        r8.hitCount = r1;	 Catch:{ all -> 0x005c }
        monitor-exit(r8);	 Catch:{ all -> 0x005c }
        return r0;
    L_0x001b:
        r1 = r8.missCount;	 Catch:{ all -> 0x005c }
        r1 = r1 + 1;
        r8.missCount = r1;	 Catch:{ all -> 0x005c }
        monitor-exit(r8);	 Catch:{ all -> 0x005c }
        r1 = r8.create(r9);
        if (r1 != 0) goto L_0x002a;
    L_0x0028:
        r2 = 0;
        return r2;
    L_0x002a:
        monitor-enter(r8);
        r2 = r8.createCount;	 Catch:{ all -> 0x0059 }
        r2 = r2 + 1;
        r8.createCount = r2;	 Catch:{ all -> 0x0059 }
        r2 = r8.map;	 Catch:{ all -> 0x0059 }
        r2 = r2.put(r9, r1);	 Catch:{ all -> 0x0059 }
        r0 = r2;
        if (r0 == 0) goto L_0x0040;
    L_0x003a:
        r2 = r8.map;	 Catch:{ all -> 0x0059 }
        r2.put(r9, r0);	 Catch:{ all -> 0x0059 }
        goto L_0x004b;
    L_0x0040:
        r2 = r8.size;	 Catch:{ all -> 0x0059 }
        r4 = r8.safeSizeOf(r9, r1);	 Catch:{ all -> 0x0059 }
        r6 = 0;
        r6 = r2 + r4;
        r8.size = r6;	 Catch:{ all -> 0x0059 }
    L_0x004b:
        monitor-exit(r8);	 Catch:{ all -> 0x0059 }
        if (r0 == 0) goto L_0x0053;
    L_0x004e:
        r2 = 0;
        r8.entryRemoved(r2, r9, r1, r0);
        return r0;
    L_0x0053:
        r2 = r8.maxSize;
        r8.trimToSize(r2);
        return r1;
    L_0x0059:
        r2 = move-exception;
        monitor-exit(r8);	 Catch:{ all -> 0x0059 }
        throw r2;
    L_0x005c:
        r0 = move-exception;
        monitor-exit(r8);	 Catch:{ all -> 0x005c }
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.koushikdutta.async.util.LruCache.get(java.lang.Object):V");
    }

    public final V put(K key, V value) {
        if (key != null) {
            if (value != null) {
                V previous;
                synchronized (this) {
                    this.putCount++;
                    this.size += safeSizeOf(key, value);
                    previous = this.map.put(key, value);
                    if (previous != null) {
                        this.size -= safeSizeOf(key, previous);
                    }
                }
                if (previous != null) {
                    entryRemoved(false, key, previous, value);
                }
                trimToSize(this.maxSize);
                return previous;
            }
        }
        throw new NullPointerException("key == null || value == null");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void trimToSize(long r10) {
        /*
        r9 = this;
    L_0x0000:
        monitor-enter(r9);
        r0 = r9.size;	 Catch:{ all -> 0x007c }
        r2 = 0;
        r4 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1));
        if (r4 < 0) goto L_0x005d;
    L_0x0009:
        r0 = r9.map;	 Catch:{ all -> 0x007c }
        r0 = r0.isEmpty();	 Catch:{ all -> 0x007c }
        if (r0 == 0) goto L_0x0018;
    L_0x0011:
        r0 = r9.size;	 Catch:{ all -> 0x007c }
        r4 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1));
        if (r4 == 0) goto L_0x0018;
    L_0x0017:
        goto L_0x005d;
    L_0x0018:
        r0 = r9.size;	 Catch:{ all -> 0x007c }
        r2 = (r0 > r10 ? 1 : (r0 == r10 ? 0 : -1));
        if (r2 <= 0) goto L_0x005b;
    L_0x001e:
        r0 = r9.map;	 Catch:{ all -> 0x007c }
        r0 = r0.isEmpty();	 Catch:{ all -> 0x007c }
        if (r0 == 0) goto L_0x0027;
    L_0x0026:
        goto L_0x005b;
    L_0x0027:
        r0 = r9.map;	 Catch:{ all -> 0x007c }
        r0 = r0.entrySet();	 Catch:{ all -> 0x007c }
        r0 = r0.iterator();	 Catch:{ all -> 0x007c }
        r0 = r0.next();	 Catch:{ all -> 0x007c }
        r0 = (java.util.Map.Entry) r0;	 Catch:{ all -> 0x007c }
        r1 = r0.getKey();	 Catch:{ all -> 0x007c }
        r2 = r0.getValue();	 Catch:{ all -> 0x007c }
        r3 = r9.map;	 Catch:{ all -> 0x007c }
        r3.remove(r1);	 Catch:{ all -> 0x007c }
        r3 = r9.size;	 Catch:{ all -> 0x007c }
        r5 = r9.safeSizeOf(r1, r2);	 Catch:{ all -> 0x007c }
        r7 = 0;
        r7 = r3 - r5;
        r9.size = r7;	 Catch:{ all -> 0x007c }
        r3 = r9.evictionCount;	 Catch:{ all -> 0x007c }
        r4 = 1;
        r3 = r3 + r4;
        r9.evictionCount = r3;	 Catch:{ all -> 0x007c }
        monitor-exit(r9);	 Catch:{ all -> 0x007c }
        r0 = 0;
        r9.entryRemoved(r4, r1, r2, r0);
        goto L_0x0000;
    L_0x005b:
        monitor-exit(r9);	 Catch:{ all -> 0x007c }
        return;
    L_0x005d:
        r0 = new java.lang.IllegalStateException;	 Catch:{ all -> 0x007c }
        r1 = new java.lang.StringBuilder;	 Catch:{ all -> 0x007c }
        r1.<init>();	 Catch:{ all -> 0x007c }
        r2 = r9.getClass();	 Catch:{ all -> 0x007c }
        r2 = r2.getName();	 Catch:{ all -> 0x007c }
        r1.append(r2);	 Catch:{ all -> 0x007c }
        r2 = ".sizeOf() is reporting inconsistent results!";
        r1.append(r2);	 Catch:{ all -> 0x007c }
        r1 = r1.toString();	 Catch:{ all -> 0x007c }
        r0.<init>(r1);	 Catch:{ all -> 0x007c }
        throw r0;	 Catch:{ all -> 0x007c }
    L_0x007c:
        r0 = move-exception;
        monitor-exit(r9);	 Catch:{ all -> 0x007c }
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.koushikdutta.async.util.LruCache.trimToSize(long):void");
    }

    public final V remove(K key) {
        if (key == null) {
            throw new NullPointerException("key == null");
        }
        V previous;
        synchronized (this) {
            previous = this.map.remove(key);
            if (previous != null) {
                this.size -= safeSizeOf(key, previous);
            }
        }
        if (previous != null) {
            entryRemoved(false, key, previous, null);
        }
        return previous;
    }

    protected void entryRemoved(boolean evicted, K k, V v, V v2) {
    }

    protected V create(K k) {
        return null;
    }

    private long safeSizeOf(K key, V value) {
        long result = sizeOf(key, value);
        if (result >= 0) {
            return result;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Negative size: ");
        stringBuilder.append(key);
        stringBuilder.append("=");
        stringBuilder.append(value);
        throw new IllegalStateException(stringBuilder.toString());
    }

    protected long sizeOf(K k, V v) {
        return 1;
    }

    public final void evictAll() {
        trimToSize(-1);
    }

    public final synchronized long size() {
        return this.size;
    }

    public void setMaxSize(long maxSize) {
        this.maxSize = maxSize;
    }

    public final synchronized long maxSize() {
        return this.maxSize;
    }

    public final synchronized int hitCount() {
        return this.hitCount;
    }

    public final synchronized int missCount() {
        return this.missCount;
    }

    public final synchronized int createCount() {
        return this.createCount;
    }

    public final synchronized int putCount() {
        return this.putCount;
    }

    public final synchronized int evictionCount() {
        return this.evictionCount;
    }

    public final synchronized Map<K, V> snapshot() {
        return new LinkedHashMap(this.map);
    }

    public final synchronized String toString() {
        int hitPercent;
        int accesses = this.hitCount + this.missCount;
        hitPercent = accesses != 0 ? (this.hitCount * 100) / accesses : 0;
        return String.format(Locale.ENGLISH, "LruCache[maxSize=%d,hits=%d,misses=%d,hitRate=%d%%]", new Object[]{Long.valueOf(this.maxSize), Integer.valueOf(this.hitCount), Integer.valueOf(this.missCount), Integer.valueOf(hitPercent)});
    }
}
