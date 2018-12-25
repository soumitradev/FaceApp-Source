package com.google.common.collect;

import java.util.Collection;
import java.util.EnumMap;
import java.util.Map;

class MultimapBuilder$4 extends MultimapBuilder$MultimapBuilderWithKeys<K0> {
    final /* synthetic */ Class val$keyClass;

    MultimapBuilder$4(Class cls) {
        this.val$keyClass = cls;
    }

    <K extends K0, V> Map<K, Collection<V>> createMap() {
        return new EnumMap(this.val$keyClass);
    }
}
