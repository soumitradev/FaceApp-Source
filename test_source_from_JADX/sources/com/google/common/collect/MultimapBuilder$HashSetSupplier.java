package com.google.common.collect;

import com.google.common.base.Supplier;
import java.io.Serializable;
import java.util.Set;

final class MultimapBuilder$HashSetSupplier<V> implements Supplier<Set<V>>, Serializable {
    private final int expectedValuesPerKey;

    MultimapBuilder$HashSetSupplier(int expectedValuesPerKey) {
        this.expectedValuesPerKey = CollectPreconditions.checkNonnegative(expectedValuesPerKey, "expectedValuesPerKey");
    }

    public Set<V> get() {
        return Sets.newHashSetWithExpectedSize(this.expectedValuesPerKey);
    }
}
