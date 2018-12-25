package com.google.android.gms.tagmanager;

import android.os.Build.VERSION;
import com.google.android.gms.internal.zzbh;
import com.google.android.gms.internal.zzbt;
import java.util.Map;

final class zzeb extends zzbr {
    private static final String zza = zzbh.OS_VERSION.toString();

    public zzeb() {
        super(zza, new String[0]);
    }

    public final zzbt zza(Map<String, zzbt> map) {
        return zzgk.zza(VERSION.RELEASE);
    }

    public final boolean zza() {
        return true;
    }
}
