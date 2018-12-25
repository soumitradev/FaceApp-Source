package com.thoughtworks.xstream.converters.basic;

public class FloatConverter extends AbstractSingleValueConverter {
    public boolean canConvert(Class type) {
        if (!type.equals(Float.TYPE)) {
            if (!type.equals(Float.class)) {
                return false;
            }
        }
        return true;
    }

    public Object fromString(String str) {
        return Float.valueOf(str);
    }
}
