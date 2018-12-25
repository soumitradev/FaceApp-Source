package com.google.common.base;

import android.support.v4.internal.view.SupportMenu;
import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.annotations.GwtIncompatible;
import java.util.BitSet;
import javax.annotation.CheckReturnValue;

@GwtCompatible(emulated = true)
@Beta
public abstract class CharMatcher implements Predicate<Character> {
    public static final CharMatcher ANY = any();
    public static final CharMatcher ASCII = ascii();
    public static final CharMatcher BREAKING_WHITESPACE = breakingWhitespace();
    public static final CharMatcher DIGIT = digit();
    private static final int DISTINCT_CHARS = 65536;
    public static final CharMatcher INVISIBLE = invisible();
    public static final CharMatcher JAVA_DIGIT = javaDigit();
    public static final CharMatcher JAVA_ISO_CONTROL = javaIsoControl();
    public static final CharMatcher JAVA_LETTER = javaLetter();
    public static final CharMatcher JAVA_LETTER_OR_DIGIT = javaLetterOrDigit();
    public static final CharMatcher JAVA_LOWER_CASE = javaLowerCase();
    public static final CharMatcher JAVA_UPPER_CASE = javaUpperCase();
    public static final CharMatcher NONE = none();
    public static final CharMatcher SINGLE_WIDTH = singleWidth();
    public static final CharMatcher WHITESPACE = whitespace();

    public abstract boolean matches(char c);

    public static CharMatcher any() {
        return CharMatcher$Any.INSTANCE;
    }

    public static CharMatcher none() {
        return CharMatcher$None.INSTANCE;
    }

    public static CharMatcher whitespace() {
        return CharMatcher$Whitespace.INSTANCE;
    }

    public static CharMatcher breakingWhitespace() {
        return CharMatcher$BreakingWhitespace.INSTANCE;
    }

    public static CharMatcher ascii() {
        return CharMatcher$Ascii.INSTANCE;
    }

    public static CharMatcher digit() {
        return CharMatcher$Digit.INSTANCE;
    }

    public static CharMatcher javaDigit() {
        return CharMatcher$JavaDigit.INSTANCE;
    }

    public static CharMatcher javaLetter() {
        return CharMatcher$JavaLetter.INSTANCE;
    }

    public static CharMatcher javaLetterOrDigit() {
        return CharMatcher$JavaLetterOrDigit.INSTANCE;
    }

    public static CharMatcher javaUpperCase() {
        return CharMatcher$JavaUpperCase.INSTANCE;
    }

    public static CharMatcher javaLowerCase() {
        return CharMatcher$JavaLowerCase.INSTANCE;
    }

    public static CharMatcher javaIsoControl() {
        return CharMatcher$JavaIsoControl.INSTANCE;
    }

    public static CharMatcher invisible() {
        return CharMatcher$Invisible.INSTANCE;
    }

    public static CharMatcher singleWidth() {
        return CharMatcher$SingleWidth.INSTANCE;
    }

    public static CharMatcher is(char match) {
        return new CharMatcher$Is(match);
    }

    public static CharMatcher isNot(char match) {
        return new CharMatcher$IsNot(match);
    }

    public static CharMatcher anyOf(CharSequence sequence) {
        switch (sequence.length()) {
            case 0:
                return none();
            case 1:
                return is(sequence.charAt(0));
            case 2:
                return isEither(sequence.charAt(0), sequence.charAt(1));
            default:
                return new CharMatcher$AnyOf(sequence);
        }
    }

    public static CharMatcher noneOf(CharSequence sequence) {
        return anyOf(sequence).negate();
    }

    public static CharMatcher inRange(char startInclusive, char endInclusive) {
        return new CharMatcher$InRange(startInclusive, endInclusive);
    }

    public static CharMatcher forPredicate(Predicate<? super Character> predicate) {
        return predicate instanceof CharMatcher ? (CharMatcher) predicate : new CharMatcher$ForPredicate(predicate);
    }

    protected CharMatcher() {
    }

    public CharMatcher negate() {
        return new CharMatcher$Negated(this);
    }

    public CharMatcher and(CharMatcher other) {
        return new CharMatcher$And(this, other);
    }

    public CharMatcher or(CharMatcher other) {
        return new CharMatcher$Or(this, other);
    }

    public CharMatcher precomputed() {
        return Platform.precomputeCharMatcher(this);
    }

