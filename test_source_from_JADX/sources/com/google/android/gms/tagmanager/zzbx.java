package com.google.android.gms.tagmanager;

import android.text.TextUtils;

final class zzbx {
    private final long zza;
    private final long zzb;
    private final long zzc;
    private String zzd;

    zzbx(long j, long j2, long j3) {
        this.zza = j;
        this.zzb = j2;
        this.zzc = j3;
    }

    final long zza() {
        return this.zza;
    }

    final void zza(String str) {
        if (str != null && !TextUtils.isEmpty(str.trim())) {
            this.zzd = str;
        }
    }

    final long zzb() {
        return this.zzc;
    }

    final String zzc() {
        return this.zzd;
    }
}
