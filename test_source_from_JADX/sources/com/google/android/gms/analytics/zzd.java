package com.google.android.gms.analytics;

import android.support.v4.app.NotificationCompat;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.internal.zzatc;

@Hide
public final class zzd {
    public static String zza(int i) {
        return zza("&cd", i);
    }

    private static String zza(String str, int i) {
        if (i <= 0) {
            zzatc.zza("index out of range for prefix", str);
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder(String.valueOf(str).length() + 11);
        stringBuilder.append(str);
        stringBuilder.append(i);
        return stringBuilder.toString();
    }

    public static String zzb(int i) {
        return zza("cd", i);
    }

    public static String zzc(int i) {
        return zza("&cm", i);
    }

    public static String zzd(int i) {
        return zza("cm", i);
    }

    public static String zze(int i) {
        return zza("&pr", i);
    }

    public static String zzf(int i) {
        return zza("pr", i);
    }

    public static String zzg(int i) {
        return zza("&promo", i);
    }

    public static String zzh(int i) {
        return zza(NotificationCompat.CATEGORY_PROMO, i);
    }

    public static String zzi(int i) {
        return zza("pi", i);
    }

    public static String zzj(int i) {
        return zza("&il", i);
    }

    public static String zzk(int i) {
        return zza("il", i);
    }

    public static String zzl(int i) {
        return zza("cd", i);
    }

    public static String zzm(int i) {
        return zza("cm", i);
    }
}
