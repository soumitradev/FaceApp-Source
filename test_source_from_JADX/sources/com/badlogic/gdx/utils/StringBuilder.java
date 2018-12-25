package com.badlogic.gdx.utils;

import com.facebook.internal.ServerProtocol;
import java.util.Arrays;
import kotlin.jvm.internal.CharCompanionObject;
import org.apache.commons.compress.compressors.bzip2.BZip2Constants;

public class StringBuilder implements Appendable, CharSequence {
    static final int INITIAL_CAPACITY = 16;
    private static final char[] digits = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
    public char[] chars;
    public int length;

    public static int numChars(int value, int radix) {
        int result = value < 0 ? 2 : 1;
        while (true) {
            int i = value / radix;
            value = i;
            if (i == 0) {
                return result;
            }
            result++;
        }
    }

    public static int numChars(long value, int radix) {
        int result = value < 0 ? 2 : 1;
        while (true) {
            long j = value / ((long) radix);
            value = j;
            if (j == 0) {
                return result;
            }
            result++;
        }
    }

    final char[] getValue() {
        return this.chars;
    }

    public StringBuilder() {
        this.chars = new char[16];
    }

    public StringBuilder(int capacity) {
        if (capacity < 0) {
            throw new NegativeArraySizeException();
        }
        this.chars = new char[capacity];
    }

    public StringBuilder(CharSequence seq) {
        this(seq.toString());
    }

    public StringBuilder(StringBuilder builder) {
        this.length = builder.length;
        this.chars = new char[(this.length + 16)];
        System.arraycopy(builder.chars, 0, this.chars, 0, this.length);
    }

    public StringBuilder(String string) {
        this.length = string.length();
        this.chars = new char[(this.length + 16)];
        string.getChars(0, this.length, this.chars, 0);
    }

    private void enlargeBuffer(int min) {
        int newSize = ((this.chars.length >> 1) + this.chars.length) + 2;
        char[] newData = new char[(min > newSize ? min : newSize)];
        System.arraycopy(this.chars, 0, newData, 0, this.length);
        this.chars = newData;
    }

    final void appendNull() {
        int newSize = this.length + 4;
        if (newSize > this.chars.length) {
            enlargeBuffer(newSize);
        }
        char[] cArr = this.chars;
        int i = this.length;
        this.length = i + 1;
        cArr[i] = 'n';
        cArr = this.chars;
        i = this.length;
        this.length = i + 1;
        cArr[i] = 'u';
        cArr = this.chars;
        i = this.length;
        this.length = i + 1;
        cArr[i] = 'l';
        cArr = this.chars;
        i = this.length;
        this.length = i + 1;
        cArr[i] = 'l';
    }

    final void append0(char[] value) {
        int newSize = this.length + value.length;
        if (newSize > this.chars.length) {
            enlargeBuffer(newSize);
        }
        System.arraycopy(value, 0, this.chars, this.length, value.length);
        this.length = newSize;
    }

    final void append0(char[] value, int offset, int length) {
        StringBuilder stringBuilder;
        if (offset <= value.length) {
            if (offset >= 0) {
                if (length >= 0) {
                    if (value.length - offset >= length) {
                        int newSize = this.length + length;
                        if (newSize > this.chars.length) {
                            enlargeBuffer(newSize);
                        }
                        System.arraycopy(value, offset, this.chars, this.length, length);
                        this.length = newSize;
                        return;
                    }
                }
                stringBuilder = new StringBuilder();
                stringBuilder.append("Length out of bounds: ");
                stringBuilder.append(length);
                throw new ArrayIndexOutOfBoundsException(stringBuilder.toString());
            }
        }
        stringBuilder = new StringBuilder();
        stringBuilder.append("Offset out of bounds: ");
        stringBuilder.append(offset);
        throw new ArrayIndexOutOfBoundsException(stringBuilder.toString());
    }

    final void append0(char ch) {
        if (this.length == this.chars.length) {
            enlargeBuffer(this.length + 1);
        }
        char[] cArr = this.chars;
        int i = this.length;
        this.length = i + 1;
        cArr[i] = ch;
    }

