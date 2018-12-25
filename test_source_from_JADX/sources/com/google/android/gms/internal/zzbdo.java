package com.google.android.gms.internal;

import android.support.annotation.NonNull;
import android.text.TextUtils;

public class zzbdo {
    private final String zza;
    private zzbem zzb;
    protected final zzbei zzc;

    protected zzbdo(String str, String str2, String str3) {
        zzbdw.zza(str);
        this.zza = str;
        this.zzc = new zzbei(str2);
        zzb(str3);
    }

    public void zza(long j, int i) {
    }

    public final void zza(zzbem zzbem) {
        this.zzb = zzbem;
        if (this.zzb == null) {
            zzf();
        }
    }

    public void zza(@NonNull String str) {
    }

    protected final void zza(String str, long j, String str2) throws IllegalStateException {
        Object[] objArr = new Object[]{str, null};
        this.zzb.zza(this.zza, str, j, null);
    }

    public final void zzb(String str) {
        if (!TextUtils.isEmpty(str)) {
            this.zzc.zza(str);
        }
    }

    public void zzf() {
    }

    public final String zzg() {
        return this.zza;
    }

    protected final long zzh() {
        return this.zzb.zza();
    }
}
