package com.google.android.gms.internal;

import android.os.RemoteException;
import android.text.TextUtils;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicReference;

final class zzcmo implements Runnable {
    private /* synthetic */ AtomicReference zza;
    private /* synthetic */ String zzb;
    private /* synthetic */ String zzc;
    private /* synthetic */ String zzd;
    private /* synthetic */ zzcif zze;
    private /* synthetic */ zzcme zzf;

    zzcmo(zzcme zzcme, AtomicReference atomicReference, String str, String str2, String str3, zzcif zzcif) {
        this.zzf = zzcme;
        this.zza = atomicReference;
        this.zzb = str;
        this.zzc = str2;
        this.zzd = str3;
        this.zze = zzcif;
    }

    public final void run() {
        synchronized (this.zza) {
            Object zza;
            try {
                zzcjb zzd = this.zzf.zzb;
                if (zzd == null) {
                    this.zzf.zzt().zzy().zza("Failed to get conditional properties", zzcjj.zza(this.zzb), this.zzc, this.zzd);
                    this.zza.set(Collections.emptyList());
                    this.zza.notify();
                    return;
                }
                AtomicReference atomicReference;
                if (TextUtils.isEmpty(this.zzb)) {
                    atomicReference = this.zza;
                    zza = zzd.zza(this.zzc, this.zzd, this.zze);
                } else {
                    atomicReference = this.zza;
                    zza = zzd.zza(this.zzb, this.zzc, this.zzd);
                }
                atomicReference.set(zza);
                this.zzf.zzaf();
                zza = this.zza;
                zza.notify();
            } catch (RemoteException e) {
                try {
                    this.zzf.zzt().zzy().zza("Failed to get conditional properties", zzcjj.zza(this.zzb), this.zzc, e);
                    this.zza.set(Collections.emptyList());
                    zza = this.zza;
                } catch (Throwable th) {
                    this.zza.notify();
                }
            }
        }
    }
}
