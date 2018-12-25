package com.google.common.collect;

import com.google.common.base.Function;
import java.util.Collection;

class Collections2$1 implements Function<Object, Object> {
    final /* synthetic */ Collection val$collection;

    Collections2$1(Collection collection) {
        this.val$collection = collection;
    }

    public Object apply(Object input) {
        return input == this.val$collection ? "(this Collection)" : input;
    }
}
