package com.google.android.gms.common.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.internal.zzbgl;
import com.google.android.gms.internal.zzbgo;

public final class zzbv extends zzbgl {
    public static final Creator<zzbv> CREATOR = new zzbw();
    private int zza;
    private final int zzb;
    private final int zzc;
    @Deprecated
    private final Scope[] zzd;

    zzbv(int i, int i2, int i3, Scope[] scopeArr) {
        this.zza = i;
        this.zzb = i2;
        this.zzc = i3;
        this.zzd = scopeArr;
    }

    public zzbv(int i, int i2, Scope[] scopeArr) {
        this(1, i, i2, null);
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zza = zzbgo.zza(parcel);
        zzbgo.zza(parcel, 1, this.zza);
        zzbgo.zza(parcel, 2, this.zzb);
        zzbgo.zza(parcel, 3, this.zzc);
        zzbgo.zza(parcel, 4, this.zzd, i, false);
        zzbgo.zza(parcel, zza);
    }
}
