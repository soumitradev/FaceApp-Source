package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzbh;
import com.google.android.gms.internal.zzbt;
import java.util.Map;

final class zzgf extends zzbr {
    private static final String zza = zzbh.TIME.toString();

    public zzgf() {
        super(zza, new String[0]);
    }

    public final zzbt zza(Map<String, zzbt> map) {
        return zzgk.zza(Long.valueOf(System.currentTimeMillis()));
    }

    public final boolean zza() {
        return false;
    }
}
