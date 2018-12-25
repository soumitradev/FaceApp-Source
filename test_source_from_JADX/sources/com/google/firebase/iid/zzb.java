package com.google.firebase.iid;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.VisibleForTesting;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;
import com.google.android.gms.common.internal.Hide;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Hide
public abstract class zzb extends Service {
    @VisibleForTesting
    final ExecutorService zza = Executors.newSingleThreadExecutor();
    private Binder zzb;
    private final Object zzc = new Object();
    private int zzd;
    private int zze = 0;

    private final void zzd(Intent intent) {
        if (intent != null) {
            WakefulBroadcastReceiver.completeWakefulIntent(intent);
        }
        synchronized (this.zzc) {
            this.zze--;
            if (this.zze == 0) {
                stopSelfResult(this.zzd);
            }
        }
    }

    @Hide
    public final synchronized IBinder onBind(Intent intent) {
        if (Log.isLoggable("EnhancedIntentService", 3)) {
            Log.d("EnhancedIntentService", "Service received bind request");
        }
        if (this.zzb == null) {
            this.zzb = new zzf(this);
        }
        return this.zzb;
    }

    @Hide
    public final int onStartCommand(Intent intent, int i, int i2) {
        synchronized (this.zzc) {
            this.zzd = i2;
            this.zze++;
        }
        Intent zza = zza(intent);
        if (zza == null) {
            zzd(intent);
            return 2;
        } else if (zzb(zza)) {
            zzd(intent);
            return 2;
        } else {
            this.zza.execute(new zzc(this, zza, intent));
            return 3;
        }
    }

    @Hide
    protected Intent zza(Intent intent) {
        return intent;
    }

    @Hide
    public boolean zzb(Intent intent) {
        return false;
    }

    @Hide
    public abstract void zzc(Intent intent);
}
