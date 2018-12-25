package com.google.common.collect;

import com.google.common.base.Preconditions;
import java.util.AbstractList;

final class Lists$CharSequenceAsList extends AbstractList<Character> {
    private final CharSequence sequence;

    Lists$CharSequenceAsList(CharSequence sequence) {
        this.sequence = sequence;
    }

    public Character get(int index) {
        Preconditions.checkElementIndex(index, size());
        return Character.valueOf(this.sequence.charAt(index));
    }

    public int size() {
        return this.sequence.length();
    }
}
