package com.google.android.gms.common.internal;

import android.util.Log;

@Hide
public abstract class zzi<TListener> {
    private TListener zza;
    private boolean zzb = false;
    private /* synthetic */ zzd zzc;

    public zzi(zzd zzd, TListener tListener) {
        this.zzc = zzd;
        this.zza = tListener;
    }

    protected abstract void zza(TListener tListener);

    protected abstract void zzb();

    public final void zzc() {
        synchronized (this) {
            Object obj = this.zza;
            if (this.zzb) {
                String valueOf = String.valueOf(this);
                StringBuilder stringBuilder = new StringBuilder(String.valueOf(valueOf).length() + 47);
                stringBuilder.append("Callback proxy ");
                stringBuilder.append(valueOf);
                stringBuilder.append(" being reused. This is not safe.");
                Log.w("GmsClient", stringBuilder.toString());
            }
        }
        if (obj != null) {
            try {
                zza(obj);
            } catch (RuntimeException e) {
                zzb();
                throw e;
            }
        }
        zzb();
        synchronized (this) {
            this.zzb = true;
        }
        zzd();
    }

    public final void zzd() {
        zze();
        synchronized (zzd.zzf(this.zzc)) {
            zzd.zzf(this.zzc).remove(this);
        }
    }

    public final void zze() {
        synchronized (this) {
            this.zza = null;
        }
    }
}
