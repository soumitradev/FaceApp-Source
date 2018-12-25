package com.google.android.gms.internal;

final class zzfgx {
    private final zzfhg zza;
    private final byte[] zzb;

    private zzfgx(int i) {
        this.zzb = new byte[i];
        this.zza = zzfhg.zza(this.zzb);
    }

    public final zzfgs zza() {
        this.zza.zzc();
        return new zzfgz(this.zzb);
    }

    public final zzfhg zzb() {
        return this.zza;
    }
}
