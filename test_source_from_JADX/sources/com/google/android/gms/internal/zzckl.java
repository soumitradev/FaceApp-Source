package com.google.android.gms.internal;

import java.util.concurrent.Callable;

final class zzckl implements Callable<String> {
    private /* synthetic */ String zza;
    private /* synthetic */ zzckj zzb;

    zzckl(zzckj zzckj, String str) {
        this.zzb = zzckj;
        this.zza = str;
    }

    public final /* synthetic */ Object call() throws Exception {
        zzcie zzb = this.zzb.zzq().zzb(this.zza);
        if (zzb != null) {
            return zzb.zzc();
        }
        this.zzb.zzf().zzaa().zza("App info was null when attempting to get app instance id");
        return null;
    }
}
