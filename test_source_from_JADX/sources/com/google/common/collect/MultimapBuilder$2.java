package com.google.common.collect;

import java.util.Collection;
import java.util.Map;

class MultimapBuilder$2 extends MultimapBuilder$MultimapBuilderWithKeys<Object> {
    final /* synthetic */ int val$expectedKeys;

    MultimapBuilder$2(int i) {
        this.val$expectedKeys = i;
    }

    <K, V> Map<K, Collection<V>> createMap() {
        return Maps.newLinkedHashMapWithExpectedSize(this.val$expectedKeys);
    }
}
