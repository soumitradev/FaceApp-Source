package com.google.common.collect;

import java.util.AbstractSet;
import java.util.Collection;
import java.util.Set;

public abstract class Sets$SetView<E> extends AbstractSet<E> {
    private Sets$SetView() {
    }

    public ImmutableSet<E> immutableCopy() {
        return ImmutableSet.copyOf((Collection) this);
    }

    public <S extends Set<E>> S copyInto(S set) {
        set.addAll(this);
        return set;
    }
}
