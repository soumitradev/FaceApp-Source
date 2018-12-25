package com.google.android.gms.internal;

import android.content.SharedPreferences.Editor;
import android.support.annotation.WorkerThread;
import com.google.android.gms.common.internal.zzbq;

public final class zzcjz {
    private final String zza;
    private final String zzb = null;
    private boolean zzc;
    private String zzd;
    private /* synthetic */ zzcju zze;

    public zzcjz(zzcju zzcju, String str, String str2) {
        this.zze = zzcju;
        zzbq.zza(str);
        this.zza = str;
    }

    @WorkerThread
    public final String zza() {
        if (!this.zzc) {
            this.zzc = true;
            this.zzd = this.zze.zzad().getString(this.zza, null);
        }
        return this.zzd;
    }

    @WorkerThread
    public final void zza(String str) {
        if (!zzcno.zzb(str, this.zzd)) {
            Editor edit = this.zze.zzad().edit();
            edit.putString(this.zza, str);
            edit.apply();
            this.zzd = str;
        }
    }
}
