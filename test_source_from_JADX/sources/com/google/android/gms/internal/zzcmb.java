package com.google.android.gms.internal;

import android.os.Bundle;

final class zzcmb implements Runnable {
    private /* synthetic */ boolean zza;
    private /* synthetic */ zzclz zzb;
    private /* synthetic */ zzcmd zzc;
    private /* synthetic */ zzcma zzd;

    zzcmb(zzcma zzcma, boolean z, zzclz zzclz, zzcmd zzcmd) {
        this.zzd = zzcma;
        this.zza = z;
        this.zzb = zzclz;
        this.zzc = zzcmd;
    }

    public final void run() {
        Object obj;
        Bundle bundle;
        if (this.zza && this.zzd.zza != null) {
            this.zzd.zza(this.zzd.zza);
        }
        if (this.zzb != null && this.zzb.zzc == this.zzc.zzc && zzcno.zzb(this.zzb.zzb, this.zzc.zzb)) {
            if (zzcno.zzb(this.zzb.zza, this.zzc.zza)) {
                obj = null;
                if (obj != null) {
                    bundle = new Bundle();
                    zzcma.zza(this.zzc, bundle, true);
                    if (this.zzb != null) {
                        if (this.zzb.zza != null) {
                            bundle.putString("_pn", this.zzb.zza);
                        }
                        bundle.putString("_pc", this.zzb.zzb);
                        bundle.putLong("_pi", this.zzb.zzc);
                    }
                    this.zzd.zzf().zza("auto", "_vs", bundle);
                }
                this.zzd.zza = this.zzc;
                this.zzd.zzi().zza(this.zzc);
            }
        }
        obj = 1;
        if (obj != null) {
            bundle = new Bundle();
            zzcma.zza(this.zzc, bundle, true);
            if (this.zzb != null) {
                if (this.zzb.zza != null) {
                    bundle.putString("_pn", this.zzb.zza);
                }
                bundle.putString("_pc", this.zzb.zzb);
                bundle.putLong("_pi", this.zzb.zzc);
            }
            this.zzd.zzf().zza("auto", "_vs", bundle);
        }
        this.zzd.zza = this.zzc;
        this.zzd.zzi().zza(this.zzc);
    }
}
