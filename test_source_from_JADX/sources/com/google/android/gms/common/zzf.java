package com.google.android.gms.common;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzak;
import com.google.android.gms.common.util.zzj;
import com.google.android.gms.internal.zzbih;

@Hide
public class zzf {
    public static final String GOOGLE_PLAY_SERVICES_PACKAGE = "com.google.android.gms";
    public static final int GOOGLE_PLAY_SERVICES_VERSION_CODE = zzs.GOOGLE_PLAY_SERVICES_VERSION_CODE;
    private static final zzf zza = new zzf();

    zzf() {
    }

    public static int zza(Context context, int i) {
        i = zzs.zza(context, i);
        return zzs.zzc(context, i) ? 18 : i;
    }

    @Nullable
    @Hide
    public static Intent zza(Context context, int i, @Nullable String str) {
        switch (i) {
            case 1:
            case 2:
                return (context == null || !zzj.zzb(context)) ? zzak.zza("com.google.android.gms", zza(context, str)) : zzak.zza();
            case 3:
                return zzak.zza("com.google.android.gms");
            default:
                return null;
        }
    }

    public static zzf zza() {
        return zza;
    }

    private static String zza(@Nullable Context context, @Nullable String str) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("gcore_");
        stringBuilder.append(GOOGLE_PLAY_SERVICES_VERSION_CODE);
        stringBuilder.append("-");
        if (!TextUtils.isEmpty(str)) {
            stringBuilder.append(str);
        }
        stringBuilder.append("-");
        if (context != null) {
            stringBuilder.append(context.getPackageName());
        }
        stringBuilder.append("-");
        if (context != null) {
            try {
                stringBuilder.append(zzbih.zza(context).zzb(context.getPackageName(), 0).versionCode);
            } catch (NameNotFoundException e) {
            }
        }
        return stringBuilder.toString();
    }

    @Hide
    public static void zzb(Context context) throws GooglePlayServicesRepairableException, GooglePlayServicesNotAvailableException {
        zzs.zza(context);
    }

    @Hide
    public static boolean zzb(Context context, int i) {
        return zzs.zzc(context, i);
    }

    @Hide
    public static void zzc(Context context) {
        zzs.zzc(context);
    }

    @Hide
    public static int zzd(Context context) {
        return zzs.zzd(context);
    }

    @Nullable
    public PendingIntent getErrorResolutionPendingIntent(Context context, int i, int i2) {
        return zza(context, i, i2, null);
    }

    public String getErrorString(int i) {
        return zzs.getErrorString(i);
    }

    public int isGooglePlayServicesAvailable(Context context) {
        return zza(context, -1);
    }

    public boolean isUserResolvableError(int i) {
        return zzs.isUserRecoverableError(i);
    }

    @Nullable
    @Hide
    public final PendingIntent zza(Context context, int i, int i2, @Nullable String str) {
        Intent zza = zza(context, i, str);
        return zza == null ? null : PendingIntent.getActivity(context, i2, zza, 268435456);
    }

    @Nullable
    @Hide
    @Deprecated
    public final Intent zza(int i) {
        return zza(null, i, null);
    }
}
