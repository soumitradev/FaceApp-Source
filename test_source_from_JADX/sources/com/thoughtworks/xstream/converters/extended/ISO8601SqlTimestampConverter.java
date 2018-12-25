package com.thoughtworks.xstream.converters.extended;

import java.sql.Timestamp;
import java.util.Date;

public class ISO8601SqlTimestampConverter extends ISO8601DateConverter {
    private static final String PADDING = "000000000";

    public boolean canConvert(Class type) {
        return type.equals(Timestamp.class);
    }

    public Object fromString(String str) {
        int idxFraction = str.lastIndexOf(46);
        int nanos = 0;
        if (idxFraction > 0) {
            int idx = idxFraction + 1;
            while (Character.isDigit(str.charAt(idx))) {
                idx++;
            }
            nanos = Integer.parseInt(str.substring(idxFraction + 1, idx));
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(str.substring(0, idxFraction));
            stringBuilder.append(str.substring(idx));
            str = stringBuilder.toString();
        }
        Timestamp timestamp = new Timestamp(((Date) super.fromString(str)).getTime());
        timestamp.setNanos(nanos);
        return timestamp;
    }

    public String toString(Object obj) {
        Timestamp timestamp = (Timestamp) obj;
        String str = super.toString(new Date((timestamp.getTime() / 1000) * 1000));
        String nanos = String.valueOf(timestamp.getNanos());
        int idxFraction = str.lastIndexOf(46);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(str.substring(0, idxFraction + 1));
        stringBuilder.append(PADDING.substring(nanos.length()));
        stringBuilder.append(nanos);
        stringBuilder.append(str.substring(idxFraction + 4));
        return stringBuilder.toString();
    }
}
