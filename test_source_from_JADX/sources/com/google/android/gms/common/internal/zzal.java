package com.google.android.gms.common.internal;

import android.util.Log;

public final class zzal {
    private static int zza = 15;
    private static final String zzb = null;
    private final String zzc;
    private final String zzd;

    public zzal(String str) {
        this(str, null);
    }

    public zzal(String str, String str2) {
        zzbq.zza(str, "log tag cannot be null");
        zzbq.zzb(str.length() <= 23, "tag \"%s\" is longer than the %d character maximum", new Object[]{str, Integer.valueOf(23)});
        this.zzc = str;
        if (str2 != null) {
            if (str2.length() > 0) {
                this.zzd = str2;
                return;
            }
        }
        this.zzd = null;
    }

    private final String zza(String str) {
        return this.zzd == null ? str : this.zzd.concat(str);
    }

    private final String zza(String str, Object... objArr) {
        str = String.format(str, objArr);
        return this.zzd == null ? str : this.zzd.concat(str);
    }

    private final boolean zza(int i) {
        return Log.isLoggable(this.zzc, i);
    }

    public final void zza(String str, String str2) {
        if (zza(3)) {
            Log.d(str, zza(str2));
        }
    }

    public final void zza(String str, String str2, Throwable th) {
        if (zza(4)) {
            Log.i(str, zza(str2), th);
        }
    }

    public final void zza(String str, String str2, Object... objArr) {
        if (zza(3)) {
            Log.d(str, zza(str2, objArr));
        }
    }

    public final void zzb(String str, String str2) {
        if (zza(5)) {
            Log.w(str, zza(str2));
        }
    }

    public final void zzb(String str, String str2, Throwable th) {
        if (zza(5)) {
            Log.w(str, zza(str2), th);
        }
    }

    public final void zzb(String str, String str2, Object... objArr) {
        if (zza(5)) {
            Log.w(this.zzc, zza(str2, objArr));
        }
    }

    public final void zzc(String str, String str2) {
        if (zza(6)) {
            Log.e(str, zza(str2));
        }
    }

    public final void zzc(String str, String str2, Throwable th) {
        if (zza(6)) {
            Log.e(str, zza(str2), th);
        }
    }

    public final void zzc(String str, String str2, Object... objArr) {
        if (zza(6)) {
            Log.e(str, zza(str2, objArr));
        }
    }
}
