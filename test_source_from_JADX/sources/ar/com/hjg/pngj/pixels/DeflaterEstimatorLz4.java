package ar.com.hjg.pngj.pixels;

import android.support.v4.internal.view.SupportMenu;
import java.nio.ByteOrder;

public final class DeflaterEstimatorLz4 {
    static final int COPY_LENGTH = 8;
    static final int HASH_LOG = 12;
    static final int HASH_LOG_64K = 13;
    static final int HASH_LOG_HC = 15;
    static final int HASH_TABLE_SIZE = 4096;
    static final int HASH_TABLE_SIZE_64K = 8192;
    static final int HASH_TABLE_SIZE_HC = 32768;
    static final int LAST_LITERALS = 5;
    static final int LZ4_64K_LIMIT = 65547;
    static final int MAX_DISTANCE = 65536;
    static final int MEMORY_USAGE = 14;
    static final int MF_LIMIT = 12;
    static final int MIN_LENGTH = 13;
    static final int MIN_MATCH = 4;
    static final int ML_BITS = 4;
    static final int ML_MASK = 15;
    static final ByteOrder NATIVE_BYTE_ORDER = ByteOrder.nativeOrder();
    static final int NOT_COMPRESSIBLE_DETECTION_LEVEL = 6;
    static final int OPTIMAL_ML = 18;
    static final int RUN_BITS = 4;
    static final int RUN_MASK = 15;
    static final int SKIP_STRENGTH = Math.max(6, 2);

