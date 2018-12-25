package com.thoughtworks.xstream.converters.basic;

public class LongConverter extends AbstractSingleValueConverter {
    public boolean canConvert(Class type) {
        if (!type.equals(Long.TYPE)) {
            if (!type.equals(Long.class)) {
                return false;
            }
        }
        return true;
    }

    public Object fromString(String str) {
        int len = str.length();
        if (len == 0) {
            throw new NumberFormatException("For input string: \"\"");
        } else if (len < 17) {
            return Long.decode(str);
        } else {
            char c0 = str.charAt('\u0000');
            if (c0 != '0' && c0 != '#') {
                return Long.decode(str);
            }
            long high;
            long low;
            char c1 = str.charAt(1);
            if (c0 == '#' && len == 17) {
                high = Long.parseLong(str.substring(1, 9), 16) << 32;
                low = Long.parseLong(str.substring(9, 17), 16);
            } else if ((c1 == 'x' || c1 == 'X') && len == 18) {
                high = Long.parseLong(str.substring(2, 10), 16) << 32;
                low = Long.parseLong(str.substring(10, 18), 16);
            } else if (len != 23 || c1 != '1') {
                return Long.decode(str);
            } else {
                high = Long.parseLong(str.substring(1, 12), 8) << 33;
                low = Long.parseLong(str.substring(12, 23), 8);
            }
            return new Long(high | low);
        }
    }
}
