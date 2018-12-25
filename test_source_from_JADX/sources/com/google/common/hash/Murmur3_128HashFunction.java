package com.google.common.hash;

import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import javax.annotation.Nullable;

final class Murmur3_128HashFunction extends AbstractStreamingHashFunction implements Serializable {
    private static final long serialVersionUID = 0;
    private final int seed;

    private static final class Murmur3_128Hasher extends AbstractStreamingHasher {
        private static final long C1 = -8663945395140668459L;
        private static final long C2 = 5545529020109919103L;
        private static final int CHUNK_SIZE = 16;
        private long h1;
        private long h2;
        private int length = 0;

        Murmur3_128Hasher(int seed) {
            super(16);
            this.h1 = (long) seed;
            this.h2 = (long) seed;
        }

        protected void process(ByteBuffer bb) {
            bmix64(bb.getLong(), bb.getLong());
            this.length += 16;
        }

        private void bmix64(long k1, long k2) {
            this.h1 ^= mixK1(k1);
            this.h1 = Long.rotateLeft(this.h1, 27);
            this.h1 += this.h2;
            this.h1 = (this.h1 * 5) + 1390208809;
            this.h2 ^= mixK2(k2);
            this.h2 = Long.rotateLeft(this.h2, 31);
            this.h2 += this.h1;
            this.h2 = (this.h2 * 5) + 944331445;
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        protected void processRemaining(java.nio.ByteBuffer r18) {
            /*
            r17 = this;
            r0 = r17;
            r1 = r18;
            r2 = 0;
            r4 = 0;
            r6 = r0.length;
            r7 = r18.remaining();
            r6 = r6 + r7;
            r0.length = r6;
            r6 = r18.remaining();
            r7 = 16;
            r8 = 24;
            r9 = 32;
            r10 = 40;
            r11 = 48;
            r12 = 8;
            switch(r6) {
                case 1: goto L_0x00f5;
                case 2: goto L_0x00e7;
                case 3: goto L_0x00d8;
                case 4: goto L_0x00c9;
                case 5: goto L_0x00ba;
                case 6: goto L_0x00ab;
                case 7: goto L_0x009d;
                case 8: goto L_0x0096;
                case 9: goto L_0x008a;
                case 10: goto L_0x007b;
                case 11: goto L_0x006b;
                case 12: goto L_0x005b;
                case 13: goto L_0x004b;
                case 14: goto L_0x003b;
                case 15: goto L_0x002c;
                default: goto L_0x0024;
            };
        L_0x0024:
            r6 = new java.lang.AssertionError;
            r7 = "Should never get here.";
            r6.<init>(r7);
            throw r6;
        L_0x002c:
            r6 = 14;
            r6 = r1.get(r6);
            r6 = com.google.common.primitives.UnsignedBytes.toInt(r6);
            r13 = (long) r6;
            r13 = r13 << r11;
            r15 = r4 ^ r13;
            goto L_0x003c;
        L_0x003b:
            r15 = r4;
        L_0x003c:
            r4 = 13;
            r4 = r1.get(r4);
            r4 = com.google.common.primitives.UnsignedBytes.toInt(r4);
            r4 = (long) r4;
            r4 = r4 << r10;
            r10 = r15 ^ r4;
            r4 = r10;
        L_0x004b:
            r6 = 12;
            r6 = r1.get(r6);
            r6 = com.google.common.primitives.UnsignedBytes.toInt(r6);
            r10 = (long) r6;
            r9 = r10 << r9;
            r13 = r4 ^ r9;
            goto L_0x005c;
        L_0x005b:
            r13 = r4;
        L_0x005c:
            r4 = 11;
            r4 = r1.get(r4);
            r4 = com.google.common.primitives.UnsignedBytes.toInt(r4);
            r4 = (long) r4;
            r4 = r4 << r8;
            r8 = r13 ^ r4;
            r4 = r8;
        L_0x006b:
            r6 = 10;
            r6 = r1.get(r6);
            r6 = com.google.common.primitives.UnsignedBytes.toInt(r6);
            r8 = (long) r6;
            r6 = r8 << r7;
            r8 = r4 ^ r6;
            r4 = r8;
        L_0x007b:
            r6 = 9;
            r6 = r1.get(r6);
            r6 = com.google.common.primitives.UnsignedBytes.toInt(r6);
            r6 = (long) r6;
            r6 = r6 << r12;
            r8 = r4 ^ r6;
            r4 = r8;
        L_0x008a:
            r6 = r1.get(r12);
            r6 = com.google.common.primitives.UnsignedBytes.toInt(r6);
            r6 = (long) r6;
            r8 = r4 ^ r6;
            r4 = r8;
        L_0x0096:
            r6 = r18.getLong();
            r8 = r2 ^ r6;
            goto L_0x0102;
        L_0x009d:
            r6 = 6;
            r6 = r1.get(r6);
            r6 = com.google.common.primitives.UnsignedBytes.toInt(r6);
            r13 = (long) r6;
            r13 = r13 << r11;
            r15 = r2 ^ r13;
            goto L_0x00ac;
        L_0x00ab:
            r15 = r2;
        L_0x00ac:
            r2 = 5;
            r2 = r1.get(r2);
            r2 = com.google.common.primitives.UnsignedBytes.toInt(r2);
            r2 = (long) r2;
            r2 = r2 << r10;
            r10 = r15 ^ r2;
            r2 = r10;
        L_0x00ba:
            r6 = 4;
            r6 = r1.get(r6);
            r6 = com.google.common.primitives.UnsignedBytes.toInt(r6);
            r10 = (long) r6;
            r9 = r10 << r9;
            r13 = r2 ^ r9;
            goto L_0x00ca;
        L_0x00c9:
            r13 = r2;
        L_0x00ca:
            r2 = 3;
            r2 = r1.get(r2);
            r2 = com.google.common.primitives.UnsignedBytes.toInt(r2);
            r2 = (long) r2;
            r2 = r2 << r8;
            r8 = r13 ^ r2;
            r2 = r8;
        L_0x00d8:
            r6 = 2;
            r6 = r1.get(r6);
            r6 = com.google.common.primitives.UnsignedBytes.toInt(r6);
            r8 = (long) r6;
            r6 = r8 << r7;
            r8 = r2 ^ r6;
            r2 = r8;
        L_0x00e7:
            r6 = 1;
            r6 = r1.get(r6);
            r6 = com.google.common.primitives.UnsignedBytes.toInt(r6);
            r6 = (long) r6;
            r6 = r6 << r12;
            r8 = r2 ^ r6;
            r2 = r8;
        L_0x00f5:
            r6 = 0;
            r6 = r1.get(r6);
            r6 = com.google.common.primitives.UnsignedBytes.toInt(r6);
            r6 = (long) r6;
            r8 = r2 ^ r6;
        L_0x0102:
            r2 = r0.h1;
            r6 = mixK1(r8);
            r10 = r2 ^ r6;
            r0.h1 = r10;
            r2 = r0.h2;
            r6 = mixK2(r4);
            r10 = r2 ^ r6;
            r0.h2 = r10;
            return;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.common.hash.Murmur3_128HashFunction.Murmur3_128Hasher.processRemaining(java.nio.ByteBuffer):void");
        }

        public HashCode makeHash() {
            this.h1 ^= (long) this.length;
            this.h2 ^= (long) this.length;
            this.h1 += this.h2;
            this.h2 += this.h1;
            this.h1 = fmix64(this.h1);
            this.h2 = fmix64(this.h2);
            this.h1 += this.h2;
            this.h2 += this.h1;
            return HashCode.fromBytesNoCopy(ByteBuffer.wrap(new byte[16]).order(ByteOrder.LITTLE_ENDIAN).putLong(this.h1).putLong(this.h2).array());
        }

        private static long fmix64(long k) {
            long k2 = (k ^ (k >>> 33)) * -49064778989728563L;
            long k3 = (k2 ^ (k2 >>> 33)) * -4265267296055464877L;
            return k3 ^ (k3 >>> 33);
        }

        private static long mixK1(long k1) {
            return Long.rotateLeft(k1 * C1, 31) * C2;
        }

        private static long mixK2(long k2) {
            return Long.rotateLeft(k2 * C2, 33) * C1;
        }
    }

    Murmur3_128HashFunction(int seed) {
        this.seed = seed;
    }

    public int bits() {
        return 128;
    }

    public Hasher newHasher() {
        return new Murmur3_128Hasher(this.seed);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Hashing.murmur3_128(");
        stringBuilder.append(this.seed);
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    public boolean equals(@Nullable Object object) {
        boolean z = false;
        if (!(object instanceof Murmur3_128HashFunction)) {
            return false;
        }
        if (this.seed == ((Murmur3_128HashFunction) object).seed) {
            z = true;
        }
        return z;
    }

    public int hashCode() {
        return getClass().hashCode() ^ this.seed;
    }
}
