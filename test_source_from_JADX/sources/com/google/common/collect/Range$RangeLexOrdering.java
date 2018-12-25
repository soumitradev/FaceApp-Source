package com.google.common.collect;

import java.io.Serializable;

class Range$RangeLexOrdering extends Ordering<Range<?>> implements Serializable {
    private static final long serialVersionUID = 0;

    private Range$RangeLexOrdering() {
    }

    public int compare(Range<?> left, Range<?> right) {
        return ComparisonChain.start().compare(left.lowerBound, right.lowerBound).compare(left.upperBound, right.upperBound).result();
    }
}
