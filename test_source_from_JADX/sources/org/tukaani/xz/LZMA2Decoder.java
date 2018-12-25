package org.tukaani.xz;

import java.io.InputStream;

class LZMA2Decoder extends LZMA2Coder implements FilterDecoder {
    private int dictSize;

    LZMA2Decoder(byte[] bArr) throws UnsupportedOptionsException {
        if (bArr.length == 1) {
            if ((bArr[0] & 255) <= 37) {
                this.dictSize = (bArr[0] & 1) | 2;
                this.dictSize <<= (bArr[0] >>> 1) + 11;
                return;
            }
        }
        throw new UnsupportedOptionsException("Unsupported LZMA2 properties");
    }

    public InputStream getInputStream(InputStream inputStream) {
        return new LZMA2InputStream(inputStream, this.dictSize);
    }

    public int getMemoryUsage() {
        return LZMA2InputStream.getMemoryUsage(this.dictSize);
    }
}
