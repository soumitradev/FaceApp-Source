package com.google.common.collect;

import com.google.common.base.Supplier;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

final class MultimapBuilder$ArrayListSupplier<V> implements Supplier<List<V>>, Serializable {
    private final int expectedValuesPerKey;

    MultimapBuilder$ArrayListSupplier(int expectedValuesPerKey) {
        this.expectedValuesPerKey = CollectPreconditions.checkNonnegative(expectedValuesPerKey, "expectedValuesPerKey");
    }

    public List<V> get() {
        return new ArrayList(this.expectedValuesPerKey);
    }
}
