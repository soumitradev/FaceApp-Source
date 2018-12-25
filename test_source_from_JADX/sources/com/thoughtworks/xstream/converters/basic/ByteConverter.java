package com.thoughtworks.xstream.converters.basic;

import kotlin.text.Typography;

public class ByteConverter extends AbstractSingleValueConverter {
    public boolean canConvert(Class type) {
        if (!type.equals(Byte.TYPE)) {
            if (!type.equals(Byte.class)) {
                return false;
            }
        }
        return true;
    }

    public Object fromString(String str) {
        int value = Integer.decode(str).intValue();
        if (value >= -128) {
            if (value <= 255) {
                return new Byte((byte) value);
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("For input string: \"");
        stringBuilder.append(str);
        stringBuilder.append(Typography.quote);
        throw new NumberFormatException(stringBuilder.toString());
    }
}
