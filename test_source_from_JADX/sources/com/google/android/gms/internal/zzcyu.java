package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.zzbr;

public final class zzcyu extends zzbgl {
    public static final Creator<zzcyu> CREATOR = new zzcyv();
    private int zza;
    private zzbr zzb;

    zzcyu(int i, zzbr zzbr) {
        this.zza = i;
        this.zzb = zzbr;
    }

    public zzcyu(zzbr zzbr) {
        this(1, zzbr);
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zza = zzbgo.zza(parcel);
        zzbgo.zza(parcel, 1, this.zza);
        zzbgo.zza(parcel, 2, this.zzb, i, false);
        zzbgo.zza(parcel, zza);
    }
}