    public int compressEstim(byte[] src, int srcOff, int srcLen) {
        if (srcLen < 10) {
            return srcLen;
        }
        int segments = ((srcLen + 65546) - 1) / 65546;
        int stride = srcLen / segments;
        if (stride < 65546 && stride * segments <= srcLen && segments >= 1) {
            if (stride >= 1) {
                int bytesIn = 0;
                int bytesOut = 0;
                int srcOff2 = srcOff;
                srcOff = srcLen;
                while (srcOff > 0) {
                    if (srcOff > stride) {
                        srcOff = stride;
                    }
                    bytesOut += compress64k(src, srcOff2, srcOff);
                    srcOff2 += srcOff;
                    bytesIn += srcOff;
                    srcOff = srcLen - bytesIn;
                }
                return bytesIn == srcLen ? bytesOut : (int) ((((double) srcLen) * (((double) bytesOut) / ((double) bytesIn))) + 0.5d);
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("?? ");
        stringBuilder.append(srcLen);
        throw new RuntimeException(stringBuilder.toString());
    }

    public int compressEstim(byte[] src) {
        return compressEstim(src, 0, src.length);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static int compress64k(byte[] r18, int r19, int r20) {
        /*
        r0 = r18;
        r1 = r19;
        r2 = r20;
        r3 = r1 + r2;
        r4 = r3 + -5;
        r5 = r3 + -12;
        r6 = r1;
        r7 = 0;
        r8 = r6;
        r10 = 1;
        r11 = 13;
        if (r2 < r11) goto L_0x00c5;
    L_0x0014:
        r11 = 8192; // 0x2000 float:1.14794E-41 double:4.0474E-320;
        r11 = new short[r11];
        r6 = r6 + 1;
        r13 = r7;
        r12 = r8;
    L_0x001c:
        r7 = r6;
        r8 = SKIP_STRENGTH;
        r8 = r10 << r8;
        r8 = r8 + 3;
    L_0x0023:
        r6 = r7;
        r14 = r8 + 1;
        r15 = SKIP_STRENGTH;
        r8 = r8 >>> r15;
        r7 = r7 + r8;
        if (r7 <= r5) goto L_0x0031;
    L_0x002d:
        r17 = r4;
        goto L_0x00c9;
    L_0x0031:
        r8 = readInt(r0, r6);
        r8 = hash64k(r8);
        r15 = readShort(r11, r8);
        r15 = r15 + r1;
        r9 = r6 - r1;
        writeShort(r11, r8, r9);
        r8 = readIntEquals(r0, r15, r6);
        if (r8 == 0) goto L_0x00c2;
    L_0x0049:
        r8 = commonBytesBackward(r0, r15, r6, r1, r12);
        r6 = r6 - r8;
        r15 = r15 - r8;
        r9 = r6 - r12;
        r13 = r13 + 1;
        r10 = 15;
        if (r9 < r10) goto L_0x0060;
    L_0x0057:
        if (r9 <= r10) goto L_0x005e;
    L_0x0059:
        r10 = r9 + -15;
        r10 = r10 / 255;
        r13 = r13 + r10;
    L_0x005e:
        r10 = 1;
        r13 = r13 + r10;
    L_0x0060:
        r13 = r13 + r9;
    L_0x0061:
        r13 = r13 + 2;
        r6 = r6 + 4;
        r15 = r15 + 4;
        r10 = commonBytes(r0, r15, r6, r4);
        r6 = r6 + r10;
        r2 = 15;
        if (r10 < r2) goto L_0x007b;
    L_0x0070:
        r2 = 270; // 0x10e float:3.78E-43 double:1.334E-321;
        if (r10 < r2) goto L_0x0079;
    L_0x0074:
        r2 = r10 + -15;
        r2 = r2 / 255;
        r13 = r13 + r2;
    L_0x0079:
        r2 = 1;
        r13 = r13 + r2;
    L_0x007b:
        if (r6 <= r5) goto L_0x0081;
    L_0x007d:
        r2 = r6;
        r12 = r2;
        goto L_0x002d;
    L_0x0081:
        r2 = r6 + -2;
        r2 = readInt(r0, r2);
        r2 = hash64k(r2);
        r16 = r6 + -2;
        r17 = r4;
        r4 = r16 - r1;
        writeShort(r11, r2, r4);
        r2 = readInt(r0, r6);
        r2 = hash64k(r2);
        r4 = readShort(r11, r2);
        r15 = r1 + r4;
        r4 = r6 - r1;
        writeShort(r11, r2, r4);
        r4 = readIntEquals(r0, r6, r15);
        if (r4 != 0) goto L_0x00ba;
        r2 = r6 + 1;
        r12 = r6;
        r6 = r2;
        r4 = r17;
        r2 = r20;
        r10 = 1;
        goto L_0x001c;
    L_0x00ba:
        r13 = r13 + 1;
        r4 = r17;
        r2 = r20;
        goto L_0x0061;
    L_0x00c2:
        r8 = r14;
        goto L_0x0023;
    L_0x00c5:
        r17 = r4;
        r13 = r7;
        r12 = r8;
    L_0x00c9:
        r2 = r3 - r12;
        r4 = 270; // 0x10e float:3.78E-43 double:1.334E-321;
        if (r2 < r4) goto L_0x00d4;
    L_0x00cf:
        r4 = r2 + -15;
        r4 = r4 / 255;
        r13 = r13 + r4;
    L_0x00d4:
        r4 = 1;
        r13 = r13 + r4;
        r13 = r13 + r2;
        return r13;
        */
        throw new UnsupportedOperationException("Method not decompiled: ar.com.hjg.pngj.pixels.DeflaterEstimatorLz4.compress64k(byte[], int, int):int");
    }

    static final int maxCompressedLength(int length) {
        if (length >= 0) {
            return ((length / 255) + length) + 16;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("length must be >= 0, got ");
        stringBuilder.append(length);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    static int hash(int i) {
        return (-1640531535 * i) >>> 20;
    }

    static int hash64k(int i) {
        return (-1640531535 * i) >>> 19;
    }

    static int readShortLittleEndian(byte[] buf, int i) {
        return (buf[i] & 255) | ((buf[i + 1] & 255) << 8);
    }

    static boolean readIntEquals(byte[] buf, int i, int j) {
        return buf[i] == buf[j] && buf[i + 1] == buf[j + 1] && buf[i + 2] == buf[j + 2] && buf[i + 3] == buf[j + 3];
    }

    static int commonBytes(byte[] b, int o1, int o2, int limit) {
        int count = 0;
        while (o2 < limit) {
            int o12 = o1 + 1;
            int o22 = o2 + 1;
            if (b[o1] != b[o2]) {
                o1 = o12;
                o2 = o22;
                break;
            }
            count++;
            o1 = o12;
            o2 = o22;
        }
        return count;
    }

    static int commonBytesBackward(byte[] b, int o1, int o2, int l1, int l2) {
        int count = 0;
        while (o1 > l1 && o2 > l2) {
            o1--;
            o2--;
            if (b[o1] != b[o2]) {
                break;
            }
            count++;
        }
        return count;
    }

    static int readShort(short[] buf, int off) {
        return buf[off] & SupportMenu.USER_MASK;
    }

    static byte readByte(byte[] buf, int i) {
        return buf[i];
    }

    static void checkRange(byte[] buf, int off) {
        if (off >= 0) {
            if (off < buf.length) {
                return;
            }
        }
        throw new ArrayIndexOutOfBoundsException(off);
    }

    static void checkRange(byte[] buf, int off, int len) {
        checkLength(len);
        if (len > 0) {
            checkRange(buf, off);
            checkRange(buf, (off + len) - 1);
        }
    }

    static void checkLength(int len) {
        if (len < 0) {
            throw new IllegalArgumentException("lengths must be >= 0");
        }
    }

    static int readIntBE(byte[] buf, int i) {
        return ((((buf[i] & 255) << 24) | ((buf[i + 1] & 255) << 16)) | ((buf[i + 2] & 255) << 8)) | (buf[i + 3] & 255);
    }

    static int readIntLE(byte[] buf, int i) {
        return (((buf[i] & 255) | ((buf[i + 1] & 255) << 8)) | ((buf[i + 2] & 255) << 16)) | ((buf[i + 3] & 255) << 24);
    }

    static int readInt(byte[] buf, int i) {
        if (NATIVE_BYTE_ORDER == ByteOrder.BIG_ENDIAN) {
            return readIntBE(buf, i);
        }
        return readIntLE(buf, i);
    }

    static void writeShort(short[] buf, int off, int v) {
        buf[off] = (short) v;
    }
}
