package com.thoughtworks.xstream.converters.basic;

import kotlin.text.Typography;

public class IntConverter extends AbstractSingleValueConverter {
    public boolean canConvert(Class type) {
        if (!type.equals(Integer.TYPE)) {
            if (!type.equals(Integer.class)) {
                return false;
            }
        }
        return true;
    }

    public Object fromString(String str) {
        long value = Long.decode(str).longValue();
        if (value >= -2147483648L) {
            if (value <= 4294967295L) {
                return new Integer((int) value);
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("For input string: \"");
        stringBuilder.append(str);
        stringBuilder.append(Typography.quote);
        throw new NumberFormatException(stringBuilder.toString());
    }
}
