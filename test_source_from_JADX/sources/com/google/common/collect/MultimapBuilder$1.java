package com.google.common.collect;

import java.util.Collection;
import java.util.Map;

class MultimapBuilder$1 extends MultimapBuilder$MultimapBuilderWithKeys<Object> {
    final /* synthetic */ int val$expectedKeys;

    MultimapBuilder$1(int i) {
        this.val$expectedKeys = i;
    }

    <K, V> Map<K, Collection<V>> createMap() {
        return Maps.newHashMapWithExpectedSize(this.val$expectedKeys);
    }
}
