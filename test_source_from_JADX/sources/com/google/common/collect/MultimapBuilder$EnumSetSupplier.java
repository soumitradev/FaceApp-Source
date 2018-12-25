package com.google.common.collect;

import com.google.common.base.Preconditions;
import com.google.common.base.Supplier;
import java.io.Serializable;
import java.util.EnumSet;
import java.util.Set;

final class MultimapBuilder$EnumSetSupplier<V extends Enum<V>> implements Supplier<Set<V>>, Serializable {
    private final Class<V> clazz;

    MultimapBuilder$EnumSetSupplier(Class<V> clazz) {
        this.clazz = (Class) Preconditions.checkNotNull(clazz);
    }

    public Set<V> get() {
        return EnumSet.noneOf(this.clazz);
    }
}
