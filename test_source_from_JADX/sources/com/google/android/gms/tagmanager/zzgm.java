package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzbh;
import com.google.android.gms.internal.zzbi;
import com.google.android.gms.internal.zzbt;
import java.util.Map;

final class zzgm extends zzbr {
    private static final String zza = zzbh.UPPERCASE_STRING.toString();
    private static final String zzb = zzbi.ARG0.toString();

    public zzgm() {
        super(zza, zzb);
    }

    public final zzbt zza(Map<String, zzbt> map) {
        return zzgk.zza(zzgk.zza((zzbt) map.get(zzb)).toUpperCase());
    }

    public final boolean zza() {
        return true;
    }
}
