package com.google.common.collect;

public abstract class MultimapBuilder$SetMultimapBuilder<K0, V0> extends MultimapBuilder<K0, V0> {
    public abstract <K extends K0, V extends V0> SetMultimap<K, V> build();

    MultimapBuilder$SetMultimapBuilder() {
        super(null);
    }

    public <K extends K0, V extends V0> SetMultimap<K, V> build(Multimap<? extends K, ? extends V> multimap) {
        return (SetMultimap) super.build(multimap);
    }
}
