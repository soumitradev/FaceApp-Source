package com.google.common.collect;

import com.google.common.annotations.VisibleForTesting;

@VisibleForTesting
class Ordering$IncomparableValueException extends ClassCastException {
    private static final long serialVersionUID = 0;
    final Object value;

    Ordering$IncomparableValueException(Object value) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Cannot compare value: ");
        stringBuilder.append(value);
        super(stringBuilder.toString());
        this.value = value;
    }
}
