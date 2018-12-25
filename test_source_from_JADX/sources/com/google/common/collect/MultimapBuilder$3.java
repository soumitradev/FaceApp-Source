package com.google.common.collect;

import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

class MultimapBuilder$3 extends MultimapBuilder$MultimapBuilderWithKeys<K0> {
    final /* synthetic */ Comparator val$comparator;

    MultimapBuilder$3(Comparator comparator) {
        this.val$comparator = comparator;
    }

    <K extends K0, V> Map<K, Collection<V>> createMap() {
        return new TreeMap(this.val$comparator);
    }
}
