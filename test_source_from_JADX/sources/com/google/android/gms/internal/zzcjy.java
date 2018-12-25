package com.google.android.gms.internal;

import android.content.SharedPreferences.Editor;
import android.support.annotation.WorkerThread;
import android.util.Pair;
import com.google.android.gms.common.internal.zzbq;

public final class zzcjy {
    private String zza;
    private final String zzb;
    private final String zzc;
    private final long zzd;
    private /* synthetic */ zzcju zze;

    private zzcjy(zzcju zzcju, String str, long j) {
        this.zze = zzcju;
        zzbq.zza(str);
        zzbq.zzb(j > 0);
        this.zza = String.valueOf(str).concat(":start");
        this.zzb = String.valueOf(str).concat(":count");
        this.zzc = String.valueOf(str).concat(":value");
        this.zzd = j;
    }

    @WorkerThread
    private final void zzb() {
        this.zze.zzc();
        long zza = this.zze.zzk().zza();
        Editor edit = this.zze.zzad().edit();
        edit.remove(this.zzb);
        edit.remove(this.zzc);
        edit.putLong(this.zza, zza);
        edit.apply();
    }

    @WorkerThread
    private final long zzc() {
        return this.zze.zzad().getLong(this.zza, 0);
    }

    @WorkerThread
    public final Pair<String, Long> zza() {
        this.zze.zzc();
        this.zze.zzc();
        long zzc = zzc();
        if (zzc == 0) {
            zzb();
            zzc = 0;
        } else {
            zzc = Math.abs(zzc - this.zze.zzk().zza());
        }
        if (zzc < this.zzd) {
            return null;
        }
        if (zzc > (this.zzd << 1)) {
            zzb();
            return null;
        }
        String string = this.zze.zzad().getString(this.zzc, null);
        long j = this.zze.zzad().getLong(this.zzb, 0);
        zzb();
        if (string != null) {
            if (j > 0) {
                return new Pair(string, Long.valueOf(j));
            }
        }
        return zzcju.zza;
    }

    @WorkerThread
    public final void zza(String str, long j) {
        this.zze.zzc();
        if (zzc() == 0) {
            zzb();
        }
        if (str == null) {
            str = "";
        }
        j = this.zze.zzad().getLong(this.zzb, 0);
        if (j <= 0) {
            Editor edit = this.zze.zzad().edit();
            edit.putString(this.zzc, str);
            edit.putLong(this.zzb, 1);
            edit.apply();
            return;
        }
        long j2 = j + 1;
        Object obj = (this.zze.zzp().zzz().nextLong() & Long.MAX_VALUE) < Long.MAX_VALUE / j2 ? 1 : null;
        Editor edit2 = this.zze.zzad().edit();
        if (obj != null) {
            edit2.putString(this.zzc, str);
        }
        edit2.putLong(this.zzb, j2);
        edit2.apply();
    }
}
