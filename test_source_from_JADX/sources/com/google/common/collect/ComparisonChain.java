package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import java.util.Comparator;
import javax.annotation.CheckReturnValue;
import javax.annotation.Nullable;

@GwtCompatible
@CheckReturnValue
public abstract class ComparisonChain {
    private static final ComparisonChain ACTIVE = new ComparisonChain$1();
    private static final ComparisonChain GREATER = new ComparisonChain$InactiveComparisonChain(1);
    private static final ComparisonChain LESS = new ComparisonChain$InactiveComparisonChain(-1);

    public abstract ComparisonChain compare(double d, double d2);

    public abstract ComparisonChain compare(float f, float f2);

    public abstract ComparisonChain compare(int i, int i2);

    public abstract ComparisonChain compare(long j, long j2);

    public abstract ComparisonChain compare(Comparable<?> comparable, Comparable<?> comparable2);

    public abstract <T> ComparisonChain compare(@Nullable T t, @Nullable T t2, Comparator<T> comparator);

    public abstract ComparisonChain compareFalseFirst(boolean z, boolean z2);

    public abstract ComparisonChain compareTrueFirst(boolean z, boolean z2);

    public abstract int result();

    private ComparisonChain() {
    }

    public static ComparisonChain start() {
        return ACTIVE;
    }

    @Deprecated
    public final ComparisonChain compare(Boolean left, Boolean right) {
        return compareFalseFirst(left.booleanValue(), right.booleanValue());
    }
}
