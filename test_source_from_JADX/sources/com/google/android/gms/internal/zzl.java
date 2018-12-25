package com.google.android.gms.internal;

import android.text.TextUtils;

public final class zzl {
    private final String zza;
    private final String zzb;

    public zzl(String str, String str2) {
        this.zza = str;
        this.zzb = str2;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        zzl zzl = (zzl) obj;
        return TextUtils.equals(this.zza, zzl.zza) && TextUtils.equals(this.zzb, zzl.zzb);
    }

    public final int hashCode() {
        return (this.zza.hashCode() * 31) + this.zzb.hashCode();
    }

    public final String toString() {
        String str = this.zza;
        String str2 = this.zzb;
        StringBuilder stringBuilder = new StringBuilder((String.valueOf(str).length() + 20) + String.valueOf(str2).length());
        stringBuilder.append("Header[name=");
        stringBuilder.append(str);
        stringBuilder.append(",value=");
        stringBuilder.append(str2);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    public final String zza() {
        return this.zza;
    }

    public final String zzb() {
        return this.zzb;
    }
}
