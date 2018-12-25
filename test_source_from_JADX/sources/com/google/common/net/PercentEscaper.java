package com.google.common.net;

import android.support.v4.internal.view.SupportMenu;
import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Preconditions;
import com.google.common.escape.UnicodeEscaper;
import com.pdrogfer.mididroid.event.meta.MetaEvent;
import name.antonsmirnov.firmata.FormatHelper;

@GwtCompatible
@Beta
public final class PercentEscaper extends UnicodeEscaper {
    private static final char[] PLUS_SIGN = new char[]{'+'};
    private static final char[] UPPER_HEX_DIGITS = "0123456789ABCDEF".toCharArray();
    private final boolean plusForSpace;
    private final boolean[] safeOctets;

    public PercentEscaper(String safeChars, boolean plusForSpace) {
        Preconditions.checkNotNull(safeChars);
        if (safeChars.matches(".*[0-9A-Za-z].*")) {
            throw new IllegalArgumentException("Alphanumeric characters are always 'safe' and should not be explicitly specified");
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(safeChars);
        stringBuilder.append("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789");
        safeChars = stringBuilder.toString();
        if (plusForSpace && safeChars.contains(FormatHelper.SPACE)) {
            throw new IllegalArgumentException("plusForSpace cannot be specified when space is a 'safe' character");
        }
        this.plusForSpace = plusForSpace;
        this.safeOctets = createSafeOctets(safeChars);
    }

    private static boolean[] createSafeOctets(String safeChars) {
        char[] safeCharArray = safeChars.toCharArray();
        int maxChar = -1;
        for (char c : safeCharArray) {
            maxChar = Math.max(c, maxChar);
        }
        boolean[] octets = new boolean[(maxChar + 1)];
        for (char c2 : safeCharArray) {
            octets[c2] = true;
        }
        return octets;
    }

    protected int nextEscapeIndex(CharSequence csq, int index, int end) {
        Preconditions.checkNotNull(csq);
        while (index < end) {
            char c = csq.charAt(index);
            if (c >= this.safeOctets.length) {
                break;
            } else if (!this.safeOctets[c]) {
                break;
            } else {
                index++;
            }
        }
        return index;
    }

    public String escape(String s) {
        Preconditions.checkNotNull(s);
        int slen = s.length();
        int index = 0;
        while (index < slen) {
            char c = s.charAt(index);
            if (c < this.safeOctets.length) {
                if (this.safeOctets[c]) {
                    index++;
                }
            }
            return escapeSlow(s, index);
        }
        return s;
    }

    protected char[] escape(int cp) {
        if (cp < this.safeOctets.length && this.safeOctets[cp]) {
            return null;
        }
        if (cp == 32 && this.plusForSpace) {
            return PLUS_SIGN;
        }
        if (cp <= MetaEvent.SEQUENCER_SPECIFIC) {
            return new char[]{'%', UPPER_HEX_DIGITS[cp & 15], UPPER_HEX_DIGITS[cp >>> 4]};
        } else if (cp <= 2047) {
            dest = new char[6];
            dest[0] = '%';
            dest[3] = '%';
            dest[5] = UPPER_HEX_DIGITS[cp & 15];
            cp >>>= 4;
            dest[4] = UPPER_HEX_DIGITS[(cp & 3) | 8];
            cp >>>= 2;
            dest[2] = UPPER_HEX_DIGITS[cp & 15];
            dest[1] = UPPER_HEX_DIGITS[(cp >>> 4) | 12];
            return dest;
        } else if (cp <= SupportMenu.USER_MASK) {
            dest = new char[9];
            cp >>>= 4;
            dest[7] = UPPER_HEX_DIGITS[(cp & 3) | 8];
            cp >>>= 2;
            dest[5] = UPPER_HEX_DIGITS[cp & 15];
            cp >>>= 4;
            dest[4] = UPPER_HEX_DIGITS[(cp & 3) | 8];
            dest[2] = UPPER_HEX_DIGITS[cp >>> 2];
            return dest;
        } else if (cp <= 1114111) {
            dest = new char[12];
            cp >>>= 4;
            dest[10] = UPPER_HEX_DIGITS[(cp & 3) | 8];
            cp >>>= 2;
            dest[8] = UPPER_HEX_DIGITS[cp & 15];
            cp >>>= 4;
            dest[7] = UPPER_HEX_DIGITS[(cp & 3) | 8];
            cp >>>= 2;
            dest[5] = UPPER_HEX_DIGITS[cp & 15];
            cp >>>= 4;
            dest[4] = UPPER_HEX_DIGITS[(cp & 3) | 8];
            dest[2] = UPPER_HEX_DIGITS[(cp >>> 2) & 7];
            return dest;
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Invalid unicode character value ");
            stringBuilder.append(cp);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
    }
}
