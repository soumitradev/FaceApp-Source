package com.google.android.gms.internal;

import android.support.annotation.Nullable;
import android.util.Log;
import com.google.android.gms.common.internal.zzal;
import java.util.Locale;
import org.catrobat.catroid.common.Constants;

public final class zzbhf {
    private final String zza;
    private final String zzb;
    private final zzal zzc;
    private final int zzd;

    private zzbhf(String str, String str2) {
        this.zzb = str2;
        this.zza = str;
        this.zzc = new zzal(str);
        this.zzd = zza();
    }

    public zzbhf(String str, String... strArr) {
        this(str, zza(strArr));
    }

    private final int zza() {
        int i = 2;
        while (7 >= i && !Log.isLoggable(this.zza, i)) {
            i++;
        }
        return i;
    }

    private static String zza(String... strArr) {
        if (strArr.length == 0) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Constants.REMIX_URL_PREFIX_INDICATOR);
        for (String str : strArr) {
            if (stringBuilder.length() > 1) {
                stringBuilder.append(",");
            }
            stringBuilder.append(str);
        }
        stringBuilder.append(Constants.REMIX_URL_SUFIX_INDICATOR);
        stringBuilder.append(' ');
        return stringBuilder.toString();
    }

    private final boolean zza(int i) {
        return this.zzd <= i;
    }

    private final String zzf(String str, @Nullable Object... objArr) {
        if (objArr != null && objArr.length > 0) {
            str = String.format(Locale.US, str, objArr);
        }
        return this.zzb.concat(str);
    }

    public final void zza(String str, Throwable th, @Nullable Object... objArr) {
        Log.wtf(this.zza, zzf(str, objArr), th);
    }

    public final void zza(String str, @Nullable Object... objArr) {
        if (zza(2)) {
            Log.v(this.zza, zzf(str, objArr));
        }
    }

    public final void zza(Throwable th) {
        Log.wtf(this.zza, th);
    }

    public final void zzb(String str, @Nullable Object... objArr) {
        if (zza(3)) {
            Log.d(this.zza, zzf(str, objArr));
        }
    }

    public final void zzc(String str, @Nullable Object... objArr) {
        Log.i(this.zza, zzf(str, objArr));
    }

    public final void zzd(String str, @Nullable Object... objArr) {
        Log.w(this.zza, zzf(str, objArr));
    }

    public final void zze(String str, @Nullable Object... objArr) {
        Log.e(this.zza, zzf(str, objArr));
    }
}
