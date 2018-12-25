package com.google.android.gms.internal;

import android.content.SharedPreferences.Editor;
import android.support.annotation.WorkerThread;
import com.google.android.gms.common.internal.zzbq;

public final class zzcjw {
    private final String zza;
    private final boolean zzb = true;
    private boolean zzc;
    private boolean zzd;
    private /* synthetic */ zzcju zze;

    public zzcjw(zzcju zzcju, String str, boolean z) {
        this.zze = zzcju;
        zzbq.zza(str);
        this.zza = str;
    }

    @WorkerThread
    public final void zza(boolean z) {
        Editor edit = this.zze.zzad().edit();
        edit.putBoolean(this.zza, z);
        edit.apply();
        this.zzd = z;
    }

    @WorkerThread
    public final boolean zza() {
        if (!this.zzc) {
            this.zzc = true;
            this.zzd = this.zze.zzad().getBoolean(this.zza, this.zzb);
        }
        return this.zzd;
    }
}
