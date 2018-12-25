package com.google.android.gms.internal;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public final class zzdkl {
    private final List<zzdkn> zza;
    private final Map<String, List<zzdkj>> zzb;
    private final String zzc;
    private final int zzd;

    private zzdkl(List<zzdkn> list, Map<String, List<zzdkj>> map, String str, int i) {
        this.zza = Collections.unmodifiableList(list);
        this.zzb = Collections.unmodifiableMap(map);
        this.zzc = str;
        this.zzd = i;
    }

    public static zzdkm zza() {
        return new zzdkm();
    }

    public final String toString() {
        String valueOf = String.valueOf(this.zza);
        String valueOf2 = String.valueOf(this.zzb);
        StringBuilder stringBuilder = new StringBuilder((String.valueOf(valueOf).length() + 17) + String.valueOf(valueOf2).length());
        stringBuilder.append("Rules: ");
        stringBuilder.append(valueOf);
        stringBuilder.append("  Macros: ");
        stringBuilder.append(valueOf2);
        return stringBuilder.toString();
    }

    public final List<zzdkn> zzb() {
        return this.zza;
    }

    public final String zzc() {
        return this.zzc;
    }

    public final Map<String, List<zzdkj>> zzd() {
        return this.zzb;
    }
}
