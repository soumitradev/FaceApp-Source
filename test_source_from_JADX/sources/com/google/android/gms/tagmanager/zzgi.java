package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzbt;
import java.util.Map;

abstract class zzgi extends zzbr {
    public zzgi(String str, String... strArr) {
        super(str, strArr);
    }

    public zzbt zza(Map<String, zzbt> map) {
        zzb(map);
        return zzgk.zzg();
    }

    public boolean zza() {
        return false;
    }

    public abstract void zzb(Map<String, zzbt> map);
}
