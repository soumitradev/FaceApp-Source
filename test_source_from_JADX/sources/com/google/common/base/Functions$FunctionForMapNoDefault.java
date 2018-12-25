package com.google.common.base;

import java.io.Serializable;
import java.util.Map;
import javax.annotation.Nullable;

class Functions$FunctionForMapNoDefault<K, V> implements Function<K, V>, Serializable {
    private static final long serialVersionUID = 0;
    final Map<K, V> map;

    Functions$FunctionForMapNoDefault(Map<K, V> map) {
        this.map = (Map) Preconditions.checkNotNull(map);
    }

    public V apply(@Nullable K key) {
        boolean z;
        V result = this.map.get(key);
        if (result == null) {
            if (!this.map.containsKey(key)) {
                z = false;
                Preconditions.checkArgument(z, "Key '%s' not present in map", key);
                return result;
            }
        }
        z = true;
        Preconditions.checkArgument(z, "Key '%s' not present in map", key);
        return result;
    }

    public boolean equals(@Nullable Object o) {
        if (!(o instanceof Functions$FunctionForMapNoDefault)) {
            return false;
        }
        return this.map.equals(((Functions$FunctionForMapNoDefault) o).map);
    }

    public int hashCode() {
        return this.map.hashCode();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Functions.forMap(");
        stringBuilder.append(this.map);
        stringBuilder.append(")");
        return stringBuilder.toString();
    }
}
