package com.google.android.gms.common.internal;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import com.google.android.gms.internal.zzbih;

public final class zzbf {
    private static Object zza = new Object();
    private static boolean zzb;
    private static String zzc;
    private static int zzd;

    public static String zza(Context context) {
        zzc(context);
        return zzc;
    }

    public static int zzb(Context context) {
        zzc(context);
        return zzd;
    }

    private static void zzc(Context context) {
        synchronized (zza) {
            if (zzb) {
                return;
            }
            zzb = true;
            try {
                Bundle bundle = zzbih.zza(context).zza(context.getPackageName(), 128).metaData;
                if (bundle == null) {
                    return;
                } else {
                    zzc = bundle.getString("com.google.app.id");
                    zzd = bundle.getInt("com.google.android.gms.version");
                }
            } catch (Throwable e) {
                Log.wtf("MetadataValueReader", "This should never happen.", e);
            }
        }
    }
}
