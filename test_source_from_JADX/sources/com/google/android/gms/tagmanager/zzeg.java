package com.google.android.gms.tagmanager;

import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.internal.zzbi;
import com.google.android.gms.internal.zzbt;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

@Hide
public abstract class zzeg extends zzbr {
    private static final String zza = zzbi.ARG0.toString();
    private static final String zzb = zzbi.ARG1.toString();

    public zzeg(String str) {
        super(str, zza, zzb);
    }

    public final zzbt zza(Map<String, zzbt> map) {
        boolean z;
        Iterator it = map.values().iterator();
        do {
            z = false;
            if (!it.hasNext()) {
                zzbt zzbt = (zzbt) map.get(zza);
                zzbt zzbt2 = (zzbt) map.get(zzb);
                if (zzbt != null) {
                    if (zzbt2 != null) {
                        z = zza(zzbt, zzbt2, map);
                    }
                }
            }
            return zzgk.zza(Boolean.valueOf(z));
        } while (((zzbt) it.next()) != zzgk.zzg());
        return zzgk.zza(Boolean.valueOf(z));
    }

    public final boolean zza() {
        return true;
    }

    protected abstract boolean zza(zzbt zzbt, zzbt zzbt2, Map<String, zzbt> map);

    public final /* bridge */ /* synthetic */ String zzd() {
        return super.zzd();
    }

    public final /* bridge */ /* synthetic */ Set zze() {
        return super.zze();
    }
}
