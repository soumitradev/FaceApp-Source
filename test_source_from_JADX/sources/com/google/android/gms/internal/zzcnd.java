package com.google.android.gms.internal;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.WorkerThread;
import com.google.android.gms.common.util.zze;

public final class zzcnd extends zzcli {
    private Handler zza;
    private long zzb = zzk().zzb();
    private final zzcip zzc = new zzcne(this, this.zzp);
    private final zzcip zzd = new zzcnf(this, this.zzp);

    zzcnd(zzckj zzckj) {
        super(zzckj);
    }

    @WorkerThread
    private final void zza(long j) {
        zzcip zzcip;
        long zza;
        zzc();
        zzy();
        this.zzc.zzc();
        this.zzd.zzc();
        zzt().zzae().zza("Activity resumed, time", Long.valueOf(j));
        this.zzb = j;
        if (zzk().zza() - zzu().zzk.zza() > zzu().zzm.zza()) {
            zzu().zzl.zza(true);
            zzu().zzn.zza(0);
        }
        if (zzu().zzl.zza()) {
            zzcip = this.zzc;
            zza = zzu().zzj.zza();
        } else {
            zzcip = this.zzd;
            zza = 3600000;
        }
        zzcip.zza(Math.max(0, zza - zzu().zzn.zza()));
    }

    @WorkerThread
    private final void zzb(long j) {
        zzc();
        zzy();
        this.zzc.zzc();
        this.zzd.zzc();
        zzt().zzae().zza("Activity paused, time", Long.valueOf(j));
        if (this.zzb != 0) {
            zzu().zzn.zza(zzu().zzn.zza() + (j - this.zzb));
        }
    }

    private final void zzy() {
        synchronized (this) {
            if (this.zza == null) {
                this.zza = new Handler(Looper.getMainLooper());
            }
        }
    }

    @WorkerThread
    private final void zzz() {
        zzc();
        zza(false);
        zzd().zza(zzk().zzb());
    }

    public final /* bridge */ /* synthetic */ void zza() {
        super.zza();
    }

    @WorkerThread
    public final boolean zza(boolean z) {
        zzc();
        zzaq();
        long zzb = zzk().zzb();
        zzu().zzm.zza(zzk().zza());
        long j = zzb - this.zzb;
        if (z || j >= 1000) {
            zzu().zzn.zza(j);
            zzt().zzae().zza("Recording user engagement, ms", Long.valueOf(j));
            Bundle bundle = new Bundle();
            bundle.putLong("_et", j);
            zzcma.zza(zzj().zzy(), bundle, true);
            zzf().zza("auto", "_e", bundle);
            this.zzb = zzb;
            this.zzd.zzc();
            this.zzd.zza(Math.max(0, 3600000 - zzu().zzn.zza()));
            return true;
        }
        zzt().zzae().zza("Screen exposed for less than 1000 ms. Event not sent. time", Long.valueOf(j));
        return false;
    }

    public final /* bridge */ /* synthetic */ void zzb() {
        super.zzb();
    }

    public final /* bridge */ /* synthetic */ void zzc() {
        super.zzc();
    }

    public final /* bridge */ /* synthetic */ zzcia zzd() {
        return super.zzd();
    }

    public final /* bridge */ /* synthetic */ zzcih zze() {
        return super.zze();
    }

    public final /* bridge */ /* synthetic */ zzclk zzf() {
        return super.zzf();
    }

    public final /* bridge */ /* synthetic */ zzcje zzg() {
        return super.zzg();
    }

    public final /* bridge */ /* synthetic */ zzcir zzh() {
        return super.zzh();
    }

    public final /* bridge */ /* synthetic */ zzcme zzi() {
        return super.zzi();
    }

    public final /* bridge */ /* synthetic */ zzcma zzj() {
        return super.zzj();
    }

    public final /* bridge */ /* synthetic */ zze zzk() {
        return super.zzk();
    }

    public final /* bridge */ /* synthetic */ Context zzl() {
        return super.zzl();
    }

    public final /* bridge */ /* synthetic */ zzcjf zzm() {
        return super.zzm();
    }

    public final /* bridge */ /* synthetic */ zzcil zzn() {
        return super.zzn();
    }

    public final /* bridge */ /* synthetic */ zzcjh zzo() {
        return super.zzo();
    }

    public final /* bridge */ /* synthetic */ zzcno zzp() {
        return super.zzp();
    }

    public final /* bridge */ /* synthetic */ zzckd zzq() {
        return super.zzq();
    }

    public final /* bridge */ /* synthetic */ zzcnd zzr() {
        return super.zzr();
    }

    public final /* bridge */ /* synthetic */ zzcke zzs() {
        return super.zzs();
    }

    public final /* bridge */ /* synthetic */ zzcjj zzt() {
        return super.zzt();
    }

    public final /* bridge */ /* synthetic */ zzcju zzu() {
        return super.zzu();
    }

    public final /* bridge */ /* synthetic */ zzcik zzv() {
        return super.zzv();
    }

    protected final boolean zzw() {
        return false;
    }
}
