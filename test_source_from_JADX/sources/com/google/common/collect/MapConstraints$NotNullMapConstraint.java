package com.google.common.collect;

import com.google.common.base.Preconditions;

enum MapConstraints$NotNullMapConstraint implements MapConstraint<Object, Object> {
    INSTANCE;

    public void checkKeyValue(Object key, Object value) {
        Preconditions.checkNotNull(key);
        Preconditions.checkNotNull(value);
    }

    public String toString() {
        return "Not null";
    }
}
