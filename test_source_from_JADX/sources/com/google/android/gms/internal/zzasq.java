package com.google.android.gms.internal;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.job.JobInfo;
import android.app.job.JobInfo.Builder;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build.VERSION;
import android.os.PersistableBundle;
import android.support.v4.app.NotificationCompat;
import com.facebook.internal.NativeProtocol;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzbq;
import io.fabric.sdk.android.services.settings.SettingsJsonConstants;

@Hide
public final class zzasq extends zzari {
    private boolean zza;
    private boolean zzb;
    private final AlarmManager zzc = ((AlarmManager) zzk().getSystemService(NotificationCompat.CATEGORY_ALARM));
    private Integer zzd;

    protected zzasq(zzark zzark) {
        super(zzark);
    }

    private final PendingIntent zzf() {
        Intent intent = new Intent("com.google.android.gms.analytics.ANALYTICS_DISPATCH");
        intent.setComponent(new ComponentName(zzk(), "com.google.android.gms.analytics.AnalyticsReceiver"));
        return PendingIntent.getBroadcast(zzk(), 0, intent, 0);
    }

    private final int zzg() {
        if (this.zzd == null) {
            String str = SettingsJsonConstants.ANALYTICS_KEY;
            String valueOf = String.valueOf(zzk().getPackageName());
            this.zzd = Integer.valueOf((valueOf.length() != 0 ? str.concat(valueOf) : new String(str)).hashCode());
        }
        return this.zzd.intValue();
    }

    protected final void zza() {
        try {
            zze();
            if (zzasl.zze() > 0) {
                ActivityInfo receiverInfo = zzk().getPackageManager().getReceiverInfo(new ComponentName(zzk(), "com.google.android.gms.analytics.AnalyticsReceiver"), 2);
                if (receiverInfo != null && receiverInfo.enabled) {
                    zzb("Receiver registered for local dispatch.");
                    this.zza = true;
                }
            }
        } catch (NameNotFoundException e) {
        }
    }

    public final boolean zzb() {
        return this.zza;
    }

    public final boolean zzc() {
        return this.zzb;
    }

    public final void zzd() {
        zzz();
        zzbq.zza(this.zza, "Receiver not registered");
        long zze = zzasl.zze();
        if (zze > 0) {
            zze();
            long zzb = zzj().zzb() + zze;
            this.zzb = true;
            if (VERSION.SDK_INT >= 24) {
                zzb("Scheduling upload with JobScheduler");
                JobScheduler jobScheduler = (JobScheduler) zzk().getSystemService("jobscheduler");
                Builder builder = new Builder(zzg(), new ComponentName(zzk(), "com.google.android.gms.analytics.AnalyticsJobService"));
                builder.setMinimumLatency(zze);
                builder.setOverrideDeadline(zze << 1);
                PersistableBundle persistableBundle = new PersistableBundle();
                persistableBundle.putString(NativeProtocol.WEB_DIALOG_ACTION, "com.google.android.gms.analytics.ANALYTICS_DISPATCH");
                builder.setExtras(persistableBundle);
                JobInfo build = builder.build();
                zza("Scheduling job. JobID", Integer.valueOf(zzg()));
                jobScheduler.schedule(build);
                return;
            }
            zzb("Scheduling upload with AlarmManager");
            this.zzc.setInexactRepeating(2, zzb, zze, zzf());
        }
    }

    public final void zze() {
        this.zzb = false;
        this.zzc.cancel(zzf());
        if (VERSION.SDK_INT >= 24) {
            JobScheduler jobScheduler = (JobScheduler) zzk().getSystemService("jobscheduler");
            zza("Cancelling job. JobID", Integer.valueOf(zzg()));
            jobScheduler.cancel(zzg());
        }
    }
}
