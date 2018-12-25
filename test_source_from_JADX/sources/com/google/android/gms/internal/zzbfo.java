package com.google.android.gms.internal;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public final class zzbfo {
    private static int zza(byte[] bArr, int i) {
        return ((bArr[i + 3] & 255) << 24) | (((bArr[i] & 255) | ((bArr[i + 1] & 255) << 8)) | ((bArr[i + 2] & 255) << 16));
    }

    private static long zza(long j, long j2, long j3) {
        long j4 = (j ^ j2) * j3;
        j4 = (j2 ^ (j4 ^ (j4 >>> 47))) * j3;
        return (j4 ^ (j4 >>> 47)) * j3;
    }

    public static long zza(byte[] bArr) {
        byte[] bArr2 = bArr;
        int length = bArr2.length;
        if (length >= 0) {
            if (length <= bArr2.length) {
                long zzb;
                long zzb2;
                long zzb3;
                long zzb4;
                if (length <= 32) {
                    if (length > 16) {
                        long j = ((long) (length << 1)) - 7286425919675154353L;
                        zzb = zzb(bArr2, 0) * -5435081209227447693L;
                        zzb2 = zzb(bArr2, 8);
                        length += 0;
                        zzb3 = zzb(bArr2, length - 8) * j;
                        return zza((Long.rotateRight(zzb + zzb2, 43) + Long.rotateRight(zzb3, 30)) + (zzb(bArr2, length - 16) * -7286425919675154353L), (zzb + Long.rotateRight(zzb2 - 7286425919675154353L, 18)) + zzb3, j);
                    } else if (length >= 8) {
                        long j2 = ((long) (length << 1)) - 7286425919675154353L;
                        zzb4 = zzb(bArr2, 0) - 7286425919675154353L;
                        long zzb5 = zzb(bArr2, (length + 0) - 8);
                        return zza((Long.rotateRight(zzb5, 37) * j2) + zzb4, (Long.rotateRight(zzb4, 25) + zzb5) * j2, j2);
                    } else if (length >= 4) {
                        return zza(((long) length) + ((((long) zza(bArr2, 0)) & 4294967295L) << 3), ((long) zza(bArr2, (length + 0) - 4)) & 4294967295L, ((long) (length << 1)) - 7286425919675154353L);
                    } else if (length <= 0) {
                        return -7286425919675154353L;
                    } else {
                        zzb4 = (((long) ((bArr2[0] & 255) + ((bArr2[(length >> 1) + 0] & 255) << 8))) * -7286425919675154353L) ^ (((long) (length + ((bArr2[(length - 1) + 0] & 255) << 2))) * -4348849565147123417L);
                        return (zzb4 ^ (zzb4 >>> 47)) * -7286425919675154353L;
                    }
                } else if (length > 64) {
                    return zza(bArr2, 0, length);
                } else {
                    long j3 = ((long) (length << 1)) - 7286425919675154353L;
                    long zzb6 = zzb(bArr2, 0) * -7286425919675154353L;
                    zzb2 = zzb(bArr2, 8);
                    length += 0;
                    zzb3 = zzb(bArr2, length - 8) * j3;
                    zzb = (Long.rotateRight(zzb6 + zzb2, 43) + Long.rotateRight(zzb3, 30)) + (zzb(bArr2, length - 16) * -7286425919675154353L);
                    zzb4 = zza(zzb, (zzb6 + Long.rotateRight(zzb2 - 7286425919675154353L, 18)) + zzb3, j3);
                    long zzb7 = zzb(bArr2, 16) * j3;
                    zzb2 = zzb(bArr2, 24);
                    zzb = (zzb + zzb(bArr2, length - 32)) * j3;
                    return zza((Long.rotateRight(zzb7 + zzb2, 43) + Long.rotateRight(zzb, 30)) + ((zzb4 + zzb(bArr2, length - 24)) * j3), (zzb7 + Long.rotateRight(zzb2 + zzb6, 18)) + zzb, j3);
                }
            }
        }
        StringBuilder stringBuilder = new StringBuilder(67);
        stringBuilder.append("Out of bound index with offput: 0 and length: ");
        stringBuilder.append(length);
        throw new IndexOutOfBoundsException(stringBuilder.toString());
    }

    private static long zza(byte[] bArr, int i, int i2) {
        byte[] bArr2 = bArr;
        long[] jArr = new long[2];
        long[] jArr2 = new long[2];
        int i3 = 0;
        long zzb = zzb(bArr2, 0) + 95310865018149119L;
        int i4 = i2 - 1;
        int i5 = ((i4 / 64) << 6) + 0;
        int i6 = i4 & 63;
        int i7 = (i5 + i6) - 63;
        long j = 2480279821605975764L;
        long j2 = 1390051526045402406L;
        int i8 = i;
        while (true) {
            long rotateRight = (Long.rotateRight(((zzb + j) + jArr[i3]) + zzb(bArr2, i8 + 8), 37) * -5435081209227447693L) ^ jArr2[1];
            byte[] bArr3 = bArr;
            long rotateRight2 = (Long.rotateRight((j + jArr[1]) + zzb(bArr2, i8 + 48), 42) * -5435081209227447693L) + (jArr[0] + zzb(bArr3, i8 + 40));
            long rotateRight3 = Long.rotateRight(j2 + jArr2[0], 33) * -5435081209227447693L;
            zza(bArr3, i8, jArr[1] * -5435081209227447693L, rotateRight + jArr2[0], jArr);
            zza(bArr3, i8 + 32, rotateRight3 + jArr2[1], rotateRight2 + zzb(bArr3, i8 + 16), jArr2);
            i8 += 64;
            if (i8 == i5) {
                long j3 = ((rotateRight & 255) << 1) - 5435081209227447693L;
                jArr2[0] = jArr2[0] + ((long) i6);
                jArr[0] = jArr[0] + jArr2[0];
                jArr2[0] = jArr2[0] + jArr[0];
                long rotateRight4 = (Long.rotateRight(((rotateRight3 + rotateRight2) + jArr[0]) + zzb(bArr3, i7 + 8), 37) * j3) ^ (jArr2[1] * 9);
                long rotateRight5 = (Long.rotateRight((rotateRight2 + jArr[1]) + zzb(bArr3, i7 + 48), 42) * j3) + ((jArr[0] * 9) + zzb(bArr3, i7 + 40));
                long rotateRight6 = Long.rotateRight(rotateRight + jArr2[0], 33) * j3;
                zza(bArr3, i7, jArr[1] * j3, rotateRight4 + jArr2[0], jArr);
                zza(bArr3, i7 + 32, rotateRight6 + jArr2[1], rotateRight5 + zzb(bArr3, i7 + 16), jArr2);
                long j4 = j3;
                return zza((zza(jArr[0], jArr2[0], j4) + ((rotateRight5 ^ (rotateRight5 >>> 47)) * -4348849565147123417L)) + rotateRight4, zza(jArr[1], jArr2[1], j4) + rotateRight6, j4);
            }
            bArr2 = bArr3;
            j = rotateRight2;
            zzb = rotateRight3;
            j2 = rotateRight;
            i3 = 0;
        }
    }

    private static void zza(byte[] bArr, int i, long j, long j2, long[] jArr) {
        long zzb = zzb(bArr, i);
        long zzb2 = zzb(bArr, i + 8);
        long zzb3 = zzb(bArr, i + 16);
        long zzb4 = zzb(bArr, i + 24);
        long j3 = j + zzb;
        zzb = (j3 + zzb2) + zzb3;
        zzb2 = Long.rotateRight((j2 + j3) + zzb4, 21) + Long.rotateRight(zzb, 44);
        jArr[0] = zzb + zzb4;
        jArr[1] = zzb2 + j3;
    }

    private static long zzb(byte[] bArr, int i) {
        ByteBuffer wrap = ByteBuffer.wrap(bArr, i, 8);
        wrap.order(ByteOrder.LITTLE_ENDIAN);
        return wrap.getLong();
    }
}
