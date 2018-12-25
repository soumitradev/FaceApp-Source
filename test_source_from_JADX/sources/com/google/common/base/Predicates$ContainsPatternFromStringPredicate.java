package com.google.common.base;

import com.google.common.annotations.GwtIncompatible;
import java.util.regex.Pattern;

@GwtIncompatible("Only used by other GWT-incompatible code.")
class Predicates$ContainsPatternFromStringPredicate extends Predicates$ContainsPatternPredicate {
    private static final long serialVersionUID = 0;

    Predicates$ContainsPatternFromStringPredicate(String string) {
        super(Pattern.compile(string));
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Predicates.containsPattern(");
        stringBuilder.append(this.pattern.pattern());
        stringBuilder.append(")");
        return stringBuilder.toString();
    }
}
