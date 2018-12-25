package com.google.android.gms.tagmanager;

import android.content.Context;
import android.content.pm.PackageManager;
import com.google.android.gms.internal.zzbh;
import com.google.android.gms.internal.zzbt;
import java.util.Map;

final class zzj extends zzbr {
    private static final String zza = zzbh.APP_NAME.toString();
    private final Context zzb;

    public zzj(Context context) {
        super(zza, new String[0]);
        this.zzb = context;
    }

    public final zzbt zza(Map<String, zzbt> map) {
        try {
            PackageManager packageManager = this.zzb.getPackageManager();
            return zzgk.zza(packageManager.getApplicationLabel(packageManager.getApplicationInfo(this.zzb.getPackageName(), 0)).toString());
        } catch (Throwable e) {
            zzdj.zza("App name is not found.", e);
            return zzgk.zzg();
        }
    }

    public final boolean zza() {
        return true;
    }
}
