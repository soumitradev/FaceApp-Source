package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;

public final class zzbhx extends zzbgl {
    public static final Creator<zzbhx> CREATOR = new zzbhu();
    final String zza;
    final zzbhq<?, ?> zzb;
    private int zzc;

    zzbhx(int i, String str, zzbhq<?, ?> zzbhq) {
        this.zzc = i;
        this.zza = str;
        this.zzb = zzbhq;
    }

    zzbhx(String str, zzbhq<?, ?> zzbhq) {
        this.zzc = 1;
        this.zza = str;
        this.zzb = zzbhq;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zza = zzbgo.zza(parcel);
        zzbgo.zza(parcel, 1, this.zzc);
        zzbgo.zza(parcel, 2, this.zza, false);
        zzbgo.zza(parcel, 3, this.zzb, i, false);
        zzbgo.zza(parcel, zza);
    }
}
