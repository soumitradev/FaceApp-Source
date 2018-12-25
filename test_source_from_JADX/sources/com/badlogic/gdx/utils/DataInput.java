package com.badlogic.gdx.utils;

import com.pdrogfer.mididroid.event.meta.MetaEvent;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

public class DataInput extends DataInputStream {
    private char[] chars = new char[32];

    public DataInput(InputStream in) {
        super(in);
    }

    public int readInt(boolean optimizePositive) throws IOException {
        int b = read();
        int result = b & MetaEvent.SEQUENCER_SPECIFIC;
        if ((b & 128) != 0) {
            b = read();
            result |= (b & MetaEvent.SEQUENCER_SPECIFIC) << 7;
            if ((b & 128) != 0) {
                b = read();
                result |= (b & MetaEvent.SEQUENCER_SPECIFIC) << 14;
                if ((b & 128) != 0) {
                    b = read();
                    result |= (b & MetaEvent.SEQUENCER_SPECIFIC) << 21;
                    if ((b & 128) != 0) {
                        result |= (read() & MetaEvent.SEQUENCER_SPECIFIC) << 28;
                    }
                }
            }
        }
        return optimizePositive ? result : (result >>> 1) ^ (-(result & 1));
    }

    public String readString() throws IOException {
        int charCount = readInt(1);
        switch (charCount) {
            case 0:
                return null;
            case 1:
                return "";
            default:
                charCount--;
                if (this.chars.length < charCount) {
                    this.chars = new char[charCount];
                }
                char[] chars = this.chars;
                int charIndex = 0;
                int b = 0;
                while (charIndex < charCount) {
                    b = read();
                    if (b > MetaEvent.SEQUENCER_SPECIFIC) {
                        if (charIndex < charCount) {
                            readUtf8_slow(charCount, charIndex, b);
                        }
                        return new String(chars, 0, charCount);
                    }
                    int charIndex2 = charIndex + 1;
                    chars[charIndex] = (char) b;
                    charIndex = charIndex2;
                }
                if (charIndex < charCount) {
                    readUtf8_slow(charCount, charIndex, b);
                }
                return new String(chars, 0, charCount);
        }
    }

    private void readUtf8_slow(int charCount, int charIndex, int b) throws IOException {
        char[] chars = this.chars;
        while (true) {
            int i = b >> 4;
            switch (i) {
                case 0:
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                    chars[charIndex] = (char) b;
                    break;
                default:
                    switch (i) {
                        case 12:
                        case 13:
                            chars[charIndex] = (char) (((b & 31) << 6) | (read() & 63));
                            break;
                        case 14:
                            chars[charIndex] = (char) ((((b & 15) << 12) | ((read() & 63) << 6)) | (read() & 63));
                            break;
                        default:
                            break;
                    }
            }
            charIndex++;
            if (charIndex < charCount) {
                b = read() & 255;
            } else {
                return;
            }
        }
    }
}
