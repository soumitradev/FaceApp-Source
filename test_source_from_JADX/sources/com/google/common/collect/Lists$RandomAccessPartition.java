package com.google.common.collect;

import java.util.List;
import java.util.RandomAccess;

class Lists$RandomAccessPartition<T> extends Lists$Partition<T> implements RandomAccess {
    Lists$RandomAccessPartition(List<T> list, int size) {
        super(list, size);
    }
}
