package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import com.google.android.gms.common.internal.zzbq;

public final class zzcif extends zzbgl {
    public static final Creator<zzcif> CREATOR = new zzcig();
    public final String zza;
    public final String zzb;
    public final String zzc;
    public final String zzd;
    public final long zze;
    public final long zzf;
    public final String zzg;
    public final boolean zzh;
    public final boolean zzi;
    public final long zzj;
    public final String zzk;
    public final long zzl;
    public final long zzm;
    public final int zzn;
    public final boolean zzo;

    zzcif(String str, String str2, String str3, long j, String str4, long j2, long j3, String str5, boolean z, boolean z2, String str6, long j4, long j5, int i, boolean z3) {
        zzbq.zza(str);
        this.zza = str;
        r0.zzb = TextUtils.isEmpty(str2) ? null : str2;
        r0.zzc = str3;
        r0.zzj = j;
        r0.zzd = str4;
        r0.zze = j2;
        r0.zzf = j3;
        r0.zzg = str5;
        r0.zzh = z;
        r0.zzi = z2;
        r0.zzk = str6;
        r0.zzl = j4;
        r0.zzm = j5;
        r0.zzn = i;
        r0.zzo = z3;
    }

    zzcif(String str, String str2, String str3, String str4, long j, long j2, String str5, boolean z, boolean z2, long j3, String str6, long j4, long j5, int i, boolean z3) {
        this.zza = str;
        this.zzb = str2;
        this.zzc = str3;
        this.zzj = j3;
        this.zzd = str4;
        this.zze = j;
        this.zzf = j2;
        this.zzg = str5;
        this.zzh = z;
        this.zzi = z2;
        this.zzk = str6;
        this.zzl = j4;
        this.zzm = j5;
        this.zzn = i;
        this.zzo = z3;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        i = zzbgo.zza(parcel);
        zzbgo.zza(parcel, 2, this.zza, false);
        zzbgo.zza(parcel, 3, this.zzb, false);
        zzbgo.zza(parcel, 4, this.zzc, false);
        zzbgo.zza(parcel, 5, this.zzd, false);
        zzbgo.zza(parcel, 6, this.zze);
        zzbgo.zza(parcel, 7, this.zzf);
        zzbgo.zza(parcel, 8, this.zzg, false);
        zzbgo.zza(parcel, 9, this.zzh);
        zzbgo.zza(parcel, 10, this.zzi);
        zzbgo.zza(parcel, 11, this.zzj);
        zzbgo.zza(parcel, 12, this.zzk, false);
        zzbgo.zza(parcel, 13, this.zzl);
        zzbgo.zza(parcel, 14, this.zzm);
        zzbgo.zza(parcel, 15, this.zzn);
        zzbgo.zza(parcel, 16, this.zzo);
        zzbgo.zza(parcel, i);
    }
}
