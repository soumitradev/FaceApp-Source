package com.google.common.collect;

import com.google.common.base.Objects;
import com.google.common.collect.MapDifference.ValueDifference;
import javax.annotation.Nullable;
import org.catrobat.catroid.common.Constants;

class Maps$ValueDifferenceImpl<V> implements ValueDifference<V> {
    private final V left;
    private final V right;

    static <V> ValueDifference<V> create(@Nullable V left, @Nullable V right) {
        return new Maps$ValueDifferenceImpl(left, right);
    }

    private Maps$ValueDifferenceImpl(@Nullable V left, @Nullable V right) {
        this.left = left;
        this.right = right;
    }

    public V leftValue() {
        return this.left;
    }

    public V rightValue() {
        return this.right;
    }

    public boolean equals(@Nullable Object object) {
        boolean z = false;
        if (!(object instanceof ValueDifference)) {
            return false;
        }
        ValueDifference<?> that = (ValueDifference) object;
        if (Objects.equal(this.left, that.leftValue()) && Objects.equal(this.right, that.rightValue())) {
            z = true;
        }
        return z;
    }

    public int hashCode() {
        return Objects.hashCode(new Object[]{this.left, this.right});
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Constants.OPENING_BRACE);
        stringBuilder.append(this.left);
        stringBuilder.append(", ");
        stringBuilder.append(this.right);
        stringBuilder.append(")");
        return stringBuilder.toString();
    }
}
