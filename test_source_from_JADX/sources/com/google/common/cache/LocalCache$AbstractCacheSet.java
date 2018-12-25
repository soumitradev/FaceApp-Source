package com.google.common.cache;

import com.google.j2objc.annotations.Weak;
import java.util.AbstractSet;
import java.util.concurrent.ConcurrentMap;

abstract class LocalCache$AbstractCacheSet<T> extends AbstractSet<T> {
    @Weak
    final ConcurrentMap<?, ?> map;
    final /* synthetic */ LocalCache this$0;

    LocalCache$AbstractCacheSet(LocalCache localCache, ConcurrentMap<?, ?> map) {
        this.this$0 = localCache;
        this.map = map;
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

    public Object[] toArray() {
        return LocalCache.access$200(this).toArray();
    }

    public <E> E[] toArray(E[] a) {
        return LocalCache.access$200(this).toArray(a);
    }
}
