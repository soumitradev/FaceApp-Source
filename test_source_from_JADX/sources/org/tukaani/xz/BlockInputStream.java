package org.tukaani.xz;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import org.tukaani.xz.check.Check;
import org.tukaani.xz.common.DecoderUtil;

class BlockInputStream extends InputStream {
    private final Check check;
    private long compressedSizeInHeader = -1;
    private long compressedSizeLimit;
    private boolean endReached = false;
    private InputStream filterChain;
    private final int headerSize;
    private final CountingInputStream inCounted;
    private final DataInputStream inData;
    private final byte[] tempBuf = new byte[1];
    private long uncompressedSize = 0;
    private long uncompressedSizeInHeader = -1;

    public BlockInputStream(InputStream inputStream, Check check, int i, long j, long j2) throws IOException, IndexIndicatorException {
        InputStream inputStream2 = inputStream;
        int i2 = i;
        long j3 = j2;
        this.check = check;
        this.inData = new DataInputStream(inputStream2);
        byte[] bArr = new byte[1024];
        this.inData.readFully(bArr, 0, 1);
        if (bArr[0] == (byte) 0) {
            throw new IndexIndicatorException();
        }
        r1.headerSize = ((bArr[0] & 255) + 1) * 4;
        r1.inData.readFully(bArr, 1, r1.headerSize - 1);
        if (!DecoderUtil.isCRC32Valid(bArr, 0, r1.headerSize - 4, r1.headerSize - 4)) {
            throw new CorruptedInputException("XZ Block Header is corrupt");
        } else if ((bArr[1] & 60) != 0) {
            throw new UnsupportedOptionsException("Unsupported options in XZ Block Header");
        } else {
            int i3 = (bArr[1] & 3) + 1;
            long[] jArr = new long[i3];
            byte[][] bArr2 = new byte[i3][];
            InputStream byteArrayInputStream = new ByteArrayInputStream(bArr, 2, r1.headerSize - 6);
            try {
                long[] jArr2;
                int i4;
                r1.compressedSizeLimit = (9223372036854775804L - ((long) r1.headerSize)) - ((long) check.getSize());
                if ((bArr[1] & 64) != 0) {
                    r1.compressedSizeInHeader = DecoderUtil.decodeVLI(byteArrayInputStream);
                    if (r1.compressedSizeInHeader != 0) {
                        if (r1.compressedSizeInHeader <= r1.compressedSizeLimit) {
                            r1.compressedSizeLimit = r1.compressedSizeInHeader;
                        }
                    }
                    throw new CorruptedInputException();
                }
                if ((bArr[1] & 128) != 0) {
                    r1.uncompressedSizeInHeader = DecoderUtil.decodeVLI(byteArrayInputStream);
                }
                int i5 = 0;
                while (i5 < i3) {
                    jArr[i5] = DecoderUtil.decodeVLI(byteArrayInputStream);
                    j3 = DecoderUtil.decodeVLI(byteArrayInputStream);
                    int i6 = i3;
                    jArr2 = jArr;
                    if (j3 > ((long) byteArrayInputStream.available())) {
                        throw new CorruptedInputException();
                    }
                    bArr2[i5] = new byte[((int) j3)];
                    byteArrayInputStream.read(bArr2[i5]);
                    i5++;
                    i3 = i6;
                    jArr = jArr2;
                }
                jArr2 = jArr;
                for (i5 = byteArrayInputStream.available(); i5 > 0; i5--) {
                    if (byteArrayInputStream.read() != 0) {
                        throw new UnsupportedOptionsException("Unsupported options in XZ Block Header");
                    }
                }
                if (j != -1) {
                    long size = (long) (r1.headerSize + check.getSize());
                    if (size >= j) {
                        throw new CorruptedInputException("XZ Index does not match a Block Header");
                    }
                    j3 = j - size;
                    if (j3 <= r1.compressedSizeLimit) {
                        if (r1.compressedSizeInHeader == -1 || r1.compressedSizeInHeader == j3) {
                            long j4;
                            if (r1.uncompressedSizeInHeader != -1) {
                                j4 = j2;
                                if (r1.uncompressedSizeInHeader != j4) {
                                    throw new CorruptedInputException("XZ Index does not match a Block Header");
                                }
                            }
                            j4 = j2;
                            r1.compressedSizeLimit = j3;
                            r1.compressedSizeInHeader = j3;
                            r1.uncompressedSizeInHeader = j4;
                        }
                    }
                    throw new CorruptedInputException("XZ Index does not match a Block Header");
                }
                long[] jArr3 = jArr2;
                FilterCoder[] filterCoderArr = new FilterDecoder[jArr3.length];
                for (i4 = 0; i4 < filterCoderArr.length; i4++) {
                    if (jArr3[i4] == 33) {
                        filterCoderArr[i4] = new LZMA2Decoder(bArr2[i4]);
                    } else if (jArr3[i4] == 3) {
                        filterCoderArr[i4] = new DeltaDecoder(bArr2[i4]);
                    } else if (BCJCoder.isBCJFilterID(jArr3[i4])) {
                        filterCoderArr[i4] = new BCJDecoder(jArr3[i4], bArr2[i4]);
                    } else {
                        StringBuffer stringBuffer = new StringBuffer();
                        stringBuffer.append("Unknown Filter ID ");
                        stringBuffer.append(jArr3[i4]);
                        throw new UnsupportedOptionsException(stringBuffer.toString());
                    }
                }
                RawCoder.validate(filterCoderArr);
                i5 = i;
                if (i5 >= 0) {
                    int i7 = 0;
                    for (FilterDecoder memoryUsage : filterCoderArr) {
                        i7 += memoryUsage.getMemoryUsage();
                    }
                    if (i7 > i5) {
                        throw new MemoryLimitException(i7, i5);
                    }
                }
                r1.inCounted = new CountingInputStream(inputStream);
                r1.filterChain = r1.inCounted;
                for (i5 = filterCoderArr.length - 1; i5 >= 0; i5--) {
                    r1.filterChain = filterCoderArr[i5].getInputStream(r1.filterChain);
                }
            } catch (IOException e) {
                throw new CorruptedInputException("XZ Block Header is corrupt");
            }
        }
    }

