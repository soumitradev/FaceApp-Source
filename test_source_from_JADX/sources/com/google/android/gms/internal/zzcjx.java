package com.google.android.gms.internal;

import android.content.SharedPreferences.Editor;
import android.support.annotation.WorkerThread;
import com.google.android.gms.common.internal.zzbq;

public final class zzcjx {
    private final String zza;
    private final long zzb;
    private boolean zzc;
    private long zzd;
    private /* synthetic */ zzcju zze;

    public zzcjx(zzcju zzcju, String str, long j) {
        this.zze = zzcju;
        zzbq.zza(str);
        this.zza = str;
        this.zzb = j;
    }

    @WorkerThread
    public final long zza() {
        if (!this.zzc) {
            this.zzc = true;
            this.zzd = this.zze.zzad().getLong(this.zza, this.zzb);
        }
        return this.zzd;
    }

    @WorkerThread
    public final void zza(long j) {
        Editor edit = this.zze.zzad().edit();
        edit.putLong(this.zza, j);
        edit.apply();
        this.zzd = j;
    }
}
