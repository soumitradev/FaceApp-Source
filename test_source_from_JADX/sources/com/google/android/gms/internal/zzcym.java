package com.google.android.gms.internal;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;

public final class zzcym extends zzbgl implements Result {
    public static final Creator<zzcym> CREATOR = new zzcyn();
    private int zza;
    private int zzb;
    private Intent zzc;

    public zzcym() {
        this(0, null);
    }

    zzcym(int i, int i2, Intent intent) {
        this.zza = i;
        this.zzb = i2;
        this.zzc = intent;
    }

    private zzcym(int i, Intent intent) {
        this(2, 0, null);
    }

    public final Status getStatus() {
        return this.zzb == 0 ? Status.zza : Status.zze;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zza = zzbgo.zza(parcel);
        zzbgo.zza(parcel, 1, this.zza);
        zzbgo.zza(parcel, 2, this.zzb);
        zzbgo.zza(parcel, 3, this.zzc, i, false);
        zzbgo.zza(parcel, zza);
    }
}
