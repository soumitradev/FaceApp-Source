package com.google.common.collect;

import com.google.common.base.Objects;
import com.google.common.collect.Multiset.Entry;
import javax.annotation.Nullable;

abstract class Multisets$AbstractEntry<E> implements Entry<E> {
    Multisets$AbstractEntry() {
    }

    public boolean equals(@Nullable Object object) {
        boolean z = false;
        if (!(object instanceof Entry)) {
            return false;
        }
        Entry<?> that = (Entry) object;
        if (getCount() == that.getCount() && Objects.equal(getElement(), that.getElement())) {
            z = true;
        }
        return z;
    }

    public int hashCode() {
        E e = getElement();
        return (e == null ? 0 : e.hashCode()) ^ getCount();
    }

    public String toString() {
        String text = String.valueOf(getElement());
        int n = getCount();
        if (n == 1) {
            return text;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(text);
        stringBuilder.append(" x ");
        stringBuilder.append(n);
        return stringBuilder.toString();
    }
}
