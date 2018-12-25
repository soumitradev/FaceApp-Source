package com.google.android.gms.internal;

import android.os.Handler;
import android.os.Looper;
import com.google.android.gms.common.util.zze;
import com.google.android.gms.common.util.zzi;

public abstract class zzbdg extends zzbdo {
    protected final zze zza;
    protected boolean zzb;
    private Handler zzd;
    private long zze;
    private Runnable zzf;

    private zzbdg(String str, zze zze, String str2, String str3) {
        this(str, zze, str2, null, 1000);
    }

    public zzbdg(String str, zze zze, String str2, String str3, long j) {
        super(str, str2, str3);
        this.zzd = new Handler(Looper.getMainLooper());
        this.zza = zze;
        this.zzf = new zzbdi();
        this.zze = 1000;
        zza(false);
    }

    public zzbdg(String str, String str2, String str3) {
        this(str, zzi.zzd(), str2, null);
    }

    protected final void zza(boolean z) {
        if (this.zzb != z) {
            this.zzb = z;
            if (z) {
                this.zzd.postDelayed(this.zzf, this.zze);
                return;
            }
            this.zzd.removeCallbacks(this.zzf);
        }
    }

    protected abstract boolean zza(long j);

    public void zzf() {
        zza(false);
    }
}
