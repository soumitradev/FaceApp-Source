package com.google.android.gms.internal;

import java.io.IOException;

public final class zzfmu extends zzflm<zzfmu> {
    private static volatile zzfmu[] zzb;
    public String zza;

    public zzfmu() {
        this.zza = "";
        this.zzax = null;
        this.zzay = -1;
    }

    public static zzfmu[] zzb() {
        if (zzb == null) {
            synchronized (zzflq.zzb) {
                if (zzb == null) {
                    zzb = new zzfmu[0];
                }
            }
        }
        return zzb;
    }

    protected final int zza() {
        int zza = super.zza();
        return (this.zza == null || this.zza.equals("")) ? zza : zza + zzflk.zzb(1, this.zza);
    }

    public final /* synthetic */ zzfls zza(zzflj zzflj) throws IOException {
        while (true) {
            int zza = zzflj.zza();
            if (zza == 0) {
                return this;
            }
            if (zza == 10) {
                this.zza = zzflj.zze();
            } else if (!super.zza(zzflj, zza)) {
                return this;
            }
        }
    }

    public final void zza(zzflk zzflk) throws IOException {
        if (!(this.zza == null || this.zza.equals(""))) {
            zzflk.zza(1, this.zza);
        }
        super.zza(zzflk);
    }
}
