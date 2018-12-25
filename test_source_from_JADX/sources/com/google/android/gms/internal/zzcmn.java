package com.google.android.gms.internal;

import android.os.RemoteException;
import android.text.TextUtils;

final class zzcmn implements Runnable {
    private /* synthetic */ boolean zza = true;
    private /* synthetic */ boolean zzb;
    private /* synthetic */ zzcii zzc;
    private /* synthetic */ zzcif zzd;
    private /* synthetic */ zzcii zze;
    private /* synthetic */ zzcme zzf;

    zzcmn(zzcme zzcme, boolean z, boolean z2, zzcii zzcii, zzcif zzcif, zzcii zzcii2) {
        this.zzf = zzcme;
        this.zzb = z2;
        this.zzc = zzcii;
        this.zzd = zzcif;
        this.zze = zzcii2;
    }

    public final void run() {
        zzcjb zzd = this.zzf.zzb;
        if (zzd == null) {
            this.zzf.zzt().zzy().zza("Discarding data. Failed to send conditional user property to service");
            return;
        }
        if (this.zza) {
            this.zzf.zza(zzd, this.zzb ? null : this.zzc, this.zzd);
        } else {
            try {
                if (TextUtils.isEmpty(this.zze.zza)) {
                    zzd.zza(this.zzc, this.zzd);
                } else {
                    zzd.zza(this.zzc);
                }
            } catch (RemoteException e) {
                this.zzf.zzt().zzy().zza("Failed to send conditional user property to the service", e);
            }
        }
        this.zzf.zzaf();
    }
}
