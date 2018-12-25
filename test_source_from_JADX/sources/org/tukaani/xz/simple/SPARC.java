package org.tukaani.xz.simple;

import com.google.common.primitives.Ints;
import name.antonsmirnov.firmata.writer.ReportAnalogPinMessageWriter;

public final class SPARC implements SimpleFilter {
    private final boolean isEncoder;
    private int pos;

    public SPARC(boolean z, int i) {
        this.isEncoder = z;
        this.pos = i;
    }

    public int code(byte[] bArr, int i, int i2) {
        i2 = (i2 + i) - 4;
        int i3 = i;
        while (i3 <= i2) {
            if ((bArr[i3] == (byte) 64 && (bArr[i3 + 1] & ReportAnalogPinMessageWriter.COMMAND) == 0) || (bArr[i3] == Byte.MAX_VALUE && (bArr[i3 + 1] & ReportAnalogPinMessageWriter.COMMAND) == ReportAnalogPinMessageWriter.COMMAND)) {
                int i4 = i3 + 1;
                int i5 = i3 + 2;
                int i6 = i3 + 3;
                int i7 = (((((bArr[i3] & 255) << 24) | ((bArr[i4] & 255) << 16)) | ((bArr[i5] & 255) << 8)) | (bArr[i6] & 255)) << 2;
                i7 = (this.isEncoder ? i7 + ((this.pos + i3) - i) : i7 - ((this.pos + i3) - i)) >>> 2;
                i7 = ((i7 & 4194303) | (((0 - ((i7 >>> 22) & 1)) << 22) & 1073741823)) | Ints.MAX_POWER_OF_TWO;
                bArr[i3] = (byte) (i7 >>> 24);
                bArr[i4] = (byte) (i7 >>> 16);
                bArr[i5] = (byte) (i7 >>> 8);
                bArr[i6] = (byte) i7;
            }
            i3 += 4;
        }
        i3 -= i;
        this.pos += i3;
        return i3;
    }
}
