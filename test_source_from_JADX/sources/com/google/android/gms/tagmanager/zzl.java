package com.google.android.gms.tagmanager;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import com.google.android.gms.internal.zzbh;
import com.google.android.gms.internal.zzbt;
import java.util.Map;

final class zzl extends zzbr {
    private static final String zza = zzbh.APP_VERSION_NAME.toString();
    private final Context zzb;

    public zzl(Context context) {
        super(zza, new String[0]);
        this.zzb = context;
    }

    public final zzbt zza(Map<String, zzbt> map) {
        try {
            return zzgk.zza(this.zzb.getPackageManager().getPackageInfo(this.zzb.getPackageName(), 0).versionName);
        } catch (NameNotFoundException e) {
            String packageName = this.zzb.getPackageName();
            String message = e.getMessage();
            StringBuilder stringBuilder = new StringBuilder((String.valueOf(packageName).length() + 25) + String.valueOf(message).length());
            stringBuilder.append("Package name ");
            stringBuilder.append(packageName);
            stringBuilder.append(" not found. ");
            stringBuilder.append(message);
            zzdj.zza(stringBuilder.toString());
            return zzgk.zzg();
        }
    }

    public final boolean zza() {
        return true;
    }
}
