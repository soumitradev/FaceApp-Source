package com.google.android.gms.common.internal;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.internal.zzbgl;
import com.google.android.gms.internal.zzbgo;

public final class zzbt extends zzbgl {
    public static final Creator<zzbt> CREATOR = new zzbu();
    private int zza;
    private IBinder zzb;
    private ConnectionResult zzc;
    private boolean zzd;
    private boolean zze;

    zzbt(int i, IBinder iBinder, ConnectionResult connectionResult, boolean z, boolean z2) {
        this.zza = i;
        this.zzb = iBinder;
        this.zzc = connectionResult;
        this.zzd = z;
        this.zze = z2;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof zzbt)) {
            return false;
        }
        zzbt zzbt = (zzbt) obj;
        return this.zzc.equals(zzbt.zzc) && zza().equals(zzbt.zza());
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zza = zzbgo.zza(parcel);
        zzbgo.zza(parcel, 1, this.zza);
        zzbgo.zza(parcel, 2, this.zzb, false);
        zzbgo.zza(parcel, 3, this.zzc, i, false);
        zzbgo.zza(parcel, 4, this.zzd);
        zzbgo.zza(parcel, 5, this.zze);
        zzbgo.zza(parcel, zza);
    }

    public final zzan zza() {
        IBinder iBinder = this.zzb;
        if (iBinder == null) {
            return null;
        }
        IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.common.internal.IAccountAccessor");
        return queryLocalInterface instanceof zzan ? (zzan) queryLocalInterface : new zzap(iBinder);
    }

    public final ConnectionResult zzb() {
        return this.zzc;
    }

    public final boolean zzc() {
        return this.zzd;
    }

    public final boolean zzd() {
        return this.zze;
    }
}
