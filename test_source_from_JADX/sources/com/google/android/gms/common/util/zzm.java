package com.google.android.gms.common.util;

import name.antonsmirnov.firmata.writer.SysexMessageWriter;

public final class zzm {
    private static final char[] zza = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    private static final char[] zzb = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public static String zza(byte[] bArr) {
        int length = bArr.length;
        StringBuilder stringBuilder = new StringBuilder(length << 1);
        for (int i = 0; i < length; i++) {
            stringBuilder.append(zza[(bArr[i] & SysexMessageWriter.COMMAND_START) >>> 4]);
            stringBuilder.append(zza[bArr[i] & 15]);
        }
        return stringBuilder.toString();
    }

    public static String zzb(byte[] bArr) {
        char[] cArr = new char[(bArr.length << 1)];
        int i = 0;
        for (byte b : bArr) {
            int i2 = b & 255;
            int i3 = i + 1;
            cArr[i] = zzb[i2 >>> 4];
            i = i3 + 1;
            cArr[i3] = zzb[i2 & 15];
        }
        return new String(cArr);
    }
}
