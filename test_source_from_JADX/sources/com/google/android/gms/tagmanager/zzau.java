package com.google.android.gms.tagmanager;

import java.util.List;

final class zzau implements Runnable {
    private /* synthetic */ List zza;
    private /* synthetic */ long zzb;
    private /* synthetic */ zzat zzc;

    zzau(zzat zzat, List list, long j) {
        this.zzc = zzat;
        this.zza = list;
        this.zzb = j;
    }

    public final void run() {
        this.zzc.zzb(this.zza, this.zzb);
    }
}
