package com.google.android.gms.internal;

import android.os.RemoteException;

final class zzcmi implements Runnable {
    private /* synthetic */ zzcif zza;
    private /* synthetic */ zzcme zzb;

    zzcmi(zzcme zzcme, zzcif zzcif) {
        this.zzb = zzcme;
        this.zza = zzcif;
    }

    public final void run() {
        zzcjb zzd = this.zzb.zzb;
        if (zzd == null) {
            this.zzb.zzt().zzy().zza("Discarding data. Failed to send app launch");
            return;
        }
        try {
            zzd.zza(this.zza);
            this.zzb.zza(zzd, null, this.zza);
            this.zzb.zzaf();
        } catch (RemoteException e) {
            this.zzb.zzt().zzy().zza("Failed to send app launch to the service", e);
        }
    }
}
