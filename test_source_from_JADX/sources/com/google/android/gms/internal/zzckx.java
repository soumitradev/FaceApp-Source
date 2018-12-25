package com.google.android.gms.internal;

import java.util.List;
import java.util.concurrent.Callable;

final class zzckx implements Callable<List<zzcii>> {
    private /* synthetic */ String zza;
    private /* synthetic */ String zzb;
    private /* synthetic */ String zzc;
    private /* synthetic */ zzcko zzd;

    zzckx(zzcko zzcko, String str, String str2, String str3) {
        this.zzd = zzcko;
        this.zza = str;
        this.zzb = str2;
        this.zzc = str3;
    }

    public final /* synthetic */ Object call() throws Exception {
        this.zzd.zza.zzag();
        return this.zzd.zza.zzq().zzb(this.zza, this.zzb, this.zzc);
    }
}
