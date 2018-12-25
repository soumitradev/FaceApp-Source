package com.google.common.collect;

import com.google.common.base.Supplier;
import java.util.LinkedList;
import java.util.List;

enum MultimapBuilder$LinkedListSupplier implements Supplier<List<Object>> {
    INSTANCE;

    public static <V> Supplier<List<V>> instance() {
        return INSTANCE;
    }

    public List<Object> get() {
        return new LinkedList();
    }
}
