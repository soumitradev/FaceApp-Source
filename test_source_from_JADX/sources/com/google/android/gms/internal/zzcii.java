package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.zzbq;

public final class zzcii extends zzbgl {
    public static final Creator<zzcii> CREATOR = new zzcij();
    public String zza;
    public String zzb;
    public zzcnl zzc;
    public long zzd;
    public boolean zze;
    public String zzf;
    public zzcix zzg;
    public long zzh;
    public zzcix zzi;
    public long zzj;
    public zzcix zzk;
    private int zzl;

    zzcii(int i, String str, String str2, zzcnl zzcnl, long j, boolean z, String str3, zzcix zzcix, long j2, zzcix zzcix2, long j3, zzcix zzcix3) {
        this.zzl = i;
        this.zza = str;
        this.zzb = str2;
        this.zzc = zzcnl;
        this.zzd = j;
        this.zze = z;
        this.zzf = str3;
        this.zzg = zzcix;
        this.zzh = j2;
        this.zzi = zzcix2;
        this.zzj = j3;
        this.zzk = zzcix3;
    }

    zzcii(zzcii zzcii) {
        this.zzl = 1;
        zzbq.zza(zzcii);
        this.zza = zzcii.zza;
        this.zzb = zzcii.zzb;
        this.zzc = zzcii.zzc;
        this.zzd = zzcii.zzd;
        this.zze = zzcii.zze;
        this.zzf = zzcii.zzf;
        this.zzg = zzcii.zzg;
        this.zzh = zzcii.zzh;
        this.zzi = zzcii.zzi;
        this.zzj = zzcii.zzj;
        this.zzk = zzcii.zzk;
    }

    zzcii(String str, String str2, zzcnl zzcnl, long j, boolean z, String str3, zzcix zzcix, long j2, zzcix zzcix2, long j3, zzcix zzcix3) {
        this.zzl = 1;
        this.zza = str;
        this.zzb = str2;
        this.zzc = zzcnl;
        this.zzd = j;
        this.zze = z;
        this.zzf = str3;
        this.zzg = zzcix;
        this.zzh = j2;
        this.zzi = zzcix2;
        this.zzj = j3;
        this.zzk = zzcix3;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zza = zzbgo.zza(parcel);
        zzbgo.zza(parcel, 1, this.zzl);
        zzbgo.zza(parcel, 2, this.zza, false);
        zzbgo.zza(parcel, 3, this.zzb, false);
        zzbgo.zza(parcel, 4, this.zzc, i, false);
        zzbgo.zza(parcel, 5, this.zzd);
        zzbgo.zza(parcel, 6, this.zze);
        zzbgo.zza(parcel, 7, this.zzf, false);
        zzbgo.zza(parcel, 8, this.zzg, i, false);
        zzbgo.zza(parcel, 9, this.zzh);
        zzbgo.zza(parcel, 10, this.zzi, i, false);
        zzbgo.zza(parcel, 11, this.zzj);
        zzbgo.zza(parcel, 12, this.zzk, i, false);
        zzbgo.zza(parcel, zza);
    }
}
