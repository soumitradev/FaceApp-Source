package org.tukaani.xz.check;

import java.security.NoSuchAlgorithmException;
import org.tukaani.xz.UnsupportedOptionsException;

public abstract class Check {
    String name;
    int size;

    public static Check getInstance(int i) throws UnsupportedOptionsException {
        if (i == 4) {
            return new CRC64();
        }
        if (i != 10) {
            switch (i) {
                case 0:
                    return new None();
                case 1:
                    return new CRC32();
                default:
                    break;
            }
        }
        try {
            return new SHA256();
        } catch (NoSuchAlgorithmException e) {
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Unsupported Check ID ");
        stringBuffer.append(i);
        throw new UnsupportedOptionsException(stringBuffer.toString());
    }

    public abstract byte[] finish();

    public String getName() {
        return this.name;
    }

    public int getSize() {
        return this.size;
    }

    public void update(byte[] bArr) {
        update(bArr, 0, bArr.length);
    }

    public abstract void update(byte[] bArr, int i, int i2);
}
