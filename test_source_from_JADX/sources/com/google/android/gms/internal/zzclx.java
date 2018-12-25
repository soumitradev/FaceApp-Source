package com.google.android.gms.internal;

final class zzclx implements Runnable {
    private /* synthetic */ zzclk zza;

    zzclx(zzclk zzclk) {
        this.zza = zzclk;
    }

    public final void run() {
        zzclh zzclh = this.zza;
        zzclh.zzc();
        zzclh.zzaq();
        zzclh.zzt().zzad().zza("Resetting analytics data (FE)");
        zzclh.zzi().zzaa();
    }
}
