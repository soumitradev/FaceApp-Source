package com.google.android.gms.internal;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.job.JobInfo;
import android.app.job.JobInfo.Builder;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.PersistableBundle;
import android.support.v4.app.NotificationCompat;
import com.facebook.internal.NativeProtocol;
import com.google.android.gms.common.util.zze;

public final class zzcnj extends zzcli {
    private final AlarmManager zza = ((AlarmManager) zzl().getSystemService(NotificationCompat.CATEGORY_ALARM));
    private final zzcip zzb;
    private Integer zzc;

    protected zzcnj(zzckj zzckj) {
        super(zzckj);
        this.zzb = new zzcnk(this, zzckj, zzckj);
    }

    private final int zzaa() {
        if (this.zzc == null) {
            String str = "measurement";
            String valueOf = String.valueOf(zzl().getPackageName());
            this.zzc = Integer.valueOf((valueOf.length() != 0 ? str.concat(valueOf) : new String(str)).hashCode());
        }
        return this.zzc.intValue();
    }

    private final PendingIntent zzab() {
        Intent className = new Intent().setClassName(zzl(), "com.google.android.gms.measurement.AppMeasurementReceiver");
        className.setAction("com.google.android.gms.measurement.UPLOAD");
        return PendingIntent.getBroadcast(zzl(), 0, className, 0);
    }

    @TargetApi(24)
    private final void zzz() {
        JobScheduler jobScheduler = (JobScheduler) zzl().getSystemService("jobscheduler");
        zzt().zzae().zza("Cancelling job. JobID", Integer.valueOf(zzaa()));
        jobScheduler.cancel(zzaa());
    }

    public final /* bridge */ /* synthetic */ void zza() {
        super.zza();
    }

    public final void zza(long j) {
        zzaq();
        if (!zzcka.zza(zzl())) {
            zzt().zzad().zza("Receiver not registered/enabled");
        }
        if (!zzcmy.zza(zzl(), false)) {
            zzt().zzad().zza("Service not registered/enabled");
        }
        zzy();
        long zzb = zzk().zzb() + j;
        if (j < Math.max(0, ((Long) zzciz.zzad.zzb()).longValue()) && !this.zzb.zzb()) {
            zzt().zzae().zza("Scheduling upload with DelayedRunnable");
            this.zzb.zza(j);
        }
        if (VERSION.SDK_INT >= 24) {
            zzt().zzae().zza("Scheduling upload with JobScheduler");
            JobScheduler jobScheduler = (JobScheduler) zzl().getSystemService("jobscheduler");
            Builder builder = new Builder(zzaa(), new ComponentName(zzl(), "com.google.android.gms.measurement.AppMeasurementJobService"));
            builder.setMinimumLatency(j);
            builder.setOverrideDeadline(j << 1);
            PersistableBundle persistableBundle = new PersistableBundle();
            persistableBundle.putString(NativeProtocol.WEB_DIALOG_ACTION, "com.google.android.gms.measurement.UPLOAD");
            builder.setExtras(persistableBundle);
            JobInfo build = builder.build();
            zzt().zzae().zza("Scheduling job. JobID", Integer.valueOf(zzaa()));
            jobScheduler.schedule(build);
            return;
        }
        zzt().zzae().zza("Scheduling upload with AlarmManager");
        this.zza.setInexactRepeating(2, zzb, Math.max(((Long) zzciz.zzy.zzb()).longValue(), j), zzab());
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
        this.zza.cancel(zzab());
        if (VERSION.SDK_INT >= 24) {
            zzz();
        }
        return false;
    }

    public final void zzy() {
        zzaq();
        this.zza.cancel(zzab());
        this.zzb.zzc();
        if (VERSION.SDK_INT >= 24) {
            zzz();
        }
    }
}
