package com.squareup.picasso;

import android.content.Context;
import android.graphics.Bitmap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

public class LruCache implements Cache {
    private int evictionCount;
    private int hitCount;
    final LinkedHashMap<String, Bitmap> map;
    private final int maxSize;
    private int missCount;
    private int putCount;
    private int size;

    public LruCache(Context context) {
        this(Utils.calculateMemoryCacheSize(context));
    }

    public LruCache(int maxSize) {
        if (maxSize <= 0) {
            throw new IllegalArgumentException("Max size must be positive.");
        }
        this.maxSize = maxSize;
        this.map = new LinkedHashMap(0, 0.75f, true);
    }

    public Bitmap get(String key) {
        Throwable th;
        if (key == null) {
            throw new NullPointerException("key == null");
        }
        synchronized (this) {
            Bitmap mapValue;
            try {
                mapValue = (Bitmap) this.map.get(key);
                if (mapValue != null) {
                    this.hitCount++;
                    return mapValue;
                }
                this.missCount++;
                return null;
            } catch (Throwable th2) {
                th = th2;
                throw th;
            }
        }
    }

    public void set(String key, Bitmap bitmap) {
        if (key != null) {
            if (bitmap != null) {
                synchronized (this) {
                    this.putCount++;
                    this.size += Utils.getBitmapBytes(bitmap);
                    Bitmap previous = (Bitmap) this.map.put(key, bitmap);
                    if (previous != null) {
                        this.size -= Utils.getBitmapBytes(previous);
                    }
                }
                trimToSize(this.maxSize);
                return;
            }
        }
        throw new NullPointerException("key == null || bitmap == null");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void trimToSize(int r6) {
        /*
        r5 = this;
        r0 = 0;
        r1 = r0;
    L_0x0002:
        monitor-enter(r5);
        r2 = r5.size;	 Catch:{ all -> 0x0076 }
        if (r2 < 0) goto L_0x0057;
    L_0x0007:
        r2 = r5.map;	 Catch:{ all -> 0x0076 }
        r2 = r2.isEmpty();	 Catch:{ all -> 0x0076 }
        if (r2 == 0) goto L_0x0014;
    L_0x000f:
        r2 = r5.size;	 Catch:{ all -> 0x0076 }
        if (r2 == 0) goto L_0x0014;
    L_0x0013:
        goto L_0x0057;
    L_0x0014:
        r2 = r5.size;	 Catch:{ all -> 0x0076 }
        if (r2 <= r6) goto L_0x0055;
    L_0x0018:
        r2 = r5.map;	 Catch:{ all -> 0x0076 }
        r2 = r2.isEmpty();	 Catch:{ all -> 0x0076 }
        if (r2 == 0) goto L_0x0021;
    L_0x0020:
        goto L_0x0055;
    L_0x0021:
        r2 = r5.map;	 Catch:{ all -> 0x0076 }
        r2 = r2.entrySet();	 Catch:{ all -> 0x0076 }
        r2 = r2.iterator();	 Catch:{ all -> 0x0076 }
        r2 = r2.next();	 Catch:{ all -> 0x0076 }
        r2 = (java.util.Map.Entry) r2;	 Catch:{ all -> 0x0076 }
        r3 = r2.getKey();	 Catch:{ all -> 0x0076 }
        r3 = (java.lang.String) r3;	 Catch:{ all -> 0x0076 }
        r0 = r3;
        r3 = r2.getValue();	 Catch:{ all -> 0x0076 }
        r3 = (android.graphics.Bitmap) r3;	 Catch:{ all -> 0x0076 }
        r1 = r3;
        r3 = r5.map;	 Catch:{ all -> 0x0076 }
        r3.remove(r0);	 Catch:{ all -> 0x0076 }
        r3 = r5.size;	 Catch:{ all -> 0x0076 }
        r4 = com.squareup.picasso.Utils.getBitmapBytes(r1);	 Catch:{ all -> 0x0076 }
        r3 = r3 - r4;
        r5.size = r3;	 Catch:{ all -> 0x0076 }
        r3 = r5.evictionCount;	 Catch:{ all -> 0x0076 }
        r3 = r3 + 1;
        r5.evictionCount = r3;	 Catch:{ all -> 0x0076 }
        monitor-exit(r5);	 Catch:{ all -> 0x0076 }
        goto L_0x0002;
    L_0x0055:
        monitor-exit(r5);	 Catch:{ all -> 0x0076 }
        return;
    L_0x0057:
        r2 = new java.lang.IllegalStateException;	 Catch:{ all -> 0x0076 }
        r3 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0076 }
        r3.<init>();	 Catch:{ all -> 0x0076 }
        r4 = r5.getClass();	 Catch:{ all -> 0x0076 }
        r4 = r4.getName();	 Catch:{ all -> 0x0076 }
        r3.append(r4);	 Catch:{ all -> 0x0076 }
        r4 = ".sizeOf() is reporting inconsistent results!";
        r3.append(r4);	 Catch:{ all -> 0x0076 }
        r3 = r3.toString();	 Catch:{ all -> 0x0076 }
        r2.<init>(r3);	 Catch:{ all -> 0x0076 }
        throw r2;	 Catch:{ all -> 0x0076 }
    L_0x0076:
        r2 = move-exception;
        monitor-exit(r5);	 Catch:{ all -> 0x0076 }
        throw r2;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.squareup.picasso.LruCache.trimToSize(int):void");
    }

    public final void evictAll() {
        trimToSize(-1);
    }

    public final synchronized int size() {
        return this.size;
    }

    public final synchronized int maxSize() {
        return this.maxSize;
    }

    public final synchronized void clear() {
        evictAll();
    }

    public final synchronized void clearKeyUri(String uri) {
        boolean sizeChanged = false;
        int uriLength = uri.length();
        Iterator<Entry<String, Bitmap>> i = this.map.entrySet().iterator();
        while (i.hasNext()) {
            Entry<String, Bitmap> entry = (Entry) i.next();
            String key = (String) entry.getKey();
            Bitmap value = (Bitmap) entry.getValue();
            int newlineIndex = key.indexOf(10);
            if (newlineIndex == uriLength && key.substring(0, newlineIndex).equals(uri)) {
                i.remove();
                this.size -= Utils.getBitmapBytes(value);
                sizeChanged = true;
            }
        }
        if (sizeChanged) {
            trimToSize(this.maxSize);
        }
    }

    public final synchronized int hitCount() {
        return this.hitCount;
    }

    public final synchronized int missCount() {
        return this.missCount;
    }

    public final synchronized int putCount() {
        return this.putCount;
    }

    public final synchronized int evictionCount() {
        return this.evictionCount;
    }
}
