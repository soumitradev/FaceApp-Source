package com.google.common.collect;

import java.util.Map.Entry;
import javax.annotation.Nullable;

enum Maps$EntryFunction$2 extends Maps$EntryFunction {
    Maps$EntryFunction$2(String str, int i) {
        super(str, i, null);
    }

    @Nullable
    public Object apply(Entry<?, ?> entry) {
        return entry.getValue();
    }
}
