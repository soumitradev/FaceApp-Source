package com.google.android.gms.internal;

import com.google.android.gms.common.internal.zzbq;

final class zzcit {
    final String zza;
    final String zzb;
    final long zzc;
    final long zzd;
    final long zze;
    final long zzf;
    final Long zzg;
    final Long zzh;
    final Boolean zzi;

    zzcit(String str, String str2, long j, long j2, long j3, long j4, Long l, Long l2, Boolean bool) {
        zzcit zzcit = this;
        long j5 = j;
        long j6 = j2;
        long j7 = j4;
        zzbq.zza(str);
        zzbq.zza(str2);
        boolean z = false;
        zzbq.zzb(j5 >= 0);
        zzbq.zzb(j6 >= 0);
        if (j7 >= 0) {
            z = true;
        }
        zzbq.zzb(z);
        zzcit.zza = str;
        zzcit.zzb = str2;
        zzcit.zzc = j5;
        zzcit.zzd = j6;
        zzcit.zze = j3;
        zzcit.zzf = j7;
        zzcit.zzg = l;
        zzcit.zzh = l2;
        zzcit.zzi = bool;
    }

    final zzcit zza() {
        String str = this.zza;
        String str2 = this.zzb;
        long j = this.zzc + 1;
        long j2 = this.zzd + 1;
        long j3 = this.zze;
        long j4 = this.zzf;
        return new zzcit(str, str2, j, j2, j3, j4, this.zzg, this.zzh, this.zzi);
    }

    final zzcit zza(long j) {
        return new zzcit(this.zza, this.zzb, this.zzc, this.zzd, j, this.zzf, this.zzg, this.zzh, this.zzi);
    }

    final zzcit zza(Long l, Long l2, Boolean bool) {
        zzcit zzcit = this;
        Boolean bool2 = (bool == null || bool.booleanValue()) ? bool : null;
        return new zzcit(zzcit.zza, zzcit.zzb, zzcit.zzc, zzcit.zzd, zzcit.zze, zzcit.zzf, l, l2, bool2);
    }

    final zzcit zzb(long j) {
        return new zzcit(this.zza, this.zzb, this.zzc, this.zzd, this.zze, j, this.zzg, this.zzh, this.zzi);
    }
}
