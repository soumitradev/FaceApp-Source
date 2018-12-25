package com.google.android.gms.dynamic;

import android.app.Activity;
import android.os.Bundle;

final class zzc implements zzi {
    private /* synthetic */ Activity zza;
    private /* synthetic */ Bundle zzb;
    private /* synthetic */ Bundle zzc;
    private /* synthetic */ zza zzd;

    zzc(zza zza, Activity activity, Bundle bundle, Bundle bundle2) {
        this.zzd = zza;
        this.zza = activity;
        this.zzb = bundle;
        this.zzc = bundle2;
    }

    public final int zza() {
        return 0;
    }

    public final void zza(LifecycleDelegate lifecycleDelegate) {
        this.zzd.zza.onInflate(this.zza, this.zzb, this.zzc);
    }
}
