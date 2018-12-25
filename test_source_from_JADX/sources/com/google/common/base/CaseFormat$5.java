package com.google.common.base;

enum CaseFormat$5 extends CaseFormat {
    CaseFormat$5(String str, int i, CharMatcher x0, String x1) {
        super(str, i, x0, x1, null);
    }

    String normalizeWord(String word) {
        return Ascii.toUpperCase(word);
    }

    String convert(CaseFormat format, String s) {
        if (format == LOWER_HYPHEN) {
            return Ascii.toLowerCase(s.replace('_', '-'));
        }
        if (format == LOWER_UNDERSCORE) {
            return Ascii.toLowerCase(s);
        }
        return super.convert(format, s);
    }
}
