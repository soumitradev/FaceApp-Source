package com.google.android.gms.internal;

import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.util.zze;

@Hide
public final class zzbeo {
    public static final Object zza = new Object();
    private static final zzbei zzg = new zzbei("RequestTracker");
    private long zzb;
    private long zzc = -1;
    private long zzd = 0;
    private zzben zze;
    private final zze zzf;

    public zzbeo(zze zze, long j) {
        this.zzf = zze;
        this.zzb = j;
    }

    private final void zzc() {
        this.zzc = -1;
        this.zze = null;
        this.zzd = 0;
    }

    public final void zza() {
        synchronized (zza) {
            if (this.zzc != -1) {
                zzc();
            }
        }
    }

    public final void zza(long j, zzben zzben) {
        synchronized (zza) {
            zzben zzben2 = this.zze;
            long j2 = this.zzc;
            this.zzc = j;
            this.zze = zzben;
            this.zzd = this.zzf.zzb();
        }
        if (zzben2 != null) {
            zzben2.zza(j2);
        }
    }

    public final boolean zza(long j) {
        boolean z;
        synchronized (zza) {
            z = this.zzc != -1 && this.zzc == j;
        }
        return z;
    }

    public final boolean zza(long j, int i) {
        boolean z;
        zzben zzben;
        synchronized (zza) {
            z = true;
            if (this.zzc == -1 || j - this.zzd < this.zzb) {
                j = 0;
                zzben = null;
                z = false;
            } else {
                zzg.zza("request %d timed out", new Object[]{Long.valueOf(this.zzc)});
                j = this.zzc;
                zzben = this.zze;
                zzc();
            }
        }
        if (zzben != null) {
            zzben.zza(j, i, null);
        }
        return z;
    }

    public final boolean zza(long j, int i, Object obj) {
        boolean z;
        zzben zzben;
        synchronized (zza) {
            z = false;
            if (this.zzc == -1 || this.zzc != j) {
                zzben = null;
            } else {
                zzg.zza("request %d completed", new Object[]{Long.valueOf(this.zzc)});
                zzben zzben2 = this.zze;
                zzc();
                zzben = zzben2;
                z = true;
            }
        }
        if (zzben != null) {
            zzben.zza(j, i, obj);
        }
        return z;
    }

    public final boolean zzb() {
        boolean z;
        synchronized (zza) {
            z = this.zzc != -1;
        }
        return z;
    }
}
