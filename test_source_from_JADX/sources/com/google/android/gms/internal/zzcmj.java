package com.google.android.gms.internal;

import android.os.RemoteException;

final class zzcmj implements Runnable {
    private /* synthetic */ zzclz zza;
    private /* synthetic */ zzcme zzb;

    zzcmj(zzcme zzcme, zzclz zzclz) {
        this.zzb = zzcme;
        this.zza = zzclz;
    }

    public final void run() {
        zzcjb zzd = this.zzb.zzb;
        if (zzd == null) {
            this.zzb.zzt().zzy().zza("Failed to send current screen to service");
            return;
        }
        try {
            long j;
            String str;
            String str2;
            String packageName;
            if (this.zza == null) {
                j = 0;
                str = null;
                str2 = null;
                packageName = this.zzb.zzl().getPackageName();
            } else {
                j = this.zza.zzc;
                str = this.zza.zza;
                str2 = this.zza.zzb;
                packageName = this.zzb.zzl().getPackageName();
            }
            zzd.zza(j, str, str2, packageName);
            this.zzb.zzaf();
        } catch (RemoteException e) {
            this.zzb.zzt().zzy().zza("Failed to send current screen to the service", e);
        }
    }
}
