package com.google.android.gms.internal;

import android.os.RemoteException;

final class zzcmg implements Runnable {
    private /* synthetic */ zzcif zza;
    private /* synthetic */ zzcme zzb;

    zzcmg(zzcme zzcme, zzcif zzcif) {
        this.zzb = zzcme;
        this.zza = zzcif;
    }

    public final void run() {
        zzcjb zzd = this.zzb.zzb;
        if (zzd == null) {
            this.zzb.zzt().zzy().zza("Failed to reset data on the service; null service");
            return;
        }
        try {
            zzd.zzd(this.zza);
        } catch (RemoteException e) {
            this.zzb.zzt().zzy().zza("Failed to reset data on the service", e);
        }
        this.zzb.zzaf();
    }
}
