package com.google.android.gms.internal;

import java.util.concurrent.Callable;

final class zzclb implements Callable<byte[]> {
    private /* synthetic */ zzcix zza;
    private /* synthetic */ String zzb;
    private /* synthetic */ zzcko zzc;

    zzclb(zzcko zzcko, zzcix zzcix, String str) {
        this.zzc = zzcko;
        this.zza = zzcix;
        this.zzb = str;
    }

    public final /* synthetic */ Object call() throws Exception {
        this.zzc.zza.zzag();
        return this.zzc.zza.zzb(this.zza, this.zzb);
    }
}
