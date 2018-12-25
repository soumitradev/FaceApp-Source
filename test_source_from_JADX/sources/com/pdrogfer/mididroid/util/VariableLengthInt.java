package com.pdrogfer.mididroid.util;

import com.pdrogfer.mididroid.event.meta.MetaEvent;
import java.io.IOException;
import java.io.InputStream;

public class VariableLengthInt {
    private byte[] mBytes;
    private int mSizeInBytes;
    private int mValue;

    public VariableLengthInt(int value) {
        setValue(value);
    }

    public VariableLengthInt(InputStream in) throws IOException {
        parseBytes(in);
    }

    public void setValue(int value) {
        this.mValue = value;
        buildBytes();
    }

    public int getValue() {
        return this.mValue;
    }

    public int getByteCount() {
        return this.mSizeInBytes;
    }

    public byte[] getBytes() {
        return this.mBytes;
    }

    private void parseBytes(InputStream in) throws IOException {
        int[] ints = new int[4];
        int i = 0;
        this.mSizeInBytes = 0;
        this.mValue = 0;
        int shift = 0;
        int b = in.read();
        while (true) {
            int i2 = 1;
            if (this.mSizeInBytes >= 4) {
                break;
            }
            this.mSizeInBytes++;
            if (!((b & 128) > 0)) {
                break;
            }
            ints[this.mSizeInBytes - 1] = b & MetaEvent.SEQUENCER_SPECIFIC;
            b = in.read();
        }
        ints[this.mSizeInBytes - 1] = b & MetaEvent.SEQUENCER_SPECIFIC;
        while (true) {
            int i3 = i2;
            if (i3 >= this.mSizeInBytes) {
                break;
            }
            shift += 7;
            i2 = i3 + 1;
        }
        this.mBytes = new byte[this.mSizeInBytes];
        while (true) {
            i3 = i;
            if (i3 < this.mSizeInBytes) {
                this.mBytes[i3] = (byte) ints[i3];
                this.mValue += ints[i3] << shift;
                shift -= 7;
                i = i3 + 1;
            } else {
                return;
            }
        }
    }

    private void buildBytes() {
        int i = 0;
        if (this.mValue == 0) {
            this.mBytes = new byte[1];
            this.mBytes[0] = (byte) 0;
            this.mSizeInBytes = 1;
            return;
        }
        int i2;
        this.mSizeInBytes = 0;
        int[] vals = new int[4];
        int tmpVal = this.mValue;
        while (this.mSizeInBytes < 4 && tmpVal > 0) {
            vals[this.mSizeInBytes] = tmpVal & MetaEvent.SEQUENCER_SPECIFIC;
            this.mSizeInBytes++;
            tmpVal >>= 7;
        }
        for (i2 = 1; i2 < this.mSizeInBytes; i2++) {
            vals[i2] = vals[i2] | 128;
        }
        this.mBytes = new byte[this.mSizeInBytes];
        while (true) {
            i2 = i;
            if (i2 < this.mSizeInBytes) {
                this.mBytes[i2] = (byte) vals[(this.mSizeInBytes - i2) - 1];
                i = i2 + 1;
            } else {
                return;
            }
        }
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(MidiUtil.bytesToHex(this.mBytes));
        stringBuilder.append(" (");
        stringBuilder.append(this.mValue);
        stringBuilder.append(")");
        return stringBuilder.toString();
    }
}
