package org.tukaani.xz.delta;

public class DeltaDecoder extends DeltaCoder {
    public DeltaDecoder(int i) {
        super(i);
    }

    public void decode(byte[] bArr, int i, int i2) {
        i2 += i;
        while (i < i2) {
            bArr[i] = (byte) (bArr[i] + this.history[(this.distance + this.pos) & 255]);
            byte[] bArr2 = this.history;
            int i3 = this.pos;
            this.pos = i3 - 1;
            bArr2[i3 & 255] = bArr[i];
            i++;
        }
    }
}
