package com.google.android.gms.common.util;

import android.util.Base64;
import com.google.android.gms.common.internal.Hide;

@Hide
public final class zzc {
    public static String zza(byte[] bArr) {
        return bArr == null ? null : Base64.encodeToString(bArr, 0);
    }

    public static String zzb(byte[] bArr) {
        return bArr == null ? null : Base64.encodeToString(bArr, 10);
    }

    public static String zzc(byte[] bArr) {
        return bArr == null ? null : Base64.encodeToString(bArr, 11);
    }
}
