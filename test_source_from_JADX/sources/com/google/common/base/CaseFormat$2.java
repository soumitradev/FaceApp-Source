package com.google.common.base;

enum CaseFormat$2 extends CaseFormat {
    CaseFormat$2(String str, int i, CharMatcher x0, String x1) {
        super(str, i, x0, x1, null);
    }

    String normalizeWord(String word) {
        return Ascii.toLowerCase(word);
    }

    String convert(CaseFormat format, String s) {
        if (format == LOWER_HYPHEN) {
            return s.replace('_', '-');
        }
        if (format == UPPER_UNDERSCORE) {
            return Ascii.toUpperCase(s);
        }
        return super.convert(format, s);
    }
}
