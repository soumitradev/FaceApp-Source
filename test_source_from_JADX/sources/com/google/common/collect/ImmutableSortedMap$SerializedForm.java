package com.google.common.collect;

import com.google.common.collect.ImmutableSortedMap.Builder;
import java.util.Comparator;

class ImmutableSortedMap$SerializedForm extends ImmutableMap$SerializedForm {
    private static final long serialVersionUID = 0;
    private final Comparator<Object> comparator;

    ImmutableSortedMap$SerializedForm(ImmutableSortedMap<?, ?> sortedMap) {
        super(sortedMap);
        this.comparator = sortedMap.comparator();
    }

    Object readResolve() {
        return createMap(new Builder(this.comparator));
    }
}
