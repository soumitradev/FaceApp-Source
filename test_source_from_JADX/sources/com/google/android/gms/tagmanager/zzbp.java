package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzbh;
import com.google.android.gms.internal.zzbt;
import java.util.Map;

final class zzbp extends zzbr {
    private static final String zza = zzbh.EVENT.toString();
    private final zzfc zzb;

    public zzbp(zzfc zzfc) {
        super(zza, new String[0]);
        this.zzb = zzfc;
    }

    public final zzbt zza(Map<String, zzbt> map) {
        Object zza = this.zzb.zza();
        return zza == null ? zzgk.zzg() : zzgk.zza(zza);
    }

    public final boolean zza() {
        return false;
    }
}
