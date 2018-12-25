package com.google.android.gms.tagmanager;

import android.content.Context;
import com.google.android.gms.internal.zzbh;
import com.google.android.gms.internal.zzbt;
import java.util.Map;

final class zze extends zzbr {
    private static final String zza = zzbh.ADVERTISER_ID.toString();
    private final zza zzb;

    public zze(Context context) {
        this(zza.zza(context));
    }

    private zze(zza zza) {
        super(zza, new String[0]);
        this.zzb = zza;
        this.zzb.zza();
    }

    public final zzbt zza(Map<String, zzbt> map) {
        Object zza = this.zzb.zza();
        return zza == null ? zzgk.zzg() : zzgk.zza(zza);
    }

    public final boolean zza() {
        return false;
    }
}
