package com.thoughtworks.xstream.converters.basic;

public class DoubleConverter extends AbstractSingleValueConverter {
    public boolean canConvert(Class type) {
        if (!type.equals(Double.TYPE)) {
            if (!type.equals(Double.class)) {
                return false;
            }
        }
        return true;
    }

    public Object fromString(String str) {
        return Double.valueOf(str);
    }
}
