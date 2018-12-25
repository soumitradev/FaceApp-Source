package com.google.android.gms.internal;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import org.apache.commons.compress.utils.CharsetNames;

public final class zzfhz {
    static final Charset zza = Charset.forName("UTF-8");
    public static final byte[] zzb;
    private static Charset zzc = Charset.forName(CharsetNames.ISO_8859_1);
    private static ByteBuffer zzd;
    private static zzfhb zze = zzfhb.zza(zzb);

    static {
        byte[] bArr = new byte[0];
        zzb = bArr;
        zzd = ByteBuffer.wrap(bArr);
    }

    static int zza(int i, byte[] bArr, int i2, int i3) {
        int i4 = i;
        for (i = i2; i < i2 + i3; i++) {
            i4 = (i4 * 31) + bArr[i];
        }
        return i4;
    }

    public static int zza(long j) {
        return (int) (j ^ (j >>> 32));
    }

    public static int zza(boolean z) {
        return z ? 1231 : 1237;
    }

    public static int zza(byte[] bArr) {
        int length = bArr.length;
        int zza = zza(length, bArr, 0, length);
        return zza == 0 ? 1 : zza;
    }

    static <T> T zza(T t) {
        if (t != null) {
            return t;
        }
        throw new NullPointerException();
    }

    static <T> T zza(T t, String str) {
        if (t != null) {
            return t;
        }
        throw new NullPointerException(str);
    }

    static boolean zza(zzfjc zzfjc) {
        return false;
    }
}
