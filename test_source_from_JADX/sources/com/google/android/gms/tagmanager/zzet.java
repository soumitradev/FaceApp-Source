package com.google.android.gms.tagmanager;

import android.content.Context;
import com.google.android.gms.internal.zzbs;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

final class zzet implements zzag {
    private final String zza;
    private final Context zzb;
    private final ScheduledExecutorService zzc;
    private final zzew zzd;
    private ScheduledFuture<?> zze;
    private boolean zzf;
    private zzal zzg;
    private String zzh;
    private zzdi<zzbs> zzi;

    public zzet(Context context, String str, zzal zzal) {
        this(context, str, zzal, null, null);
    }

    private zzet(Context context, String str, zzal zzal, zzex zzex, zzew zzew) {
        this.zzg = zzal;
        this.zzb = context;
        this.zza = str;
        this.zzc = new zzeu(this).zza();
        this.zzd = new zzev(this);
    }

    private final synchronized void zza() {
        if (this.zzf) {
            throw new IllegalStateException("called method after closed");
        }
    }

    public final synchronized void release() {
        zza();
        if (this.zze != null) {
            this.zze.cancel(false);
        }
        this.zzc.shutdown();
        this.zzf = true;
    }

    public final synchronized void zza(long j, String str) {
        String str2 = this.zza;
        StringBuilder stringBuilder = new StringBuilder(String.valueOf(str2).length() + 55);
        stringBuilder.append("loadAfterDelay: containerId=");
        stringBuilder.append(str2);
        stringBuilder.append(" delay=");
        stringBuilder.append(j);
        zzdj.zze(stringBuilder.toString());
        zza();
        if (this.zzi == null) {
            throw new IllegalStateException("callback must be set before loadAfterDelay() is called.");
        }
        if (this.zze != null) {
            this.zze.cancel(false);
        }
        ScheduledExecutorService scheduledExecutorService = this.zzc;
        Runnable zza = this.zzd.zza(this.zzg);
        zza.zza(this.zzi);
        zza.zza(this.zzh);
        zza.zzb(str);
        this.zze = scheduledExecutorService.schedule(zza, j, TimeUnit.MILLISECONDS);
    }

    public final synchronized void zza(zzdi<zzbs> zzdi) {
        zza();
        this.zzi = zzdi;
    }

    public final synchronized void zza(String str) {
        zza();
        this.zzh = str;
    }
}
