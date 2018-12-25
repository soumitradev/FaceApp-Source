package com.google.android.gms.common;

import android.support.annotation.NonNull;
import com.google.android.gms.common.internal.Hide;

@Hide
class zzp {
    private static final zzp zzc = new zzp(true, null, null);
    final boolean zza;
    final Throwable zzb;
    private String zzd;

    zzp(boolean z, String str, Throwable th) {
        this.zza = z;
        this.zzd = str;
        this.zzb = th;
    }

    static zzp zza() {
        return zzc;
    }

    static zzp zza(@NonNull String str) {
        return new zzp(false, str, null);
    }

    static zzp zza(String str, zzh zzh, boolean z, boolean z2) {
        return new zzr(str, zzh, z, z2, null);
    }

    static zzp zza(@NonNull String str, @NonNull Throwable th) {
        return new zzp(false, str, th);
    }

    String zzb() {
        return this.zzd;
    }
}
