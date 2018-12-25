package com.google.common.collect;

public abstract class MultimapBuilder$ListMultimapBuilder<K0, V0> extends MultimapBuilder<K0, V0> {
    public abstract <K extends K0, V extends V0> ListMultimap<K, V> build();

    MultimapBuilder$ListMultimapBuilder() {
        super(null);
    }

    public <K extends K0, V extends V0> ListMultimap<K, V> build(Multimap<? extends K, ? extends V> multimap) {
        return (ListMultimap) super.build(multimap);
    }
}
