package com.google.firebase.iid;

import android.content.BroadcastReceiver.PendingResult;
import android.content.Intent;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

final class zzd {
    final Intent zza;
    private final PendingResult zzb;
    private boolean zzc = false;
    private final ScheduledFuture<?> zzd;

    zzd(Intent intent, PendingResult pendingResult, ScheduledExecutorService scheduledExecutorService) {
        this.zza = intent;
        this.zzb = pendingResult;
        this.zzd = scheduledExecutorService.schedule(new zze(this, intent), 9500, TimeUnit.MILLISECONDS);
    }

    final synchronized void zza() {
        if (!this.zzc) {
            this.zzb.finish();
            this.zzd.cancel(false);
            this.zzc = true;
        }
    }
}