    final void append0(String string) {
        if (string == null) {
            appendNull();
            return;
        }
        int adding = string.length();
        int newSize = this.length + adding;
        if (newSize > this.chars.length) {
            enlargeBuffer(newSize);
        }
        string.getChars(0, adding, this.chars, this.length);
        this.length = newSize;
    }

    final void append0(CharSequence s, int start, int end) {
        if (s == null) {
            s = "null";
        }
        if (start >= 0 && end >= 0 && start <= end) {
            if (end <= s.length()) {
                append0(s.subSequence(start, end).toString());
                return;
            }
        }
        throw new IndexOutOfBoundsException();
    }

    public int capacity() {
        return this.chars.length;
    }

    public char charAt(int index) {
        if (index >= 0) {
            if (index < this.length) {
                return this.chars[index];
            }
        }
        throw new StringIndexOutOfBoundsException(index);
    }

    final void delete0(int start, int end) {
        if (start >= 0) {
            if (end > this.length) {
                end = this.length;
            }
            if (end != start) {
                if (end > start) {
                    int count = this.length - end;
                    if (count >= 0) {
                        System.arraycopy(this.chars, end, this.chars, start, count);
                    }
                    this.length -= end - start;
                    return;
                }
            }
            return;
        }
        throw new StringIndexOutOfBoundsException();
    }

    final void deleteCharAt0(int location) {
        if (location >= 0) {
            if (location < this.length) {
                int count = (this.length - location) - 1;
                if (count > 0) {
                    System.arraycopy(this.chars, location + 1, this.chars, location, count);
                }
                this.length--;
                return;
            }
        }
        throw new StringIndexOutOfBoundsException(location);
    }

    public void ensureCapacity(int min) {
        if (min > this.chars.length) {
            int twice = (this.chars.length << 1) + 2;
            enlargeBuffer(twice > min ? twice : min);
        }
    }

    public void getChars(int start, int end, char[] dest, int destStart) {
        if (start <= this.length && end <= this.length) {
            if (start <= end) {
                System.arraycopy(this.chars, start, dest, destStart, end - start);
                return;
            }
        }
        throw new StringIndexOutOfBoundsException();
    }

    final void insert0(int index, char[] value) {
        if (index >= 0) {
            if (index <= this.length) {
                if (value.length != 0) {
                    move(value.length, index);
                    System.arraycopy(value, 0, value, index, value.length);
                    this.length += value.length;
                    return;
                }
                return;
            }
        }
        throw new StringIndexOutOfBoundsException(index);
    }

