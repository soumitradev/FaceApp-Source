package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.cast.ApplicationMetadata;
import com.google.android.gms.common.internal.Hide;
import java.util.Arrays;

public final class zzbdx extends zzbgl {
    @Hide
    public static final Creator<zzbdx> CREATOR = new zzbdy();
    private double zza;
    private boolean zzb;
    private int zzc;
    private ApplicationMetadata zzd;
    private int zze;

    public zzbdx() {
        this(Double.NaN, false, -1, null, -1);
    }

    zzbdx(double d, boolean z, int i, ApplicationMetadata applicationMetadata, int i2) {
        this.zza = d;
        this.zzb = z;
        this.zzc = i;
        this.zzd = applicationMetadata;
        this.zze = i2;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzbdx)) {
            return false;
        }
        zzbdx zzbdx = (zzbdx) obj;
        return this.zza == zzbdx.zza && this.zzb == zzbdx.zzb && this.zzc == zzbdx.zzc && zzbdw.zza(this.zzd, zzbdx.zzd) && this.zze == zzbdx.zze;
    }

    public final int hashCode() {
        return Arrays.hashCode(new Object[]{Double.valueOf(this.zza), Boolean.valueOf(this.zzb), Integer.valueOf(this.zzc), this.zzd, Integer.valueOf(this.zze)});
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zza = zzbgo.zza(parcel);
        zzbgo.zza(parcel, 2, this.zza);
        zzbgo.zza(parcel, 3, this.zzb);
        zzbgo.zza(parcel, 4, this.zzc);
        zzbgo.zza(parcel, 5, this.zzd, i, false);
        zzbgo.zza(parcel, 6, this.zze);
        zzbgo.zza(parcel, zza);
    }

    public final double zza() {
        return this.zza;
    }

    public final boolean zzb() {
        return this.zzb;
    }

    public final int zzc() {
        return this.zzc;
    }

    public final int zzd() {
        return this.zze;
    }

    public final ApplicationMetadata zze() {
        return this.zzd;
    }
}