    @GwtIncompatible("java.util.BitSet")
    CharMatcher precomputedInternal() {
        BitSet table = new BitSet();
        setBits(table);
        int totalCharacters = table.cardinality();
        if (totalCharacters * 2 <= 65536) {
            return precomputedPositive(totalCharacters, table, toString());
        }
        String negatedDescription;
        table.flip(0, 65536);
        int negatedCharacters = 65536 - totalCharacters;
        String suffix = ".negate()";
        String description = toString();
        if (description.endsWith(suffix)) {
            negatedDescription = description.substring(0, description.length() - suffix.length());
        } else {
            negatedDescription = new StringBuilder();
            negatedDescription.append(description);
            negatedDescription.append(suffix);
            negatedDescription = negatedDescription.toString();
        }
        return new CharMatcher$1(this, precomputedPositive(negatedCharacters, table, negatedDescription), description);
    }

    @GwtIncompatible("java.util.BitSet")
    private static CharMatcher precomputedPositive(int totalCharacters, BitSet table, String description) {
        switch (totalCharacters) {
            case 0:
                return none();
            case 1:
                return is((char) table.nextSetBit(0));
            case 2:
                char c1 = (char) table.nextSetBit(0);
                return isEither(c1, (char) table.nextSetBit(c1 + 1));
            default:
                return isSmall(totalCharacters, table.length()) ? SmallCharMatcher.from(table, description) : new CharMatcher$BitSetMatcher(table, description, null);
        }
    }

    @GwtIncompatible("SmallCharMatcher")
    private static boolean isSmall(int totalCharacters, int tableLength) {
        return totalCharacters <= 1023 && tableLength > (totalCharacters * 4) * 16;
    }

    @GwtIncompatible("java.util.BitSet")
    void setBits(BitSet table) {
        for (int c = SupportMenu.USER_MASK; c >= 0; c--) {
            if (matches((char) c)) {
                table.set(c);
            }
        }
    }

    public boolean matchesAnyOf(CharSequence sequence) {
        return matchesNoneOf(sequence) ^ 1;
    }

