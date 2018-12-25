package com.google.android.gms.internal;

import android.os.RemoteException;
import android.text.TextUtils;

final class zzcmm implements Runnable {
    private /* synthetic */ boolean zza = true;
    private /* synthetic */ boolean zzb;
    private /* synthetic */ zzcix zzc;
    private /* synthetic */ zzcif zzd;
    private /* synthetic */ String zze;
    private /* synthetic */ zzcme zzf;

    zzcmm(zzcme zzcme, boolean z, boolean z2, zzcix zzcix, zzcif zzcif, String str) {
        this.zzf = zzcme;
        this.zzb = z2;
        this.zzc = zzcix;
        this.zzd = zzcif;
        this.zze = str;
    }

    public final void run() {
        zzcjb zzd = this.zzf.zzb;
        if (zzd == null) {
            this.zzf.zzt().zzy().zza("Discarding data. Failed to send event to service");
            return;
        }
        if (this.zza) {
            this.zzf.zza(zzd, this.zzb ? null : this.zzc, this.zzd);
        } else {
            try {
                if (TextUtils.isEmpty(this.zze)) {
                    zzd.zza(this.zzc, this.zzd);
                } else {
                    zzd.zza(this.zzc, this.zze, this.zzf.zzt().zzaf());
                }
            } catch (RemoteException e) {
                this.zzf.zzt().zzy().zza("Failed to send event to the service", e);
            }
        }
        this.zzf.zzaf();
    }
}
