package com.google.common.collect;

import com.google.common.base.Function;
import com.google.common.base.Throwables;
import java.io.Serializable;
import java.util.concurrent.ExecutionException;

final class MapMaker$ComputingMapAdapter<K, V> extends ComputingConcurrentHashMap<K, V> implements Serializable {
    private static final long serialVersionUID = 0;

    MapMaker$ComputingMapAdapter(MapMaker mapMaker, Function<? super K, ? extends V> computingFunction) {
        super(mapMaker, computingFunction);
    }

    public V get(Object key) {
        try {
            V value = getOrCompute(key);
            if (value != null) {
                return value;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(this.computingFunction);
            stringBuilder.append(" returned null for key ");
            stringBuilder.append(key);
            stringBuilder.append(".");
            throw new NullPointerException(stringBuilder.toString());
        } catch (ExecutionException e) {
            Throwable cause = e.getCause();
            Throwables.propagateIfInstanceOf(cause, ComputationException.class);
            throw new ComputationException(cause);
        }
    }
}
