package com.google.android.gms.internal;

import android.os.RemoteException;
import java.util.concurrent.atomic.AtomicReference;

final class zzcmh implements Runnable {
    private /* synthetic */ AtomicReference zza;
    private /* synthetic */ zzcif zzb;
    private /* synthetic */ zzcme zzc;

    zzcmh(zzcme zzcme, AtomicReference atomicReference, zzcif zzcif) {
        this.zzc = zzcme;
        this.zza = atomicReference;
        this.zzb = zzcif;
    }

    public final void run() {
        synchronized (this.zza) {
            Object obj;
            try {
                zzcjb zzd = this.zzc.zzb;
                if (zzd == null) {
                    this.zzc.zzt().zzy().zza("Failed to get app instance id");
                    this.zza.notify();
                    return;
                }
                this.zza.set(zzd.zzc(this.zzb));
                String str = (String) this.zza.get();
                if (str != null) {
                    this.zzc.zzf().zza(str);
                    this.zzc.zzu().zzi.zza(str);
                }
                this.zzc.zzaf();
                obj = this.zza;
                obj.notify();
            } catch (RemoteException e) {
                try {
                    this.zzc.zzt().zzy().zza("Failed to get app instance id", e);
                    obj = this.zza;
                } catch (Throwable th) {
                    this.zza.notify();
                }
            }
        }
    }
}
