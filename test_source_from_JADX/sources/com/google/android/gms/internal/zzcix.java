package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.zzbq;

public final class zzcix extends zzbgl {
    public static final Creator<zzcix> CREATOR = new zzciy();
    public final String zza;
    public final zzciu zzb;
    public final String zzc;
    public final long zzd;

    zzcix(zzcix zzcix, long j) {
        zzbq.zza(zzcix);
        this.zza = zzcix.zza;
        this.zzb = zzcix.zzb;
        this.zzc = zzcix.zzc;
        this.zzd = j;
    }

    public zzcix(String str, zzciu zzciu, String str2, long j) {
        this.zza = str;
        this.zzb = zzciu;
        this.zzc = str2;
        this.zzd = j;
    }

    public final String toString() {
        String str = this.zzc;
        String str2 = this.zza;
        String valueOf = String.valueOf(this.zzb);
        StringBuilder stringBuilder = new StringBuilder(((String.valueOf(str).length() + 21) + String.valueOf(str2).length()) + String.valueOf(valueOf).length());
        stringBuilder.append("origin=");
        stringBuilder.append(str);
        stringBuilder.append(",name=");
        stringBuilder.append(str2);
        stringBuilder.append(",params=");
        stringBuilder.append(valueOf);
        return stringBuilder.toString();
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zza = zzbgo.zza(parcel);
        zzbgo.zza(parcel, 2, this.zza, false);
        zzbgo.zza(parcel, 3, this.zzb, i, false);
        zzbgo.zza(parcel, 4, this.zzc, false);
        zzbgo.zza(parcel, 5, this.zzd);
        zzbgo.zza(parcel, zza);
    }
}
