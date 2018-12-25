package com.google.android.gms.tagmanager;

import java.util.Arrays;

final class zzay {
    final String zza;
    final byte[] zzb;

    zzay(String str, byte[] bArr) {
        this.zza = str;
        this.zzb = bArr;
    }

    public final String toString() {
        String str = this.zza;
        int hashCode = Arrays.hashCode(this.zzb);
        StringBuilder stringBuilder = new StringBuilder(String.valueOf(str).length() + 54);
        stringBuilder.append("KeyAndSerialized: key = ");
        stringBuilder.append(str);
        stringBuilder.append(" serialized hash = ");
        stringBuilder.append(hashCode);
        return stringBuilder.toString();
    }
}
