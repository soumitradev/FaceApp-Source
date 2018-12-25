package com.google.common.base;

import java.io.Serializable;
import java.util.Map;
import javax.annotation.Nullable;

class Functions$ForMapWithDefault<K, V> implements Function<K, V>, Serializable {
    private static final long serialVersionUID = 0;
    final V defaultValue;
    final Map<K, ? extends V> map;

    Functions$ForMapWithDefault(Map<K, ? extends V> map, @Nullable V defaultValue) {
        this.map = (Map) Preconditions.checkNotNull(map);
        this.defaultValue = defaultValue;
    }

    public V apply(@Nullable K key) {
        V result = this.map.get(key);
        if (result == null) {
            if (!this.map.containsKey(key)) {
                return this.defaultValue;
            }
        }
        return result;
    }

    public boolean equals(@Nullable Object o) {
        boolean z = false;
        if (!(o instanceof Functions$ForMapWithDefault)) {
            return false;
        }
        Functions$ForMapWithDefault<?, ?> that = (Functions$ForMapWithDefault) o;
        if (this.map.equals(that.map) && Objects.equal(this.defaultValue, that.defaultValue)) {
            z = true;
        }
        return z;
    }

    public int hashCode() {
        return Objects.hashCode(new Object[]{this.map, this.defaultValue});
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Functions.forMap(");
        stringBuilder.append(this.map);
        stringBuilder.append(", defaultValue=");
        stringBuilder.append(this.defaultValue);
        stringBuilder.append(")");
        return stringBuilder.toString();
    }
}
