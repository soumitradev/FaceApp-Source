package com.google.common.collect;

import com.google.common.primitives.Booleans;
import com.google.common.primitives.Ints;
import com.google.common.primitives.Longs;
import java.util.Comparator;
import javax.annotation.Nullable;

class ComparisonChain$1 extends ComparisonChain {
    ComparisonChain$1() {
        super(null);
    }

    public ComparisonChain compare(Comparable left, Comparable right) {
        return classify(left.compareTo(right));
    }

    public <T> ComparisonChain compare(@Nullable T left, @Nullable T right, Comparator<T> comparator) {
        return classify(comparator.compare(left, right));
    }

    public ComparisonChain compare(int left, int right) {
        return classify(Ints.compare(left, right));
    }

    public ComparisonChain compare(long left, long right) {
        return classify(Longs.compare(left, right));
    }

    public ComparisonChain compare(float left, float right) {
        return classify(Float.compare(left, right));
    }

    public ComparisonChain compare(double left, double right) {
        return classify(Double.compare(left, right));
    }

    public ComparisonChain compareTrueFirst(boolean left, boolean right) {
        return classify(Booleans.compare(right, left));
    }

    public ComparisonChain compareFalseFirst(boolean left, boolean right) {
        return classify(Booleans.compare(left, right));
    }

    ComparisonChain classify(int result) {
        if (result < 0) {
            return ComparisonChain.access$100();
        }
        return result > 0 ? ComparisonChain.access$200() : ComparisonChain.access$300();
    }

    public int result() {
        return 0;
    }
}
