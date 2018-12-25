package com.google.android.gms.tagmanager;

import android.content.Context;
import com.google.android.gms.internal.zzbh;
import com.google.android.gms.internal.zzbt;
import java.util.Map;

final class zzi extends zzbr {
    private static final String zza = zzbh.APP_ID.toString();
    private final Context zzb;

    public zzi(Context context) {
        super(zza, new String[0]);
        this.zzb = context;
    }

    public final zzbt zza(Map<String, zzbt> map) {
        return zzgk.zza(this.zzb.getPackageName());
    }

    public final boolean zza() {
        return true;
    }
}
