package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzdkf;

final class zzfa implements Runnable {
    private /* synthetic */ zzdkf zza;
    private /* synthetic */ zzey zzb;

    zzfa(zzey zzey, zzdkf zzdkf) {
        this.zzb = zzey;
        this.zza = zzdkf;
    }

    public final void run() {
        this.zzb.zzb(this.zza);
    }
}
