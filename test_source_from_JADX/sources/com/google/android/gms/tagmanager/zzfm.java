package com.google.android.gms.tagmanager;

import com.google.android.gms.common.util.zze;
import com.google.android.gms.common.util.zzi;
import org.catrobat.catroid.common.BrickValues;

final class zzfm implements zzek {
    private final long zza;
    private final int zzb;
    private double zzc;
    private long zzd;
    private final Object zze;
    private final zze zzf;

    public zzfm() {
        this(60, 2000);
    }

    private zzfm(int i, long j) {
        this.zze = new Object();
        this.zzb = 60;
        this.zzc = (double) this.zzb;
        this.zza = 2000;
        this.zzf = zzi.zzd();
    }

    public final boolean zza() {
        synchronized (this.zze) {
            long zza = this.zzf.zza();
            if (this.zzc < ((double) this.zzb)) {
                double d = ((double) (zza - this.zzd)) / ((double) this.zza);
                if (d > BrickValues.SET_COLOR_TO) {
                    this.zzc = Math.min((double) this.zzb, this.zzc + d);
                }
            }
            this.zzd = zza;
            if (this.zzc >= 1.0d) {
                this.zzc -= 1.0d;
                return true;
            }
            zzdj.zzb("No more tokens available.");
            return false;
        }
    }
}
