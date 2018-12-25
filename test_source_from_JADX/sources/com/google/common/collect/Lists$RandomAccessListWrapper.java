package com.google.common.collect;

import java.util.List;
import java.util.RandomAccess;

class Lists$RandomAccessListWrapper<E> extends Lists$AbstractListWrapper<E> implements RandomAccess {
    Lists$RandomAccessListWrapper(List<E> backingList) {
        super(backingList);
    }
}
