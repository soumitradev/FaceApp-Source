package com.google.common.collect;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.annotation.Nullable;

final class Sets$CartesianSet<E> extends ForwardingCollection<List<E>> implements Set<List<E>> {
    private final transient ImmutableList<ImmutableSet<E>> axes;
    private final transient CartesianList<E> delegate;

    static <E> Set<List<E>> create(List<? extends Set<? extends E>> sets) {
        ImmutableList$Builder<ImmutableSet<E>> axesBuilder = new ImmutableList$Builder(sets.size());
        for (Collection set : sets) {
            Object copy = ImmutableSet.copyOf(set);
            if (copy.isEmpty()) {
                return ImmutableSet.of();
            }
            axesBuilder.add(copy);
        }
        final ImmutableList<ImmutableSet<E>> axes = axesBuilder.build();
        return new Sets$CartesianSet(axes, new CartesianList(new ImmutableList<List<E>>() {
            public int size() {
                return axes.size();
            }

            public List<E> get(int index) {
                return ((ImmutableSet) axes.get(index)).asList();
            }

            boolean isPartialView() {
                return true;
            }
        }));
    }

    private Sets$CartesianSet(ImmutableList<ImmutableSet<E>> axes, CartesianList<E> delegate) {
        this.axes = axes;
        this.delegate = delegate;
    }

    protected Collection<List<E>> delegate() {
        return this.delegate;
    }

    public boolean equals(@Nullable Object object) {
        if (!(object instanceof Sets$CartesianSet)) {
            return super.equals(object);
        }
        return this.axes.equals(((Sets$CartesianSet) object).axes);
    }

    public int hashCode() {
        int i;
        int adjust = size() - 1;
        for (i = 0; i < this.axes.size(); i++) {
            adjust = ((adjust * 31) ^ -1) ^ -1;
        }
        i = 1;
        Iterator i$ = this.axes.iterator();
        while (i$.hasNext()) {
            Set<E> axis = (Set) i$.next();
            i = (((i * 31) + ((size() / axis.size()) * axis.hashCode())) ^ -1) ^ -1;
        }
        return ((i + adjust) ^ -1) ^ -1;
    }
}
