package com.google.android.gms.internal;

import java.util.HashMap;
import java.util.Map;

public final class zzdkk {
    private final Map<String, zzbt> zza;
    private zzbt zzb;

    private zzdkk() {
        this.zza = new HashMap();
    }

    public final zzdkj zza() {
        return new zzdkj(this.zza, this.zzb);
    }

    public final zzdkk zza(zzbt zzbt) {
        this.zzb = zzbt;
        return this;
    }

    public final zzdkk zza(String str, zzbt zzbt) {
        this.zza.put(str, zzbt);
        return this;
    }
}
