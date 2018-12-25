package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzbt;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

abstract class zzbr {
    private final Set<String> zza;
    private final String zzb;

    public zzbr(String str, String... strArr) {
        this.zzb = str;
        this.zza = new HashSet(strArr.length);
        for (Object add : strArr) {
            this.zza.add(add);
        }
    }

    public abstract zzbt zza(Map<String, zzbt> map);

    public abstract boolean zza();

    final boolean zza(Set<String> set) {
        return set.containsAll(this.zza);
    }

    public String zzd() {
        return this.zzb;
    }

    public Set<String> zze() {
        return this.zza;
    }
}
