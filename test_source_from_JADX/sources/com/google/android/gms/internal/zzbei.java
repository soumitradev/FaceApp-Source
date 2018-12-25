package com.google.android.gms.internal;

import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.common.internal.zzbq;
import java.util.Locale;

public final class zzbei {
    private static boolean zza = false;
    private String zzb;
    private final boolean zzc;
    private boolean zzd;
    private boolean zze;
    private String zzf;

    public zzbei(String str) {
        this(str, false);
    }

    private zzbei(String str, boolean z) {
        zzbq.zza(str, (Object) "The log tag cannot be null or empty.");
        this.zzb = str;
        this.zzc = str.length() <= 23;
        this.zzd = false;
        this.zze = false;
    }

    private final boolean zza() {
        if (!this.zzd) {
            if (!this.zzc || !Log.isLoggable(this.zzb, 3)) {
                return false;
            }
        }
        return true;
    }

    private String zze(String str, Object... objArr) {
        if (objArr.length != 0) {
            str = String.format(Locale.ROOT, str, objArr);
        }
        if (!TextUtils.isEmpty(this.zzf)) {
            String valueOf = String.valueOf(this.zzf);
            str = String.valueOf(str);
            if (str.length() != 0) {
                return valueOf.concat(str);
            }
            str = new String(valueOf);
        }
        return str;
    }

    public final void zza(String str) {
        if (TextUtils.isEmpty(str)) {
            str = null;
        } else {
            str = String.format("[%s] ", new Object[]{str});
        }
        this.zzf = str;
    }

    public final void zza(String str, Object... objArr) {
        if (zza()) {
            Log.d(this.zzb, zze(str, objArr));
        }
    }

    public final void zza(Throwable th, String str, Object... objArr) {
        if (zza()) {
            Log.d(this.zzb, zze(str, objArr), th);
        }
    }

    public final void zza(boolean z) {
        this.zzd = true;
    }

    public final void zzb(String str, Object... objArr) {
        Log.i(this.zzb, zze(str, objArr));
    }

    public final void zzb(Throwable th, String str, Object... objArr) {
        Log.w(this.zzb, zze(str, objArr), th);
    }

    public final void zzc(String str, Object... objArr) {
        Log.w(this.zzb, zze(str, objArr));
    }

    public final void zzc(Throwable th, String str, Object... objArr) {
        Log.e(this.zzb, zze(str, objArr), th);
    }

    public final void zzd(String str, Object... objArr) {
        Log.e(this.zzb, zze(str, objArr));
    }
}
