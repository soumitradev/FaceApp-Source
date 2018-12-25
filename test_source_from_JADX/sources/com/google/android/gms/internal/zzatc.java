package com.google.android.gms.internal;

import android.util.Log;
import com.google.android.gms.analytics.Logger;
import com.google.android.gms.common.internal.Hide;

@Hide
@Deprecated
public final class zzatc {
    private static volatile Logger zza = new zzasm();

    public static Logger zza() {
        return zza;
    }

    public static void zza(Logger logger) {
        zza = logger;
    }

    public static void zza(String str) {
        zzarh zzb = zzatd.zzb();
        if (zzb != null) {
            zzb.zzb(str);
        } else if (zza(0)) {
            Log.v((String) zzast.zzb.zza(), str);
        }
        Logger logger = zza;
        if (logger != null) {
            logger.verbose(str);
        }
    }

    public static void zza(String str, Object obj) {
        zzarh zzb = zzatd.zzb();
        if (zzb != null) {
            zzb.zze(str, obj);
        } else if (zza(3)) {
            String valueOf;
            if (obj != null) {
                valueOf = String.valueOf(obj);
                StringBuilder stringBuilder = new StringBuilder((String.valueOf(str).length() + 1) + String.valueOf(valueOf).length());
                stringBuilder.append(str);
                stringBuilder.append(":");
                stringBuilder.append(valueOf);
                valueOf = stringBuilder.toString();
            } else {
                valueOf = str;
            }
            Log.e((String) zzast.zzb.zza(), valueOf);
        }
        Logger logger = zza;
        if (logger != null) {
            logger.error(str);
        }
    }

    private static boolean zza(int i) {
        return zza != null && zza.getLogLevel() <= i;
    }

    public static void zzb(String str) {
        zzarh zzb = zzatd.zzb();
        if (zzb != null) {
            zzb.zze(str);
        } else if (zza(2)) {
            Log.w((String) zzast.zzb.zza(), str);
        }
        Logger logger = zza;
        if (logger != null) {
            logger.warn(str);
        }
    }
}
