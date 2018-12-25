package com.google.common.collect;

import com.google.common.base.Preconditions;
import javax.annotation.Nullable;

final class Lists$StringAsImmutableList extends ImmutableList<Character> {
    private final String string;

    Lists$StringAsImmutableList(String string) {
        this.string = string;
    }

    public int indexOf(@Nullable Object object) {
        return object instanceof Character ? this.string.indexOf(((Character) object).charValue()) : -1;
    }

    public int lastIndexOf(@Nullable Object object) {
        return object instanceof Character ? this.string.lastIndexOf(((Character) object).charValue()) : -1;
    }

    public ImmutableList<Character> subList(int fromIndex, int toIndex) {
        Preconditions.checkPositionIndexes(fromIndex, toIndex, size());
        return Lists.charactersOf(this.string.substring(fromIndex, toIndex));
    }

    boolean isPartialView() {
        return false;
    }

    public Character get(int index) {
        Preconditions.checkElementIndex(index, size());
        return Character.valueOf(this.string.charAt(index));
    }

    public int size() {
        return this.string.length();
    }
}
