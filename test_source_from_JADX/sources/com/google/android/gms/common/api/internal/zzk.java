package com.google.android.gms.common.api.internal;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.Application;
import android.app.Application.ActivityLifecycleCallbacks;
import android.content.ComponentCallbacks2;
import android.content.res.Configuration;
import android.os.Bundle;
import com.google.android.gms.common.util.zzs;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public final class zzk implements ActivityLifecycleCallbacks, ComponentCallbacks2 {
    private static final zzk zza = new zzk();
    private final AtomicBoolean zzb = new AtomicBoolean();
    private final AtomicBoolean zzc = new AtomicBoolean();
    private final ArrayList<zzl> zzd = new ArrayList();
    private boolean zze = false;

    private zzk() {
    }

    public static zzk zza() {
        return zza;
    }

    public static void zza(Application application) {
        synchronized (zza) {
            if (!zza.zze) {
                application.registerActivityLifecycleCallbacks(zza);
                application.registerComponentCallbacks(zza);
                zza.zze = true;
            }
        }
    }

    private final void zzb(boolean z) {
        synchronized (zza) {
            ArrayList arrayList = this.zzd;
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                Object obj = arrayList.get(i);
                i++;
                ((zzl) obj).zza(z);
            }
        }
    }

    public final void onActivityCreated(Activity activity, Bundle bundle) {
        boolean compareAndSet = this.zzb.compareAndSet(true, false);
        this.zzc.set(true);
        if (compareAndSet) {
            zzb(false);
        }
    }

    public final void onActivityDestroyed(Activity activity) {
    }

    public final void onActivityPaused(Activity activity) {
    }

    public final void onActivityResumed(Activity activity) {
        boolean compareAndSet = this.zzb.compareAndSet(true, false);
        this.zzc.set(true);
        if (compareAndSet) {
            zzb(false);
        }
    }

    public final void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
    }

    public final void onActivityStarted(Activity activity) {
    }

    public final void onActivityStopped(Activity activity) {
    }

    public final void onConfigurationChanged(Configuration configuration) {
    }

    public final void onLowMemory() {
    }

    public final void onTrimMemory(int i) {
        if (i == 20 && this.zzb.compareAndSet(false, true)) {
            this.zzc.set(true);
            zzb(true);
        }
    }

    public final void zza(zzl zzl) {
        synchronized (zza) {
            this.zzd.add(zzl);
        }
    }

    @TargetApi(16)
    public final boolean zza(boolean z) {
        if (!this.zzc.get()) {
            if (!zzs.zzb()) {
                return true;
            }
            RunningAppProcessInfo runningAppProcessInfo = new RunningAppProcessInfo();
            ActivityManager.getMyMemoryState(runningAppProcessInfo);
            if (!this.zzc.getAndSet(true) && runningAppProcessInfo.importance > 100) {
                this.zzb.set(true);
            }
        }
        return this.zzb.get();
    }

    public final boolean zzb() {
        return this.zzb.get();
    }
}
