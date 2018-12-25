package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;

public final class zzbhm extends zzbgl {
    public static final Creator<zzbhm> CREATOR = new zzbho();
    final String zza;
    final int zzb;
    private int zzc;

    zzbhm(int i, String str, int i2) {
        this.zzc = i;
        this.zza = str;
        this.zzb = i2;
    }

    zzbhm(String str, int i) {
        this.zzc = 1;
        this.zza = str;
        this.zzb = i;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        i = zzbgo.zza(parcel);
        zzbgo.zza(parcel, 1, this.zzc);
        zzbgo.zza(parcel, 2, this.zza, false);
        zzbgo.zza(parcel, 3, this.zzb);
        zzbgo.zza(parcel, i);
    }
}
