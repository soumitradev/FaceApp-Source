package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;

public final class zzbhj extends zzbgl {
    public static final Creator<zzbhj> CREATOR = new zzbhk();
    private int zza;
    private final zzbhl zzb;

    zzbhj(int i, zzbhl zzbhl) {
        this.zza = i;
        this.zzb = zzbhl;
    }

    private zzbhj(zzbhl zzbhl) {
        this.zza = 1;
        this.zzb = zzbhl;
    }

    public static zzbhj zza(zzbhr<?, ?> zzbhr) {
        if (zzbhr instanceof zzbhl) {
            return new zzbhj((zzbhl) zzbhr);
        }
        throw new IllegalArgumentException("Unsupported safe parcelable field converter class.");
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zza = zzbgo.zza(parcel);
        zzbgo.zza(parcel, 1, this.zza);
        zzbgo.zza(parcel, 2, this.zzb, i, false);
        zzbgo.zza(parcel, zza);
    }

    public final zzbhr<?, ?> zza() {
        if (this.zzb != null) {
            return this.zzb;
        }
        throw new IllegalStateException("There was no converter wrapped in this ConverterWrapper.");
    }
}
