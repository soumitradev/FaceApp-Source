package com.google.android.gms.internal;

import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.util.zze;
import org.catrobat.catroid.common.BrickValues;

@Hide
public final class zzatb {
    private final long zza;
    private final int zzb;
    private double zzc;
    private long zzd;
    private final Object zze;
    private final String zzf;
    private final zze zzg;

    private zzatb(int i, long j, String str, zze zze) {
        this.zze = new Object();
        this.zzb = 60;
        this.zzc = (double) this.zzb;
        this.zza = 2000;
        this.zzf = str;
        this.zzg = zze;
    }

    public zzatb(String str, zze zze) {
        this(60, 2000, str, zze);
    }

    public final boolean zza() {
        synchronized (this.zze) {
            long zza = this.zzg.zza();
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
            String str = this.zzf;
            StringBuilder stringBuilder = new StringBuilder(String.valueOf(str).length() + 34);
            stringBuilder.append("Excessive ");
            stringBuilder.append(str);
            stringBuilder.append(" detected; call ignored.");
            zzatc.zzb(stringBuilder.toString());
            return false;
        }
    }
}
