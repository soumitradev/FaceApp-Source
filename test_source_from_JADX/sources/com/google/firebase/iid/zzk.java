package com.google.firebase.iid;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.VisibleForTesting;
import android.util.Log;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.tasks.Task;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Hide
public final class zzk {
    private static zzk zza;
    private final Context zzb;
    private final ScheduledExecutorService zzc;
    private zzm zzd = new zzm();
    private int zze = 1;

    @VisibleForTesting
    private zzk(Context context, ScheduledExecutorService scheduledExecutorService) {
        this.zzc = scheduledExecutorService;
        this.zzb = context.getApplicationContext();
    }

    private final synchronized int zza() {
        int i;
        i = this.zze;
        this.zze = i + 1;
        return i;
    }

    private final synchronized <T> Task<T> zza(zzt<T> zzt) {
        if (Log.isLoggable("MessengerIpcClient", 3)) {
            String valueOf = String.valueOf(zzt);
            StringBuilder stringBuilder = new StringBuilder(String.valueOf(valueOf).length() + 9);
            stringBuilder.append("Queueing ");
            stringBuilder.append(valueOf);
            Log.d("MessengerIpcClient", stringBuilder.toString());
        }
        if (!this.zzd.zza((zzt) zzt)) {
            this.zzd = new zzm();
            this.zzd.zza((zzt) zzt);
        }
        return zzt.zzb.getTask();
    }

    public static synchronized zzk zza(Context context) {
        zzk zzk;
        synchronized (zzk.class) {
            if (zza == null) {
                zza = new zzk(context, Executors.newSingleThreadScheduledExecutor());
            }
            zzk = zza;
        }
        return zzk;
    }

    public final Task<Void> zza(int i, Bundle bundle) {
        return zza(new zzs(zza(), 2, bundle));
    }

    public final Task<Bundle> zzb(int i, Bundle bundle) {
        return zza(new zzv(zza(), 1, bundle));
    }
}
