package com.google.android.gms.tagmanager;

import android.content.Context;
import android.os.Process;
import com.google.android.gms.ads.identifier.AdvertisingIdClient.Info;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.util.zze;
import com.google.android.gms.common.util.zzi;

@Hide
public final class zza {
    private static Object zzl = new Object();
    private static zza zzm;
    private volatile long zza;
    private volatile long zzb;
    private volatile boolean zzc;
    private volatile Info zzd;
    private volatile long zze;
    private volatile long zzf;
    private final Context zzg;
    private final zze zzh;
    private final Thread zzi;
    private final Object zzj;
    private zzd zzk;

    private zza(Context context) {
        this(context, null, zzi.zzd());
    }

    private zza(Context context, zzd zzd, zze zze) {
        this.zza = 900000;
        this.zzb = 30000;
        this.zzc = false;
        this.zzj = new Object();
        this.zzk = new zzb(this);
        this.zzh = zze;
        if (context != null) {
            context = context.getApplicationContext();
        }
        this.zzg = context;
        this.zze = this.zzh.zza();
        this.zzi = new Thread(new zzc(this));
    }

    public static zza zza(Context context) {
        if (zzm == null) {
            synchronized (zzl) {
                if (zzm == null) {
                    zza zza = new zza(context);
                    zzm = zza;
                    zza.zzi.start();
                }
            }
        }
        return zzm;
    }

    private final void zzd() {
        synchronized (this) {
            try {
                if (!this.zzc) {
                    zze();
                    wait(500);
                }
            } catch (InterruptedException e) {
            }
        }
    }

    private final void zze() {
        if (this.zzh.zza() - this.zze > this.zzb) {
            synchronized (this.zzj) {
                this.zzj.notify();
            }
            this.zze = this.zzh.zza();
        }
    }

    private final void zzf() {
        if (this.zzh.zza() - this.zzf > 3600000) {
            this.zzd = null;
        }
    }

    private final void zzg() {
        Process.setThreadPriority(10);
        while (!this.zzc) {
            Info zza = this.zzk.zza();
            if (zza != null) {
                this.zzd = zza;
                this.zzf = this.zzh.zza();
                zzdj.zzc("Obtained fresh AdvertisingId info from GmsCore.");
            }
            synchronized (this) {
                notifyAll();
            }
            try {
                synchronized (this.zzj) {
                    this.zzj.wait(this.zza);
                }
            } catch (InterruptedException e) {
                zzdj.zzc("sleep interrupted in AdvertiserDataPoller thread; continuing");
            }
        }
    }

    public final String zza() {
        if (this.zzd == null) {
            zzd();
        } else {
            zze();
        }
        zzf();
        return this.zzd == null ? null : this.zzd.getId();
    }

    public final boolean zzb() {
        if (this.zzd == null) {
            zzd();
        } else {
            zze();
        }
        zzf();
        return this.zzd == null ? true : this.zzd.isLimitAdTrackingEnabled();
    }

    public final void zzc() {
        this.zzc = true;
        this.zzi.interrupt();
    }
}
