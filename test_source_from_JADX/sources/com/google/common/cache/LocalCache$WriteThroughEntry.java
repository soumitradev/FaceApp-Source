package com.google.common.cache;

import java.util.Map.Entry;
import javax.annotation.Nullable;

final class LocalCache$WriteThroughEntry implements Entry<K, V> {
    final K key;
    final /* synthetic */ LocalCache this$0;
    V value;

    LocalCache$WriteThroughEntry(LocalCache localCache, K key, V value) {
        this.this$0 = localCache;
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return this.key;
    }

    public V getValue() {
        return this.value;
    }

    public boolean equals(@Nullable Object object) {
        boolean z = false;
        if (!(object instanceof Entry)) {
            return false;
        }
        Entry<?, ?> that = (Entry) object;
        if (this.key.equals(that.getKey()) && this.value.equals(that.getValue())) {
            z = true;
        }
        return z;
    }

    public int hashCode() {
        return this.key.hashCode() ^ this.value.hashCode();
    }

    public V setValue(V v) {
        throw new UnsupportedOperationException();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getKey());
        stringBuilder.append("=");
        stringBuilder.append(getValue());
        return stringBuilder.toString();
    }
}