    final void insert0(int index, char[] value, int start, int length) {
        if (index < 0 || index > length) {
            throw new StringIndexOutOfBoundsException(index);
        } else if (start < 0 || length < 0 || length > value.length - start) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("offset ");
            stringBuilder.append(start);
            stringBuilder.append(", length ");
            stringBuilder.append(length);
            stringBuilder.append(", char[].length ");
            stringBuilder.append(value.length);
            throw new StringIndexOutOfBoundsException(stringBuilder.toString());
        } else if (length != 0) {
            move(length, index);
            System.arraycopy(value, start, this.chars, index, length);
            this.length += length;
        }
    }

    final void insert0(int index, char ch) {
        if (index >= 0) {
            if (index <= this.length) {
                move(1, index);
                this.chars[index] = ch;
                this.length++;
                return;
            }
        }
        throw new ArrayIndexOutOfBoundsException(index);
    }

    final void insert0(int index, String string) {
        if (index < 0 || index > this.length) {
            throw new StringIndexOutOfBoundsException(index);
        }
        if (string == null) {
            string = "null";
        }
        int min = string.length();
        if (min != 0) {
            move(min, index);
            string.getChars(0, min, this.chars, index);
            this.length += min;
        }
    }

    final void insert0(int index, CharSequence s, int start, int end) {
        if (s == null) {
            s = "null";
        }
        if (index >= 0 && index <= this.length && start >= 0 && end >= 0 && start <= end) {
            if (end <= s.length()) {
                insert0(index, s.subSequence(start, end).toString());
                return;
            }
        }
        throw new IndexOutOfBoundsException();
    }

    public int length() {
        return this.length;
    }

    private void move(int size, int index) {
        if (this.chars.length - this.length >= size) {
            System.arraycopy(this.chars, index, this.chars, index + size, this.length - index);
            return;
        }
        int a = this.length + size;
        int b = (this.chars.length << 1) + 2;
        char[] newData = new char[(a > b ? a : b)];
        System.arraycopy(this.chars, 0, newData, 0, index);
        System.arraycopy(this.chars, index, newData, index + size, this.length - index);
        this.chars = newData;
    }

    final void replace0(int start, int end, String string) {
        if (start >= 0) {
            if (end > this.length) {
                end = this.length;
            }
            if (end > start) {
                int stringLength = string.length();
                int diff = (end - start) - stringLength;
                if (diff > 0) {
                    System.arraycopy(this.chars, end, this.chars, start + stringLength, this.length - end);
                } else if (diff < 0) {
                    move(-diff, end);
                }
                string.getChars(0, stringLength, this.chars, start);
                this.length -= diff;
                return;
            } else if (start == end) {
                if (string == null) {
                    throw new NullPointerException();
                }
                insert0(start, string);
                return;
            }
        }
        throw new StringIndexOutOfBoundsException();
    }

    final void reverse0() {
        if (this.length >= 2) {
            int end = r0.length - 1;
            char frontHigh = r0.chars[0];
            char endLow = r0.chars[end];
            boolean allowFrontSur = true;
            boolean allowEndSur = true;
            int i = 0;
            int mid = r0.length / 2;
            while (i < mid) {
                char frontLow = r0.chars[i + 1];
                char endHigh = r0.chars[end - 1];
                boolean z = allowFrontSur && frontLow >= CharCompanionObject.MIN_LOW_SURROGATE && frontLow <= '?' && frontHigh >= '?' && frontHigh <= CharCompanionObject.MAX_HIGH_SURROGATE;
                boolean surAtFront = z;
                if (!surAtFront || r0.length >= 3) {
                    boolean surAtEnd = allowEndSur && endHigh >= '?' && endHigh <= CharCompanionObject.MAX_HIGH_SURROGATE && endLow >= CharCompanionObject.MIN_LOW_SURROGATE && endLow <= '?';
                    allowEndSur = true;
                    allowFrontSur = true;
                    if (surAtFront == surAtEnd) {
                        if (surAtFront) {
                            r0.chars[end] = frontLow;
                            r0.chars[end - 1] = frontHigh;
                            r0.chars[i] = endHigh;
                            r0.chars[i + 1] = endLow;
                            frontHigh = r0.chars[i + 2];
                            endLow = r0.chars[end - 2];
                            i++;
                            end--;
                        } else {
                            r0.chars[end] = frontHigh;
                            r0.chars[i] = endLow;
                            frontHigh = frontLow;
                            endLow = endHigh;
                        }
                    } else if (surAtFront) {
                        r0.chars[end] = frontLow;
                        r0.chars[i] = endLow;
                        endLow = endHigh;
                        allowFrontSur = false;
                    } else {
                        r0.chars[end] = frontHigh;
                        r0.chars[i] = endHigh;
                        frontHigh = frontLow;
                        allowEndSur = false;
                    }
                    i++;
                    end--;
                } else {
                    return;
                }
            }
            if ((r0.length & 1) == 1 && !(allowFrontSur && allowEndSur)) {
                r0.chars[end] = allowFrontSur ? endLow : frontHigh;
            }
        }
    }

    public void setCharAt(int index, char ch) {
        if (index >= 0) {
            if (index < this.length) {
                this.chars[index] = ch;
                return;
            }
        }
        throw new StringIndexOutOfBoundsException(index);
    }

    public void setLength(int newLength) {
        if (newLength < 0) {
            throw new StringIndexOutOfBoundsException(newLength);
        }
        if (newLength > this.chars.length) {
            enlargeBuffer(newLength);
        } else if (this.length < newLength) {
            Arrays.fill(this.chars, this.length, newLength, '\u0000');
        }
        this.length = newLength;
    }

    public String substring(int start) {
        if (start < 0 || start > this.length) {
            throw new StringIndexOutOfBoundsException(start);
        } else if (start == this.length) {
            return "";
        } else {
            return new String(this.chars, start, this.length - start);
        }
    }

    public String substring(int start, int end) {
        if (start < 0 || start > end || end > this.length) {
            throw new StringIndexOutOfBoundsException();
        } else if (start == end) {
            return "";
        } else {
            return new String(this.chars, start, end - start);
        }
    }

    public String toString() {
        if (this.length == 0) {
            return "";
        }
        return new String(this.chars, 0, this.length);
    }

    public CharSequence subSequence(int start, int end) {
        return substring(start, end);
    }

    public int indexOf(String string) {
        return indexOf(string, 0);
    }

    public int indexOf(String subString, int start) {
        if (start < 0) {
            start = 0;
        }
        int subCount = subString.length();
        if (subCount <= 0) {
            int i;
            if (start >= this.length) {
                if (start != 0) {
                    i = this.length;
                    return i;
                }
            }
            i = start;
            return i;
        } else if (subCount + start > this.length) {
            return -1;
        } else {
            char firstChar = subString.charAt(0);
            while (true) {
                int i2 = start;
                boolean found = false;
                while (i2 < this.length) {
                    if (this.chars[i2] == firstChar) {
                        found = true;
                        break;
                    }
                    i2++;
                }
                if (!found) {
                    break;
                } else if (subCount + i2 > this.length) {
                    break;
                } else {
                    int o1 = i2;
                    int o2 = 0;
                    while (true) {
                        o2++;
                        if (o2 >= subCount) {
                            break;
                        }
                        o1++;
                        if (this.chars[o1] != subString.charAt(o2)) {
                            break;
                        }
                    }
                    if (o2 == subCount) {
                        return i2;
                    }
                    start = i2 + 1;
                }
            }
            return -1;
        }
    }

    public int lastIndexOf(String string) {
        return lastIndexOf(string, this.length);
    }

    public int lastIndexOf(String subString, int start) {
        int subCount = subString.length();
        if (subCount > this.length || start < 0) {
            return -1;
        }
        if (subCount > 0) {
            if (start > this.length - subCount) {
                start = this.length - subCount;
            }
            char firstChar = subString.charAt(0);
            while (true) {
                int i = start;
                boolean found = false;
                while (i >= 0) {
                    if (this.chars[i] == firstChar) {
                        found = true;
                        break;
                    }
                    i--;
                }
                if (!found) {
                    return -1;
                }
                int o1 = i;
                int o2 = 0;
                while (true) {
                    o2++;
                    if (o2 >= subCount) {
                        break;
                    }
                    o1++;
                    if (this.chars[o1] != subString.charAt(o2)) {
                        break;
                    }
                }
                if (o2 == subCount) {
                    return i;
                }
                start = i - 1;
            }
        } else {
            return start < this.length ? start : this.length;
        }
    }

    public void trimToSize() {
        if (this.length < this.chars.length) {
            char[] newValue = new char[this.length];
            System.arraycopy(this.chars, 0, newValue, 0, this.length);
            this.chars = newValue;
        }
    }

    public int codePointAt(int index) {
        if (index >= 0) {
            if (index < this.length) {
                return Character.codePointAt(this.chars, index, this.length);
            }
        }
        throw new StringIndexOutOfBoundsException(index);
    }

    public int codePointBefore(int index) {
        if (index >= 1) {
            if (index <= this.length) {
                return Character.codePointBefore(this.chars, index);
            }
        }
        throw new StringIndexOutOfBoundsException(index);
    }

    public int codePointCount(int beginIndex, int endIndex) {
        if (beginIndex >= 0 && endIndex <= this.length) {
            if (beginIndex <= endIndex) {
                return Character.codePointCount(this.chars, beginIndex, endIndex - beginIndex);
            }
        }
        throw new StringIndexOutOfBoundsException();
    }

    public int offsetByCodePoints(int index, int codePointOffset) {
        return Character.offsetByCodePoints(this.chars, 0, this.length, index, codePointOffset);
    }

    public StringBuilder append(boolean b) {
        append0(b ? ServerProtocol.DIALOG_RETURN_SCOPES_TRUE : "false");
        return this;
    }

    public StringBuilder append(char c) {
        append0(c);
        return this;
    }

    public StringBuilder append(int value) {
        return append(value, 0);
    }

    public StringBuilder append(int value, int minLength) {
        return append(value, minLength, '0');
    }

    public StringBuilder append(int value, int minLength, char prefix) {
        if (value == Integer.MIN_VALUE) {
            append0("-2147483648");
            return this;
        }
        if (value < 0) {
            append0('-');
            value = -value;
        }
        if (minLength > 1) {
            for (int j = minLength - numChars(value, 10); j > 0; j--) {
                append(prefix);
            }
        }
        if (value >= 10000) {
            if (value >= 1000000000) {
                append0(digits[(int) ((((long) value) % 10000000000L) / 1000000000)]);
            }
            if (value >= 100000000) {
                append0(digits[(value % 1000000000) / 100000000]);
            }
            if (value >= 10000000) {
                append0(digits[(value % 100000000) / 10000000]);
            }
            if (value >= 1000000) {
                append0(digits[(value % 10000000) / 1000000]);
            }
            if (value >= BZip2Constants.BASEBLOCKSIZE) {
                append0(digits[(value % 1000000) / BZip2Constants.BASEBLOCKSIZE]);
            }
            append0(digits[(value % BZip2Constants.BASEBLOCKSIZE) / 10000]);
        }
        if (value >= 1000) {
            append0(digits[(value % 10000) / 1000]);
        }
        if (value >= 100) {
            append0(digits[(value % 1000) / 100]);
        }
        if (value >= 10) {
            append0(digits[(value % 100) / 10]);
        }
        append0(digits[value % 10]);
        return this;
    }

    public StringBuilder append(long value) {
        return append(value, 0);
    }

    public StringBuilder append(long value, int minLength) {
        return append(value, minLength, '0');
    }

    public StringBuilder append(long value, int minLength, char prefix) {
        if (value == Long.MIN_VALUE) {
            append0("-9223372036854775808");
            return this;
        }
        if (value < 0) {
            append0('-');
            value = -value;
        }
        if (minLength > 1) {
            for (int j = minLength - numChars(value, 10); j > 0; j--) {
                append(prefix);
            }
        }
        if (value >= 10000) {
            if (value >= 1000000000000000000L) {
                append0(digits[(int) ((((double) value) % 1.0E19d) / 1.0E18d)]);
            }
            if (value >= 100000000000000000L) {
                append0(digits[(int) ((value % 1000000000000000000L) / 100000000000000000L)]);
            }
            if (value >= 10000000000000000L) {
                append0(digits[(int) ((value % 100000000000000000L) / 10000000000000000L)]);
            }
            if (value >= 1000000000000000L) {
                append0(digits[(int) ((value % 10000000000000000L) / 1000000000000000L)]);
            }
            if (value >= 100000000000000L) {
                append0(digits[(int) ((value % 1000000000000000L) / 100000000000000L)]);
            }
            if (value >= 10000000000000L) {
                append0(digits[(int) ((value % 100000000000000L) / 10000000000000L)]);
            }
            if (value >= 1000000000000L) {
                append0(digits[(int) ((value % 10000000000000L) / 1000000000000L)]);
            }
            if (value >= 100000000000L) {
                append0(digits[(int) ((value % 1000000000000L) / 100000000000L)]);
            }
            if (value >= 10000000000L) {
                append0(digits[(int) ((value % 100000000000L) / 10000000000L)]);
            }
            if (value >= 1000000000) {
                append0(digits[(int) ((value % 10000000000L) / 1000000000)]);
            }
            if (value >= 100000000) {
                append0(digits[(int) ((value % 1000000000) / 100000000)]);
            }
            if (value >= 10000000) {
                append0(digits[(int) ((value % 100000000) / 10000000)]);
            }
            if (value >= 1000000) {
                append0(digits[(int) ((value % 10000000) / 1000000)]);
            }
            if (value >= 100000) {
                append0(digits[(int) ((value % 1000000) / 100000)]);
            }
            append0(digits[(int) ((value % 100000) / 10000)]);
        }
        if (value >= 1000) {
            append0(digits[(int) ((value % 10000) / 1000)]);
        }
        if (value >= 100) {
            append0(digits[(int) ((value % 1000) / 100)]);
        }
        if (value >= 10) {
            append0(digits[(int) ((value % 100) / 10)]);
        }
        append0(digits[(int) (value % 10)]);
        return this;
    }

    public StringBuilder append(float f) {
        append0(Float.toString(f));
        return this;
    }

    public StringBuilder append(double d) {
        append0(Double.toString(d));
        return this;
    }

    public StringBuilder append(Object obj) {
        if (obj == null) {
            appendNull();
        } else {
            append0(obj.toString());
        }
        return this;
    }

    public StringBuilder append(String str) {
        append0(str);
        return this;
    }

    public StringBuilder append(char[] ch) {
        append0(ch);
        return this;
    }

    public StringBuilder append(char[] str, int offset, int len) {
        append0(str, offset, len);
        return this;
    }

    public StringBuilder append(CharSequence csq) {
        if (csq == null) {
            appendNull();
        } else if (csq instanceof StringBuilder) {
            StringBuilder builder = (StringBuilder) csq;
            append0(builder.chars, 0, builder.length);
        } else {
            append0(csq.toString());
        }
        return this;
    }

    public StringBuilder append(StringBuilder builder) {
        if (builder == null) {
            appendNull();
        } else {
            append0(builder.chars, 0, builder.length);
        }
        return this;
    }

    public StringBuilder append(CharSequence csq, int start, int end) {
        append0(csq, start, end);
        return this;
    }

    public StringBuilder append(StringBuilder builder, int start, int end) {
        if (builder == null) {
            appendNull();
        } else {
            append0(builder.chars, start, end);
        }
        return this;
    }

    public StringBuilder appendCodePoint(int codePoint) {
        append0(Character.toChars(codePoint));
        return this;
    }

    public StringBuilder delete(int start, int end) {
        delete0(start, end);
        return this;
    }

    public StringBuilder deleteCharAt(int index) {
        deleteCharAt0(index);
        return this;
    }

    public StringBuilder insert(int offset, boolean b) {
        insert0(offset, b ? ServerProtocol.DIALOG_RETURN_SCOPES_TRUE : "false");
        return this;
    }

    public StringBuilder insert(int offset, char c) {
        insert0(offset, c);
        return this;
    }

    public StringBuilder insert(int offset, int i) {
        insert0(offset, Integer.toString(i));
        return this;
    }

    public StringBuilder insert(int offset, long l) {
        insert0(offset, Long.toString(l));
        return this;
    }

    public StringBuilder insert(int offset, float f) {
        insert0(offset, Float.toString(f));
        return this;
    }

    public StringBuilder insert(int offset, double d) {
        insert0(offset, Double.toString(d));
        return this;
    }

    public StringBuilder insert(int offset, Object obj) {
        insert0(offset, obj == null ? "null" : obj.toString());
        return this;
    }

    public StringBuilder insert(int offset, String str) {
        insert0(offset, str);
        return this;
    }

    public StringBuilder insert(int offset, char[] ch) {
        insert0(offset, ch);
        return this;
    }

    public StringBuilder insert(int offset, char[] str, int strOffset, int strLen) {
        insert0(offset, str, strOffset, strLen);
        return this;
    }

    public StringBuilder insert(int offset, CharSequence s) {
        insert0(offset, s == null ? "null" : s.toString());
        return this;
    }

    public StringBuilder insert(int offset, CharSequence s, int start, int end) {
        insert0(offset, s, start, end);
        return this;
    }

    public StringBuilder replace(int start, int end, String str) {
        replace0(start, end, str);
        return this;
    }

    public StringBuilder reverse() {
        reverse0();
        return this;
    }

    public int hashCode() {
        return ((this.length + 31) * 31) + Arrays.hashCode(this.chars);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        StringBuilder other = (StringBuilder) obj;
        int length = this.length;
        if (length != other.length) {
            return false;
        }
        char[] chars = this.chars;
        char[] chars2 = other.chars;
        if (chars == chars2) {
            return true;
        }
        if (chars != null) {
            if (chars2 != null) {
                for (int i = 0; i < length; i++) {
                    if (chars[i] != chars2[i]) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }
}
