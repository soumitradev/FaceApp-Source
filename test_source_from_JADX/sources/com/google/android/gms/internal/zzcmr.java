package com.google.android.gms.internal;

import android.os.RemoteException;
import java.util.concurrent.atomic.AtomicReference;

final class zzcmr implements Runnable {
    private /* synthetic */ AtomicReference zza;
    private /* synthetic */ zzcif zzb;
    private /* synthetic */ boolean zzc;
    private /* synthetic */ zzcme zzd;

    zzcmr(zzcme zzcme, AtomicReference atomicReference, zzcif zzcif, boolean z) {
        this.zzd = zzcme;
        this.zza = atomicReference;
        this.zzb = zzcif;
        this.zzc = z;
    }

    public final void run() {
        synchronized (this.zza) {
            Object obj;
            try {
                zzcjb zzd = this.zzd.zzb;
                if (zzd == null) {
                    this.zzd.zzt().zzy().zza("Failed to get user properties");
                    this.zza.notify();
                    return;
                }
                this.zza.set(zzd.zza(this.zzb, this.zzc));
                this.zzd.zzaf();
                obj = this.zza;
                obj.notify();
            } catch (RemoteException e) {
                try {
                    this.zzd.zzt().zzy().zza("Failed to get user properties", e);
                    obj = this.zza;
                } catch (Throwable th) {
                    this.zza.notify();
                }
            }
        }
    }
}
