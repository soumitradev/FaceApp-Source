package com.google.android.gms.tagmanager;

import android.content.Context;
import android.provider.Settings.Secure;
import com.google.android.gms.internal.zzbh;
import com.google.android.gms.internal.zzbt;
import java.util.Map;

final class zzbc extends zzbr {
    private static final String zza = zzbh.DEVICE_ID.toString();
    private final Context zzb;

    public zzbc(Context context) {
        super(zza, new String[0]);
        this.zzb = context;
    }

    public final zzbt zza(Map<String, zzbt> map) {
        Object string = Secure.getString(this.zzb.getContentResolver(), "android_id");
        return string == null ? zzgk.zzg() : zzgk.zza(string);
    }

    public final boolean zza() {
        return true;
    }
}
