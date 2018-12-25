package com.thoughtworks.xstream.converters.basic;

import android.support.v4.internal.view.SupportMenu;
import kotlin.text.Typography;

public class ShortConverter extends AbstractSingleValueConverter {
    public boolean canConvert(Class type) {
        if (!type.equals(Short.TYPE)) {
            if (!type.equals(Short.class)) {
                return false;
            }
        }
        return true;
    }

    public Object fromString(String str) {
        int value = Integer.decode(str).intValue();
        if (value >= -32768) {
            if (value <= SupportMenu.USER_MASK) {
                return new Short((short) value);
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("For input string: \"");
        stringBuilder.append(str);
        stringBuilder.append(Typography.quote);
        throw new NumberFormatException(stringBuilder.toString());
    }
}
