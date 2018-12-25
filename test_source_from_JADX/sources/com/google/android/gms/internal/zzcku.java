package com.google.android.gms.internal;

import java.util.List;
import java.util.concurrent.Callable;

final class zzcku implements Callable<List<zzcnn>> {
    private /* synthetic */ zzcif zza;
    private /* synthetic */ String zzb;
    private /* synthetic */ String zzc;
    private /* synthetic */ zzcko zzd;

    zzcku(zzcko zzcko, zzcif zzcif, String str, String str2) {
        this.zzd = zzcko;
        this.zza = zzcif;
        this.zzb = str;
        this.zzc = str2;
    }

    public final /* synthetic */ Object call() throws Exception {
        this.zzd.zza.zzag();
        return this.zzd.zza.zzq().zza(this.zza.zza, this.zzb, this.zzc);
    }
}
