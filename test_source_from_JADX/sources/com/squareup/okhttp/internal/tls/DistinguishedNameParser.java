package com.squareup.okhttp.internal.tls;

import javax.security.auth.x500.X500Principal;
import kotlin.text.Typography;
import name.antonsmirnov.firmata.writer.ReportAnalogPinMessageWriter;
import org.catrobat.catroid.common.Constants;

final class DistinguishedNameParser {
    private int beg;
    private char[] chars;
    private int cur;
    private final String dn;
    private int end;
    private final int length = this.dn.length();
    private int pos;

    public DistinguishedNameParser(X500Principal principal) {
        this.dn = principal.getName("RFC2253");
    }

    private String nextAT() {
        while (this.pos < this.length && this.chars[this.pos] == ' ') {
            this.pos++;
        }
        if (this.pos == this.length) {
            return null;
        }
        this.beg = this.pos;
        this.pos++;
        while (this.pos < this.length && this.chars[this.pos] != '=' && this.chars[this.pos] != ' ') {
            this.pos++;
        }
        if (this.pos >= this.length) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unexpected end of DN: ");
            stringBuilder.append(this.dn);
            throw new IllegalStateException(stringBuilder.toString());
        }
        this.end = this.pos;
        if (this.chars[this.pos] == ' ') {
            while (this.pos < this.length && this.chars[this.pos] != '=' && this.chars[this.pos] == ' ') {
                this.pos++;
            }
            if (this.chars[this.pos] != '=' || this.pos == this.length) {
                stringBuilder = new StringBuilder();
                stringBuilder.append("Unexpected end of DN: ");
                stringBuilder.append(this.dn);
                throw new IllegalStateException(stringBuilder.toString());
            }
        }
        this.pos++;
        while (this.pos < this.length && this.chars[this.pos] == ' ') {
            this.pos++;
        }
        if (this.end - this.beg > 4 && this.chars[this.beg + 3] == '.' && ((this.chars[this.beg] == 'O' || this.chars[this.beg] == 'o') && ((this.chars[this.beg + 1] == 'I' || this.chars[this.beg + 1] == 'i') && (this.chars[this.beg + 2] == 'D' || this.chars[this.beg + 2] == 'd')))) {
            this.beg += 4;
        }
        return new String(this.chars, this.beg, this.end - this.beg);
    }

    private String quotedAV() {
        this.pos++;
        this.beg = this.pos;
        this.end = this.beg;
        while (this.pos != this.length) {
            if (this.chars[this.pos] == Typography.quote) {
                this.pos++;
                while (this.pos < this.length && this.chars[this.pos] == ' ') {
                    this.pos++;
                }
                return new String(this.chars, this.beg, this.end - this.beg);
            }
            if (this.chars[this.pos] == '\\') {
                this.chars[this.end] = getEscaped();
            } else {
                this.chars[this.end] = this.chars[this.pos];
            }
            this.pos++;
            this.end++;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unexpected end of DN: ");
        stringBuilder.append(this.dn);
        throw new IllegalStateException(stringBuilder.toString());
    }

    private String hexAV() {
        if (this.pos + 4 >= this.length) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unexpected end of DN: ");
            stringBuilder.append(this.dn);
            throw new IllegalStateException(stringBuilder.toString());
        }
        int hexLen;
        byte[] encoded;
        int i;
        StringBuilder stringBuilder2;
        this.beg = this.pos;
        this.pos++;
        while (this.pos != this.length && this.chars[this.pos] != '+' && this.chars[this.pos] != Constants.REMIX_URL_SEPARATOR) {
            int p;
            if (this.chars[this.pos] == Constants.REMIX_URL_REPLACE_SEPARATOR) {
                break;
            } else if (this.chars[this.pos] == ' ') {
                this.end = this.pos;
                this.pos++;
                while (this.pos < this.length && this.chars[this.pos] == ' ') {
                    this.pos++;
                }
                hexLen = this.end - this.beg;
                if (hexLen >= 5) {
                    if ((hexLen & 1) == 0) {
                        encoded = new byte[(hexLen / 2)];
                        p = this.beg + 1;
                        for (i = 0; i < encoded.length; i++) {
                            encoded[i] = (byte) getByte(p);
                            p += 2;
                        }
                        return new String(this.chars, this.beg, hexLen);
                    }
                }
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append("Unexpected end of DN: ");
                stringBuilder2.append(this.dn);
                throw new IllegalStateException(stringBuilder2.toString());
            } else {
                if (this.chars[this.pos] >= 'A' && this.chars[this.pos] <= 'F') {
                    char[] cArr = this.chars;
                    i = this.pos;
                    cArr[i] = (char) (cArr[i] + 32);
                }
                this.pos++;
            }
        }
        this.end = this.pos;
        hexLen = this.end - this.beg;
        if (hexLen >= 5) {
            if ((hexLen & 1) == 0) {
                encoded = new byte[(hexLen / 2)];
                p = this.beg + 1;
                for (i = 0; i < encoded.length; i++) {
                    encoded[i] = (byte) getByte(p);
                    p += 2;
                }
                return new String(this.chars, this.beg, hexLen);
            }
        }
        stringBuilder2 = new StringBuilder();
        stringBuilder2.append("Unexpected end of DN: ");
        stringBuilder2.append(this.dn);
        throw new IllegalStateException(stringBuilder2.toString());
    }

    private String escapedAV() {
        this.beg = this.pos;
        this.end = this.pos;
        while (this.pos < this.length) {
            char c = this.chars[this.pos];
            char[] cArr;
            if (c != ' ') {
                if (c != Constants.REMIX_URL_REPLACE_SEPARATOR) {
                    int i;
                    if (c != '\\') {
                        switch (c) {
                            case '+':
                            case ',':
                                break;
                            default:
                                cArr = this.chars;
                                i = this.end;
                                this.end = i + 1;
                                cArr[i] = this.chars[this.pos];
                                this.pos++;
                                continue;
                        }
                    } else {
                        cArr = this.chars;
                        i = this.end;
                        this.end = i + 1;
                        cArr[i] = getEscaped();
                        this.pos++;
                    }
                }
                return new String(this.chars, this.beg, this.end - this.beg);
            }
            this.cur = this.end;
            this.pos++;
            cArr = this.chars;
            int i2 = this.end;
            this.end = i2 + 1;
            cArr[i2] = ' ';
            while (this.pos < this.length && this.chars[this.pos] == ' ') {
                cArr = this.chars;
                i2 = this.end;
                this.end = i2 + 1;
                cArr[i2] = ' ';
                this.pos++;
            }
            if (this.pos == this.length || this.chars[this.pos] == Constants.REMIX_URL_SEPARATOR || this.chars[this.pos] == '+' || this.chars[this.pos] == Constants.REMIX_URL_REPLACE_SEPARATOR) {
                return new String(this.chars, this.beg, this.cur - this.beg);
            }
        }
        return new String(this.chars, this.beg, this.end - this.beg);
    }

    private char getEscaped() {
        this.pos++;
        if (this.pos == this.length) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unexpected end of DN: ");
            stringBuilder.append(this.dn);
            throw new IllegalStateException(stringBuilder.toString());
        }
        char c = this.chars[this.pos];
        if (!(c == ' ' || c == '%' || c == '\\' || c == '_')) {
            switch (c) {
                case '\"':
                case '#':
                    break;
                default:
                    switch (c) {
                        case '*':
                        case '+':
                        case ',':
                            break;
                        default:
                            switch (c) {
                                case ';':
                                case '<':
                                case '=':
                                case '>':
                                    break;
                                default:
                                    return getUTF8();
                            }
                    }
            }
        }
        return this.chars[this.pos];
    }

    private char getUTF8() {
        int res = getByte(this.pos);
        this.pos++;
        if (res < 128) {
            return (char) res;
        }
        if (res < ReportAnalogPinMessageWriter.COMMAND || res > 247) {
            return '?';
        }
        int count;
        if (res <= 223) {
            count = 1;
            res &= 31;
        } else if (res <= 239) {
            count = 2;
            res &= 15;
        } else {
            count = 3;
            res &= 7;
        }
        int i = 0;
        while (i < count) {
            this.pos++;
            if (this.pos != this.length) {
                if (this.chars[this.pos] == '\\') {
                    this.pos++;
                    int b = getByte(this.pos);
                    this.pos++;
                    if ((b & ReportAnalogPinMessageWriter.COMMAND) != 128) {
                        return '?';
                    }
                    res = (res << 6) + (b & 63);
                    i++;
                }
            }
            return '?';
        }
        return (char) res;
    }

    private int getByte(int position) {
        if (position + 1 >= this.length) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Malformed DN: ");
            stringBuilder.append(this.dn);
            throw new IllegalStateException(stringBuilder.toString());
        }
        int b1 = this.chars[position];
        if (b1 >= 48 && b1 <= 57) {
            b1 -= 48;
        } else if (b1 >= 97 && b1 <= 102) {
            b1 -= 87;
        } else if (b1 < 65 || b1 > 70) {
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("Malformed DN: ");
            stringBuilder2.append(this.dn);
            throw new IllegalStateException(stringBuilder2.toString());
        } else {
            b1 -= 55;
        }
        int b2 = this.chars[position + 1];
        if (b2 >= 48 && b2 <= 57) {
            b2 -= 48;
        } else if (b2 >= 97 && b2 <= 102) {
            b2 -= 87;
        } else if (b2 < 65 || b2 > 70) {
            stringBuilder2 = new StringBuilder();
            stringBuilder2.append("Malformed DN: ");
            stringBuilder2.append(this.dn);
            throw new IllegalStateException(stringBuilder2.toString());
        } else {
            b2 -= 55;
        }
        return (b1 << 4) + b2;
    }

    public String findMostSpecific(String attributeType) {
        this.pos = 0;
        this.beg = 0;
        this.end = 0;
        this.cur = 0;
        this.chars = this.dn.toCharArray();
        String attType = nextAT();
        if (attType == null) {
            return null;
        }
        while (true) {
            String attValue = "";
            if (this.pos == this.length) {
                return null;
            }
            switch (this.chars[this.pos]) {
                case '\"':
                    attValue = quotedAV();
                    break;
                case '#':
                    attValue = hexAV();
                    break;
                case '+':
                case ',':
                case ';':
                    break;
                default:
                    attValue = escapedAV();
                    break;
            }
            if (attributeType.equalsIgnoreCase(attType)) {
                return attValue;
            }
            if (this.pos >= this.length) {
                return null;
            }
            if (this.chars[this.pos] != Constants.REMIX_URL_SEPARATOR) {
                if (this.chars[this.pos] != Constants.REMIX_URL_REPLACE_SEPARATOR) {
                    if (this.chars[this.pos] != '+') {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Malformed DN: ");
                        stringBuilder.append(this.dn);
                        throw new IllegalStateException(stringBuilder.toString());
                    }
                }
            }
            this.pos++;
            attType = nextAT();
            if (attType == null) {
                stringBuilder = new StringBuilder();
                stringBuilder.append("Malformed DN: ");
                stringBuilder.append(this.dn);
                throw new IllegalStateException(stringBuilder.toString());
            }
        }
    }
}
