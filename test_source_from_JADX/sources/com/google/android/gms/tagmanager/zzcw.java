package com.google.android.gms.tagmanager;

import android.content.Context;
import com.google.android.gms.internal.zzbh;
import com.google.android.gms.internal.zzbi;
import com.google.android.gms.internal.zzbt;
import java.util.Map;

final class zzcw extends zzbr {
    private static final String zza = zzbh.INSTALL_REFERRER.toString();
    private static final String zzb = zzbi.COMPONENT.toString();
    private final Context zzc;

    public zzcw(Context context) {
        super(zza, new String[0]);
        this.zzc = context;
    }

    public final zzbt zza(Map<String, zzbt> map) {
        Object zzb = zzcx.zzb(this.zzc, ((zzbt) map.get(zzb)) != null ? zzgk.zza((zzbt) map.get(zzb)) : null);
        return zzb != null ? zzgk.zza(zzb) : zzgk.zzg();
    }

    public final boolean zza() {
        return true;
    }
}
