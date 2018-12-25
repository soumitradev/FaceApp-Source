package com.google.common.collect;

import java.util.Collection;
import java.util.Set;
import javax.annotation.Nullable;

final class TreeRangeSet$AsRanges extends ForwardingCollection<Range<C>> implements Set<Range<C>> {
    final Collection<Range<C>> delegate;
    final /* synthetic */ TreeRangeSet this$0;

    TreeRangeSet$AsRanges(TreeRangeSet treeRangeSet, Collection<Range<C>> delegate) {
        this.this$0 = treeRangeSet;
        this.delegate = delegate;
    }

    protected Collection<Range<C>> delegate() {
        return this.delegate;
    }

    public int hashCode() {
        return Sets.hashCodeImpl(this);
    }

    public boolean equals(@Nullable Object o) {
        return Sets.equalsImpl(this, o);
    }
}
