package com.thoughtworks.xstream.converters.extended;

import com.thoughtworks.xstream.converters.basic.AbstractSingleValueConverter;
import java.util.Locale;

public class LocaleConverter extends AbstractSingleValueConverter {
    public boolean canConvert(Class type) {
        return type.equals(Locale.class);
    }

    public Object fromString(String str) {
        String country;
        String str2;
        String language;
        String variant;
        int[] underscorePositions = underscorePositions(str);
        if (underscorePositions[0] == -1) {
            country = "";
            str2 = "";
            language = str;
        } else if (underscorePositions[1] == -1) {
            language = str.substring(0, underscorePositions[0]);
            country = str.substring(underscorePositions[0] + 1);
            variant = "";
            return new Locale(language, country, variant);
        } else {
            language = str.substring(0, underscorePositions[0]);
            variant = str.substring(underscorePositions[0] + 1, underscorePositions[1]);
            str2 = str.substring(underscorePositions[1] + 1);
            country = variant;
        }
        variant = str2;
        return new Locale(language, country, variant);
    }

    private int[] underscorePositions(String in) {
        int[] result = new int[2];
        int i = 0;
        while (i < result.length) {
            result[i] = in.indexOf(95, (i == 0 ? 0 : result[i - 1]) + 1);
            i++;
        }
        return result;
    }
}
