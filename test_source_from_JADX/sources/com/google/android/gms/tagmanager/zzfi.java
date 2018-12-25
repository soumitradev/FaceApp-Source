package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzbt;

final class zzfi {
    private zzea<zzbt> zza;
    private zzbt zzb;

    public zzfi(zzea<zzbt> zzea, zzbt zzbt) {
        this.zza = zzea;
        this.zzb = zzbt;
    }

    public final zzea<zzbt> zza() {
        return this.zza;
    }

    public final zzbt zzb() {
        return this.zzb;
    }

    public final int zzc() {
        return ((zzbt) this.zza.zza()).zze() + (this.zzb == null ? 0 : this.zzb.zze());
    }
}
