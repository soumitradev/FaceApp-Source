package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzbt;
import java.util.Map;

abstract class zzga extends zzeg {
    public zzga(String str) {
        super(str);
    }

    protected final boolean zza(zzbt zzbt, zzbt zzbt2, Map<String, zzbt> map) {
        String zza = zzgk.zza(zzbt);
        String zza2 = zzgk.zza(zzbt2);
        if (zza != zzgk.zzf()) {
            if (zza2 != zzgk.zzf()) {
                return zza(zza, zza2, (Map) map);
            }
        }
        return false;
    }

    protected abstract boolean zza(String str, String str2, Map<String, zzbt> map);
}