    public boolean matchesAllOf(CharSequence sequence) {
        for (int i = sequence.length() - 1; i >= 0; i--) {
            if (!matches(sequence.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public boolean matchesNoneOf(CharSequence sequence) {
        return indexIn(sequence) == -1;
    }

    public int indexIn(CharSequence sequence) {
        return indexIn(sequence, 0);
    }

    public int indexIn(CharSequence sequence, int start) {
        int length = sequence.length();
        Preconditions.checkPositionIndex(start, length);
        for (int i = start; i < length; i++) {
            if (matches(sequence.charAt(i))) {
                return i;
            }
        }
        return -1;
    }

    public int lastIndexIn(CharSequence sequence) {
        for (int i = sequence.length() - 1; i >= 0; i--) {
            if (matches(sequence.charAt(i))) {
                return i;
            }
        }
        return -1;
    }

    public int countIn(CharSequence sequence) {
        int count = 0;
        for (int i = 0; i < sequence.length(); i++) {
            if (matches(sequence.charAt(i))) {
                count++;
            }
        }
        return count;
    }

    @CheckReturnValue
    public String removeFrom(CharSequence sequence) {
        String string = sequence.toString();
        int pos = indexIn(string);
        if (pos == -1) {
            return string;
        }
        char[] chars = string.toCharArray();
        int pos2 = pos;
        pos = 1;
        while (true) {
            pos2++;
            while (pos2 != chars.length) {
                if (matches(chars[pos2])) {
                    pos++;
                } else {
                    chars[pos2 - pos] = chars[pos2];
                    pos2++;
                }
            }
            return new String(chars, 0, pos2 - pos);
        }
    }

    @CheckReturnValue
    public String retainFrom(CharSequence sequence) {
        return negate().removeFrom(sequence);
    }

    @CheckReturnValue
    public String replaceFrom(CharSequence sequence, char replacement) {
        String string = sequence.toString();
        int pos = indexIn(string);
        if (pos == -1) {
            return string;
        }
        char[] chars = string.toCharArray();
        chars[pos] = replacement;
        for (int i = pos + 1; i < chars.length; i++) {
            if (matches(chars[i])) {
                chars[i] = replacement;
            }
        }
        return new String(chars);
    }

    @CheckReturnValue
    public String replaceFrom(CharSequence sequence, CharSequence replacement) {
        int replacementLen = replacement.length();
        if (replacementLen == 0) {
            return removeFrom(sequence);
        }
        int oldpos = 0;
        if (replacementLen == 1) {
            return replaceFrom(sequence, replacement.charAt(0));
        }
        String string = sequence.toString();
        int pos = indexIn(string);
        if (pos == -1) {
            return string;
        }
        int len = string.length();
        StringBuilder buf = new StringBuilder(((len * 3) / 2) + 16);
        do {
            buf.append(string, oldpos, pos);
            buf.append(replacement);
            oldpos = pos + 1;
            pos = indexIn(string, oldpos);
        } while (pos != -1);
        buf.append(string, oldpos, len);
        return buf.toString();
    }

    @CheckReturnValue
    public String trimFrom(CharSequence sequence) {
        int len = sequence.length();
        int first = 0;
        while (first < len) {
            if (!matches(sequence.charAt(first))) {
                break;
            }
            first++;
        }
        int last = len - 1;
        while (last > first) {
            if (!matches(sequence.charAt(last))) {
                break;
            }
            last--;
        }
        return sequence.subSequence(first, last + 1).toString();
    }

    @CheckReturnValue
    public String trimLeadingFrom(CharSequence sequence) {
        int len = sequence.length();
        for (int first = 0; first < len; first++) {
            if (!matches(sequence.charAt(first))) {
                return sequence.subSequence(first, len).toString();
            }
        }
        return "";
    }

    @CheckReturnValue
    public String trimTrailingFrom(CharSequence sequence) {
        for (int last = sequence.length() - 1; last >= 0; last--) {
            if (!matches(sequence.charAt(last))) {
                return sequence.subSequence(0, last + 1).toString();
            }
        }
        return "";
    }

    @CheckReturnValue
    public String collapseFrom(CharSequence sequence, char replacement) {
        int i;
        StringBuilder stringBuilder;
        StringBuilder builder;
        int len = sequence.length();
        int i2 = 0;
        while (true) {
            i = i2;
            if (i >= len) {
                return sequence.toString();
            }
            char c = sequence.charAt(i);
            if (matches(c)) {
                if (c != replacement || (i != len - 1 && matches(sequence.charAt(i + 1)))) {
                    stringBuilder = new StringBuilder(len);
                    stringBuilder.append(sequence.subSequence(0, i));
                    builder = stringBuilder.append(replacement);
                } else {
                    i++;
                }
            }
            i2 = i + 1;
        }
        stringBuilder = new StringBuilder(len);
        stringBuilder.append(sequence.subSequence(0, i));
        builder = stringBuilder.append(replacement);
        return finishCollapseFrom(sequence, i + 1, len, replacement, builder, true);
    }

    @CheckReturnValue
    public String trimAndCollapseFrom(CharSequence sequence, char replacement) {
        int len = sequence.length();
        int first = 0;
        int last = len - 1;
        while (first < len && matches(sequence.charAt(first))) {
            first++;
        }
        while (last > first && matches(sequence.charAt(last))) {
            last--;
        }
        if (first == 0 && last == len - 1) {
            return collapseFrom(sequence, replacement);
        }
        return finishCollapseFrom(sequence, first, last + 1, replacement, new StringBuilder((last + 1) - first), false);
    }

    private String finishCollapseFrom(CharSequence sequence, int start, int end, char replacement, StringBuilder builder, boolean inMatchingGroup) {
        boolean inMatchingGroup2 = inMatchingGroup;
        for (int i = start; i < end; i++) {
            char c = sequence.charAt(i);
            if (!matches(c)) {
                builder.append(c);
                inMatchingGroup2 = false;
            } else if (!inMatchingGroup2) {
                builder.append(replacement);
                inMatchingGroup2 = true;
            }
        }
        return builder.toString();
    }

    @Deprecated
    public boolean apply(Character character) {
        return matches(character.charValue());
    }

    public String toString() {
        return super.toString();
    }

    private static String showCharacter(char c) {
        String hex = "0123456789ABCDEF";
        char[] tmp = new char[]{'\\', 'u', '\u0000', '\u0000', '\u0000', '\u0000'};
        for (int i = 0; i < 4; i++) {
            tmp[5 - i] = hex.charAt(c & 15);
            c = (char) (c >> 4);
        }
        return String.copyValueOf(tmp);
    }

    private static CharMatcher$IsEither isEither(char c1, char c2) {
        return new CharMatcher$IsEither(c1, c2);
    }
}
