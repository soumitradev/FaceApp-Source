package com.google.common.base;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import javax.annotation.CheckReturnValue;

@GwtCompatible
@CheckReturnValue
public enum CaseFormat {
    ;
    
    private final CharMatcher wordBoundary;
    private final String wordSeparator;

    abstract String normalizeWord(String str);

    private CaseFormat(CharMatcher wordBoundary, String wordSeparator) {
        this.wordBoundary = wordBoundary;
        this.wordSeparator = wordSeparator;
    }

    public final String to(CaseFormat format, String str) {
        Preconditions.checkNotNull(format);
        Preconditions.checkNotNull(str);
        return format == this ? str : convert(format, str);
    }

    String convert(CaseFormat format, String s) {
        int i = 0;
        StringBuilder out = null;
        int j = -1;
        while (true) {
            int indexIn = this.wordBoundary.indexIn(s, j + 1);
            j = indexIn;
            if (indexIn == -1) {
                break;
            }
            if (i == 0) {
                out = new StringBuilder(s.length() + (this.wordSeparator.length() * 4));
                out.append(format.normalizeFirstWord(s.substring(i, j)));
            } else {
                out.append(format.normalizeWord(s.substring(i, j)));
            }
            out.append(format.wordSeparator);
            i = j + this.wordSeparator.length();
        }
        if (i == 0) {
            return format.normalizeFirstWord(s);
        }
        out.append(format.normalizeWord(s.substring(i)));
        return out.toString();
    }

    @Beta
    public Converter<String, String> converterTo(CaseFormat targetFormat) {
        return new CaseFormat$StringConverter(this, targetFormat);
    }

    private String normalizeFirstWord(String word) {
        return this == LOWER_CAMEL ? Ascii.toLowerCase(word) : normalizeWord(word);
    }

    private static String firstCharOnlyToUpper(String word) {
        if (word.isEmpty()) {
            return word;
        }
        StringBuilder stringBuilder = new StringBuilder(word.length());
        stringBuilder.append(Ascii.toUpperCase(word.charAt(0)));
        stringBuilder.append(Ascii.toLowerCase(word.substring(1)));
        return stringBuilder.toString();
    }
}
