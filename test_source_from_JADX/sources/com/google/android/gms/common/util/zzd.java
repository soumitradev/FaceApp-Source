package com.google.android.gms.common.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.support.annotation.Nullable;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.internal.zzbih;

@Hide
public final class zzd {
    public static int zza(Context context, String str) {
        PackageInfo zzc = zzc(context, str);
        if (zzc == null || zzc.applicationInfo == null) {
            return -1;
        }
        Bundle bundle = zzc.applicationInfo.metaData;
        return bundle == null ? -1 : bundle.getInt("com.google.android.gms.version", -1);
    }

    public static boolean zzb(Context context, String str) {
        "com.google.android.gms".equals(str);
        try {
            return (zzbih.zza(context).zza(str, 0).flags & 2097152) != 0;
        } catch (NameNotFoundException e) {
            return false;
        }
    }

    @Nullable
    private static PackageInfo zzc(Context context, String str) {
        try {
            return zzbih.zza(context).zzb(str, 128);
        } catch (NameNotFoundException e) {
            return null;
        }
    }
}
