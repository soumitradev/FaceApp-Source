package org.tukaani.xz.simple;

import com.badlogic.gdx.Input.Keys;
import name.antonsmirnov.firmata.writer.SysexMessageWriter;

public final class ARMThumb implements SimpleFilter {
    private final boolean isEncoder;
    private int pos;

    public ARMThumb(boolean z, int i) {
        this.isEncoder = z;
        this.pos = i + 4;
    }

    public int code(byte[] bArr, int i, int i2) {
        i2 = (i2 + i) - 4;
        int i3 = i;
        while (i3 <= i2) {
            int i4 = i3 + 1;
            if ((bArr[i4] & Keys.F5) == SysexMessageWriter.COMMAND_START) {
                int i5 = i3 + 3;
                if ((bArr[i5] & Keys.F5) == Keys.F5) {
                    int i6 = i3 + 2;
                    int i7 = (((((bArr[i4] & 7) << 19) | ((bArr[i3] & 255) << 11)) | ((bArr[i5] & 7) << 8)) | (bArr[i6] & 255)) << 1;
                    i7 = (this.isEncoder ? i7 + ((this.pos + i3) - i) : i7 - ((this.pos + i3) - i)) >>> 1;
                    bArr[i4] = (byte) (SysexMessageWriter.COMMAND_START | ((i7 >>> 19) & 7));
                    bArr[i3] = (byte) (i7 >>> 11);
                    bArr[i5] = (byte) (((i7 >>> 8) & 7) | Keys.F5);
                    bArr[i6] = (byte) i7;
                    i3 = i6;
                }
            }
            i3 += 2;
        }
        i3 -= i;
        this.pos += i3;
        return i3;
    }
}
