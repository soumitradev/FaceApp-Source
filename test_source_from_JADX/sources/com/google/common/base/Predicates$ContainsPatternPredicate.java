package com.google.common.base;

import com.google.common.annotations.GwtIncompatible;
import java.io.Serializable;
import java.util.regex.Pattern;
import javax.annotation.Nullable;

@GwtIncompatible("Only used by other GWT-incompatible code.")
class Predicates$ContainsPatternPredicate implements Predicate<CharSequence>, Serializable {
    private static final long serialVersionUID = 0;
    final Pattern pattern;

    Predicates$ContainsPatternPredicate(Pattern pattern) {
        this.pattern = (Pattern) Preconditions.checkNotNull(pattern);
    }

    public boolean apply(CharSequence t) {
        return this.pattern.matcher(t).find();
    }

    public int hashCode() {
        return Objects.hashCode(new Object[]{this.pattern.pattern(), Integer.valueOf(this.pattern.flags())});
    }

    public boolean equals(@Nullable Object obj) {
        boolean z = false;
        if (!(obj instanceof Predicates$ContainsPatternPredicate)) {
            return false;
        }
        Predicates$ContainsPatternPredicate that = (Predicates$ContainsPatternPredicate) obj;
        if (Objects.equal(this.pattern.pattern(), that.pattern.pattern()) && Objects.equal(Integer.valueOf(this.pattern.flags()), Integer.valueOf(that.pattern.flags()))) {
            z = true;
        }
        return z;
    }

    public String toString() {
        String patternString = Objects.toStringHelper(this.pattern).add("pattern", this.pattern.pattern()).add("pattern.flags", this.pattern.flags()).toString();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Predicates.contains(");
        stringBuilder.append(patternString);
        stringBuilder.append(")");
        return stringBuilder.toString();
    }
}
