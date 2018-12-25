package com.google.android.gms.tagmanager;

import com.google.android.gms.common.util.zze;
import javax.jmdns.impl.constants.DNSConstants;
import org.catrobat.catroid.common.BrickValues;

final class zzdh implements zzek {
    private final long zza = 900000;
    private final long zzb = DNSConstants.CLOSE_TIMEOUT;
    private final int zzc = 5;
    private double zzd = ((double) Math.min(1, 5));
    private long zze;
    private final Object zzf = new Object();
    private final String zzg;
    private final zze zzh;

    public zzdh(int i, int i2, long j, long j2, String str, zze zze) {
        this.zzg = str;
        this.zzh = zze;
    }

    public final boolean zza() {
        synchronized (this.zzf) {
            long zza = this.zzh.zza();
            if (zza - this.zze < this.zzb) {
                String str = this.zzg;
                StringBuilder stringBuilder = new StringBuilder(String.valueOf(str).length() + 34);
                stringBuilder.append("Excessive ");
                stringBuilder.append(str);
                stringBuilder.append(" detected; call ignored.");
                zzdj.zzb(stringBuilder.toString());
                return false;
            }
            if (this.zzd < ((double) this.zzc)) {
                double d = ((double) (zza - this.zze)) / ((double) this.zza);
                if (d > BrickValues.SET_COLOR_TO) {
                    this.zzd = Math.min((double) this.zzc, this.zzd + d);
                }
            }
            this.zze = zza;
            if (this.zzd >= 1.0d) {
                this.zzd -= 1.0d;
                return true;
            }
            str = this.zzg;
            stringBuilder = new StringBuilder(String.valueOf(str).length() + 34);
            stringBuilder.append("Excessive ");
            stringBuilder.append(str);
            stringBuilder.append(" detected; call ignored.");
            zzdj.zzb(stringBuilder.toString());
            return false;
        }
    }
}
