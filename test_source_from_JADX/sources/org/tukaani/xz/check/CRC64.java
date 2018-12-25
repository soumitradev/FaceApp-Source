package org.tukaani.xz.check;

public class CRC64 extends Check {
    private static final long[] crcTable = new long[256];
    private static final long poly = -3932672073523589310L;
    private long crc;

    static {
        for (int i = 0; i < crcTable.length; i++) {
            long j = (long) i;
            for (int i2 = 0; i2 < 8; i2++) {
                j = (j & 1) == 1 ? (j >>> 1) ^ poly : j >>> 1;
            }
            crcTable[i] = j;
        }
    }

    public CRC64() {
        this.crc = -1;
        this.size = 8;
        this.name = "CRC64";
    }

    public byte[] finish() {
        long j = this.crc ^ -1;
        this.crc = -1;
        byte[] bArr = new byte[8];
        for (int i = 0; i < bArr.length; i++) {
            bArr[i] = (byte) ((int) (j >> (i * 8)));
        }
        return bArr;
    }

    public void update(byte[] bArr, int i, int i2) {
        i2 += i;
        while (i < i2) {
            int i3 = i + 1;
            this.crc = crcTable[(bArr[i] ^ ((int) this.crc)) & 255] ^ (this.crc >>> 8);
            i = i3;
        }
    }
}
