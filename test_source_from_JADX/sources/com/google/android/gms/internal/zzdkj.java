package com.google.android.gms.internal;

import java.util.Collections;
import java.util.Map;

public final class zzdkj {
    private final Map<String, zzbt> zza;
    private final zzbt zzb;

    private zzdkj(Map<String, zzbt> map, zzbt zzbt) {
        this.zza = map;
        this.zzb = zzbt;
    }

    public static zzdkk zza() {
        return new zzdkk();
    }

    public final String toString() {
        String valueOf = String.valueOf(Collections.unmodifiableMap(this.zza));
        String valueOf2 = String.valueOf(this.zzb);
        StringBuilder stringBuilder = new StringBuilder((String.valueOf(valueOf).length() + 32) + String.valueOf(valueOf2).length());
        stringBuilder.append("Properties: ");
        stringBuilder.append(valueOf);
        stringBuilder.append(" pushAfterEvaluate: ");
        stringBuilder.append(valueOf2);
        return stringBuilder.toString();
    }

    public final void zza(String str, zzbt zzbt) {
        this.zza.put(str, zzbt);
    }

    public final Map<String, zzbt> zzb() {
        return Collections.unmodifiableMap(this.zza);
    }

    public final zzbt zzc() {
        return this.zzb;
    }
}
