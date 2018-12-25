package com.google.common.collect;

import java.util.List;
import java.util.RandomAccess;

class Lists$RandomAccessReverseList<T> extends Lists$ReverseList<T> implements RandomAccess {
    Lists$RandomAccessReverseList(List<T> forwardList) {
        super(forwardList);
    }
}
