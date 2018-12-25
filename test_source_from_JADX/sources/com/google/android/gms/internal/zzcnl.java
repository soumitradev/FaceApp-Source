package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.zzbq;

public final class zzcnl extends zzbgl {
    public static final Creator<zzcnl> CREATOR = new zzcnm();
    public final String zza;
    public final long zzb;
    public final String zzc;
    private int zzd;
    private Long zze;
    private Float zzf;
    private String zzg;
    private Double zzh;

    zzcnl(int i, String str, long j, Long l, Float f, String str2, String str3, Double d) {
        this.zzd = i;
        this.zza = str;
        this.zzb = j;
        this.zze = l;
        Double d2 = null;
        this.zzf = null;
        if (i == 1) {
            if (f != null) {
                d2 = Double.valueOf(f.doubleValue());
            }
            this.zzh = d2;
        } else {
            this.zzh = d;
        }
        this.zzg = str2;
        this.zzc = str3;
    }

    zzcnl(zzcnn zzcnn) {
        this(zzcnn.zzc, zzcnn.zzd, zzcnn.zze, zzcnn.zzb);
    }

    zzcnl(String str, long j, Object obj, String str2) {
        zzbq.zza(str);
        this.zzd = 2;
        this.zza = str;
        this.zzb = j;
        this.zzc = str2;
        if (obj == null) {
            this.zze = null;
            this.zzf = null;
            this.zzh = null;
            this.zzg = null;
        } else if (obj instanceof Long) {
            this.zze = (Long) obj;
            this.zzf = null;
            this.zzh = null;
            this.zzg = null;
        } else if (obj instanceof String) {
            this.zze = null;
            this.zzf = null;
            this.zzh = null;
            this.zzg = (String) obj;
        } else if (obj instanceof Double) {
            this.zze = null;
            this.zzf = null;
            this.zzh = (Double) obj;
            this.zzg = null;
        } else {
            throw new IllegalArgumentException("User attribute given of un-supported type");
        }
    }

    public final void writeToParcel(Parcel parcel, int i) {
        i = zzbgo.zza(parcel);
        zzbgo.zza(parcel, 1, this.zzd);
        zzbgo.zza(parcel, 2, this.zza, false);
        zzbgo.zza(parcel, 3, this.zzb);
        zzbgo.zza(parcel, 4, this.zze, false);
        zzbgo.zza(parcel, 5, null, false);
        zzbgo.zza(parcel, 6, this.zzg, false);
        zzbgo.zza(parcel, 7, this.zzc, false);
        zzbgo.zza(parcel, 8, this.zzh, false);
        zzbgo.zza(parcel, i);
    }

    public final Object zza() {
        return this.zze != null ? this.zze : this.zzh != null ? this.zzh : this.zzg != null ? this.zzg : null;
    }
}
