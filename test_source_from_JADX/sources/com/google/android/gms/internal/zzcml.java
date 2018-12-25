package com.google.android.gms.internal;

import android.os.RemoteException;

final class zzcml implements Runnable {
    private /* synthetic */ zzcif zza;
    private /* synthetic */ zzcme zzb;

    zzcml(zzcme zzcme, zzcif zzcif) {
        this.zzb = zzcme;
        this.zza = zzcif;
    }

    public final void run() {
        zzcjb zzd = this.zzb.zzb;
        if (zzd == null) {
            this.zzb.zzt().zzy().zza("Failed to send measurementEnabled to service");
            return;
        }
        try {
            zzd.zzb(this.zza);
            this.zzb.zzaf();
        } catch (RemoteException e) {
            this.zzb.zzt().zzy().zza("Failed to send measurementEnabled to the service", e);
        }
    }
}
