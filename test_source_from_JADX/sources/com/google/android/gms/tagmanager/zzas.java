package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzbh;
import com.google.android.gms.internal.zzbi;
import com.google.android.gms.internal.zzbt;
import java.util.Map;

final class zzas extends zzbr {
    private static final String zza = zzbh.CUSTOM_VAR.toString();
    private static final String zzb = zzbi.NAME.toString();
    private static final String zzc = zzbi.DEFAULT_VALUE.toString();
    private final DataLayer zzd;

    public zzas(DataLayer dataLayer) {
        super(zza, zzb);
        this.zzd = dataLayer;
    }

    public final zzbt zza(Map<String, zzbt> map) {
        Object obj = this.zzd.get(zzgk.zza((zzbt) map.get(zzb)));
        if (obj != null) {
            return zzgk.zza(obj);
        }
        zzbt zzbt = (zzbt) map.get(zzc);
        return zzbt != null ? zzbt : zzgk.zzg();
    }

    public final boolean zza() {
        return false;
    }
}
