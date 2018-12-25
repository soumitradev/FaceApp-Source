package com.google.android.gms.auth.api.signin.internal;

public final class zzq {
    private static int zza = 31;
    private int zzb = 1;

    public final int zza() {
        return this.zzb;
    }

    public final zzq zza(Object obj) {
        this.zzb = (zza * this.zzb) + (obj == null ? 0 : obj.hashCode());
        return this;
    }

    public final zzq zza(boolean z) {
        this.zzb = (zza * this.zzb) + z;
        return this;
    }
}
