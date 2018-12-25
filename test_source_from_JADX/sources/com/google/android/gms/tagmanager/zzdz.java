package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzbt;
import java.util.Map;

abstract class zzdz extends zzeg {
    public zzdz(String str) {
        super(str);
    }

    protected final boolean zza(zzbt zzbt, zzbt zzbt2, Map<String, zzbt> map) {
        zzgj zzb = zzgk.zzb(zzbt);
        zzgj zzb2 = zzgk.zzb(zzbt2);
        if (zzb != zzgk.zze()) {
            if (zzb2 != zzgk.zze()) {
                return zza(zzb, zzb2, (Map) map);
            }
        }
        return false;
    }

    protected abstract boolean zza(zzgj zzgj, zzgj zzgj2, Map<String, zzbt> map);
}
