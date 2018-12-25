package com.google.android.gms.internal;

import android.os.Bundle;

final class zzcls implements Runnable {
    private /* synthetic */ String zza;
    private /* synthetic */ String zzb;
    private /* synthetic */ long zzc;
    private /* synthetic */ Bundle zzd;
    private /* synthetic */ boolean zze;
    private /* synthetic */ boolean zzf;
    private /* synthetic */ boolean zzg;
    private /* synthetic */ String zzh;
    private /* synthetic */ zzclk zzi;

    zzcls(zzclk zzclk, String str, String str2, long j, Bundle bundle, boolean z, boolean z2, boolean z3, String str3) {
        this.zzi = zzclk;
        this.zza = str;
        this.zzb = str2;
        this.zzc = j;
        this.zzd = bundle;
        this.zze = z;
        this.zzf = z2;
        this.zzg = z3;
        this.zzh = str3;
    }

    public final void run() {
        this.zzi.zzb(this.zza, this.zzb, this.zzc, this.zzd, this.zze, this.zzf, this.zzg, this.zzh);
    }
}
