package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzbh;
import com.google.android.gms.internal.zzbi;
import com.google.android.gms.internal.zzbt;
import java.util.List;
import java.util.Map;

final class zzaz extends zzgi {
    private static final String zza = zzbh.DATA_LAYER_WRITE.toString();
    private static final String zzb = zzbi.VALUE.toString();
    private static final String zzc = zzbi.CLEAR_PERSISTENT_DATA_LAYER_PREFIX.toString();
    private final DataLayer zzd;

    public zzaz(DataLayer dataLayer) {
        super(zza, zzb);
        this.zzd = dataLayer;
    }

    public final void zzb(Map<String, zzbt> map) {
        zzbt zzbt = (zzbt) map.get(zzb);
        if (zzbt != null) {
            if (zzbt != zzgk.zza()) {
                Object zzf = zzgk.zzf(zzbt);
                if (zzf instanceof List) {
                    for (Object next : (List) zzf) {
                        if (next instanceof Map) {
                            this.zzd.push((Map) next);
                        }
                    }
                }
            }
        }
        zzbt zzbt2 = (zzbt) map.get(zzc);
        if (zzbt2 != null && zzbt2 != zzgk.zza()) {
            String zza = zzgk.zza(zzbt2);
            if (zza != zzgk.zzf()) {
                this.zzd.zza(zza);
            }
        }
    }
}
