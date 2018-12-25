package com.google.common.collect;

public abstract class MultimapBuilder$SortedSetMultimapBuilder<K0, V0> extends MultimapBuilder$SetMultimapBuilder<K0, V0> {
    public abstract <K extends K0, V extends V0> SortedSetMultimap<K, V> build();

    MultimapBuilder$SortedSetMultimapBuilder() {
    }

    public <K extends K0, V extends V0> SortedSetMultimap<K, V> build(Multimap<? extends K, ? extends V> multimap) {
        return (SortedSetMultimap) super.build((Multimap) multimap);
    }
}