    private void validate() throws IOException {
        long size = this.inCounted.getSize();
        if ((this.compressedSizeInHeader == -1 || this.compressedSizeInHeader == size) && (this.uncompressedSizeInHeader == -1 || this.uncompressedSizeInHeader == this.uncompressedSize)) {
            while (true) {
                long j = size + 1;
                if ((size & 3) == 0) {
                    break;
                } else if (this.inData.readUnsignedByte() != 0) {
                    throw new CorruptedInputException();
                } else {
                    size = j;
                }
            }
            byte[] bArr = new byte[this.check.getSize()];
            this.inData.readFully(bArr);
            if (!Arrays.equals(this.check.finish(), bArr)) {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("Integrity check (");
                stringBuffer.append(this.check.getName());
                stringBuffer.append(") does not match");
                throw new CorruptedInputException(stringBuffer.toString());
            }
            return;
        }
        throw new CorruptedInputException();
    }

    public int available() throws IOException {
        return this.filterChain.available();
    }

    public long getUncompressedSize() {
        return this.uncompressedSize;
    }

    public long getUnpaddedSize() {
        return (((long) this.headerSize) + this.inCounted.getSize()) + ((long) this.check.getSize());
    }

    public int read() throws IOException {
        return read(this.tempBuf, 0, 1) == -1 ? -1 : this.tempBuf[0] & 255;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int read(byte[] r9, int r10, int r11) throws java.io.IOException {
        /*
        r8 = this;
        r0 = r8.endReached;
        r1 = -1;
        if (r0 == 0) goto L_0x0006;
    L_0x0005:
        return r1;
    L_0x0006:
        r0 = r8.filterChain;
        r0 = r0.read(r9, r10, r11);
        r2 = 1;
        if (r0 <= 0) goto L_0x0062;
    L_0x000f:
        r3 = r8.check;
        r3.update(r9, r10, r0);
        r9 = r8.uncompressedSize;
        r3 = (long) r0;
        r5 = r9 + r3;
        r8.uncompressedSize = r5;
        r9 = r8.inCounted;
        r9 = r9.getSize();
        r3 = 0;
        r5 = (r9 > r3 ? 1 : (r9 == r3 ? 0 : -1));
        if (r5 < 0) goto L_0x005c;
    L_0x0027:
        r5 = r8.compressedSizeLimit;
        r7 = (r9 > r5 ? 1 : (r9 == r5 ? 0 : -1));
        if (r7 > 0) goto L_0x005c;
    L_0x002d:
        r9 = r8.uncompressedSize;
        r5 = (r9 > r3 ? 1 : (r9 == r3 ? 0 : -1));
        if (r5 < 0) goto L_0x005c;
    L_0x0033:
        r9 = r8.uncompressedSizeInHeader;
        r3 = -1;
        r5 = (r9 > r3 ? 1 : (r9 == r3 ? 0 : -1));
        if (r5 == 0) goto L_0x0044;
    L_0x003b:
        r9 = r8.uncompressedSize;
        r3 = r8.uncompressedSizeInHeader;
        r5 = (r9 > r3 ? 1 : (r9 == r3 ? 0 : -1));
        if (r5 <= 0) goto L_0x0044;
    L_0x0043:
        goto L_0x005c;
    L_0x0044:
        if (r0 < r11) goto L_0x004e;
    L_0x0046:
        r9 = r8.uncompressedSize;
        r3 = r8.uncompressedSizeInHeader;
        r11 = (r9 > r3 ? 1 : (r9 == r3 ? 0 : -1));
        if (r11 != 0) goto L_0x0069;
    L_0x004e:
        r9 = r8.filterChain;
        r9 = r9.read();
        if (r9 == r1) goto L_0x0064;
    L_0x0056:
        r9 = new org.tukaani.xz.CorruptedInputException;
        r9.<init>();
        throw r9;
    L_0x005c:
        r9 = new org.tukaani.xz.CorruptedInputException;
        r9.<init>();
        throw r9;
    L_0x0062:
        if (r0 != r1) goto L_0x0069;
    L_0x0064:
        r8.validate();
        r8.endReached = r2;
    L_0x0069:
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.tukaani.xz.BlockInputStream.read(byte[], int, int):int");
    }
}
