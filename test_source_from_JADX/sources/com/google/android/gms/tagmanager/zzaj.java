package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzbh;
import com.google.android.gms.internal.zzbt;
import java.util.Map;

final class zzaj extends zzbr {
    private static final String zza = zzbh.CONTAINER_VERSION.toString();
    private final String zzb;

    public zzaj(String str) {
        super(zza, new String[0]);
        this.zzb = str;
    }

    public final zzbt zza(Map<String, zzbt> map) {
        return this.zzb == null ? zzgk.zzg() : zzgk.zza(this.zzb);
    }

    public final boolean zza() {
        return true;
    }
}
