package com.google.android.gms.tagmanager;

import android.content.Context;
import com.google.android.gms.internal.zzbh;
import com.google.android.gms.internal.zzbt;
import java.util.Map;

final class zzf extends zzbr {
    private static final String zza = zzbh.ADVERTISING_TRACKING_ENABLED.toString();
    private final zza zzb;

    public zzf(Context context) {
        this(zza.zza(context));
    }

    private zzf(zza zza) {
        super(zza, new String[0]);
        this.zzb = zza;
    }

    public final zzbt zza(Map<String, zzbt> map) {
        return zzgk.zza(Boolean.valueOf(this.zzb.zzb() ^ 1));
    }

    public final boolean zza() {
        return false;
    }
}
