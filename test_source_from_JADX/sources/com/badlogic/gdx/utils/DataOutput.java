package com.badlogic.gdx.utils;

import com.pdrogfer.mididroid.event.meta.MetaEvent;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import name.antonsmirnov.firmata.writer.AnalogMessageWriter;
import name.antonsmirnov.firmata.writer.ReportAnalogPinMessageWriter;

public class DataOutput extends DataOutputStream {
    public DataOutput(OutputStream out) {
        super(out);
    }

    public int writeInt(int value, boolean optimizePositive) throws IOException {
        if (!optimizePositive) {
            value = (value << 1) ^ (value >> 31);
        }
        if ((value >>> 7) == 0) {
            write((byte) value);
            return 1;
        }
        write((byte) ((value & MetaEvent.SEQUENCER_SPECIFIC) | 128));
        if ((value >>> 14) == 0) {
            write((byte) (value >>> 7));
            return 2;
        }
        write((byte) ((value >>> 7) | 128));
        if ((value >>> 21) == 0) {
            write((byte) (value >>> 14));
            return 3;
        }
        write((byte) ((value >>> 14) | 128));
        if ((value >>> 28) == 0) {
            write((byte) (value >>> 21));
            return 4;
        }
        write((byte) ((value >>> 21) | 128));
        write((byte) (value >>> 28));
        return 5;
    }

    public void writeString(String value) throws IOException {
        int charIndex = 0;
        if (value == null) {
            write(0);
            return;
        }
        int charCount = value.length();
        if (charCount == 0) {
            writeByte(1);
            return;
        }
        writeInt(charCount + 1, true);
        while (charIndex < charCount) {
            int c = value.charAt(charIndex);
            if (c > MetaEvent.SEQUENCER_SPECIFIC) {
                break;
            }
            write((byte) c);
            charIndex++;
        }
        if (charIndex < charCount) {
            writeString_slow(value, charCount, charIndex);
        }
    }

    private void writeString_slow(String value, int charCount, int charIndex) throws IOException {
        while (charIndex < charCount) {
            int c = value.charAt(charIndex);
            if (c <= MetaEvent.SEQUENCER_SPECIFIC) {
                write((byte) c);
            } else if (c > 2047) {
                write((byte) (((c >> 12) & 15) | AnalogMessageWriter.COMMAND));
                write((byte) (((c >> 6) & 63) | 128));
                write((byte) ((c & 63) | 128));
            } else {
                write((byte) (((c >> 6) & 31) | ReportAnalogPinMessageWriter.COMMAND));
                write((byte) ((c & 63) | 128));
            }
            charIndex++;
        }
    }
}
