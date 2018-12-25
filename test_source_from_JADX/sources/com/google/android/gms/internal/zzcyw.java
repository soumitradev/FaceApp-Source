package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.zzbt;

public final class zzcyw extends zzbgl {
    public static final Creator<zzcyw> CREATOR = new zzcyx();
    private int zza;
    private final ConnectionResult zzb;
    private final zzbt zzc;

    public zzcyw(int i) {
        this(new ConnectionResult(8, null), null);
    }

    zzcyw(int i, ConnectionResult connectionResult, zzbt zzbt) {
        this.zza = i;
        this.zzb = connectionResult;
        this.zzc = zzbt;
    }

    private zzcyw(ConnectionResult connectionResult, zzbt zzbt) {
        this(1, connectionResult, null);
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zza = zzbgo.zza(parcel);
        zzbgo.zza(parcel, 1, this.zza);
        zzbgo.zza(parcel, 2, this.zzb, i, false);
        zzbgo.zza(parcel, 3, this.zzc, i, false);
        zzbgo.zza(parcel, zza);
    }

    public final ConnectionResult zza() {
        return this.zzb;
    }

    public final zzbt zzb() {
        return this.zzc;
    }
}
