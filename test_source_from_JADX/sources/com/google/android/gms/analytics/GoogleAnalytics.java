package com.google.android.gms.analytics;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.support.annotation.RequiresPermission;
import android.util.Log;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.internal.zzari;
import com.google.android.gms.internal.zzark;
import com.google.android.gms.internal.zzast;
import com.google.android.gms.internal.zzatc;
import com.google.android.gms.internal.zzatq;
import com.google.android.gms.internal.zzats;
import com.google.android.gms.internal.zzatu;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class GoogleAnalytics extends zza {
    private static List<Runnable> zzb = new ArrayList();
    private boolean zzc;
    private Set<GoogleAnalytics$zza> zzd = new HashSet();
    private boolean zze;
    private boolean zzf;
    private volatile boolean zzg;
    private boolean zzh;

    @Hide
    public GoogleAnalytics(zzark zzark) {
        super(zzark);
    }

    @RequiresPermission(allOf = {"android.permission.INTERNET", "android.permission.ACCESS_NETWORK_STATE"})
    public static GoogleAnalytics getInstance(Context context) {
        return zzark.zza(context).zzj();
    }

    @Hide
    public static void zze() {
        synchronized (GoogleAnalytics.class) {
            if (zzb != null) {
                for (Runnable run : zzb) {
                    run.run();
                }
                zzb = null;
            }
        }
    }

    public final void dispatchLocalHits() {
        zza().zzh().zzd();
    }

    @TargetApi(14)
    public final void enableAutoActivityReports(Application application) {
        if (!this.zze) {
            application.registerActivityLifecycleCallbacks(new GoogleAnalytics$zzb(this));
            this.zze = true;
        }
    }

    public final boolean getAppOptOut() {
        return this.zzg;
    }

    @Deprecated
    public final Logger getLogger() {
        return zzatc.zza();
    }

    public final boolean isDryRunEnabled() {
        return this.zzf;
    }

    public final Tracker newTracker(int i) {
        zzari tracker;
        synchronized (this) {
            tracker = new Tracker(zza(), null, null);
            if (i > 0) {
                zzats zzats = (zzats) new zzatq(zza()).zza(i);
                if (zzats != null) {
                    tracker.zza(zzats);
                }
            }
            tracker.zzaa();
        }
        return tracker;
    }

    public final Tracker newTracker(String str) {
        zzari tracker;
        synchronized (this) {
            tracker = new Tracker(zza(), str, null);
            tracker.zzaa();
        }
        return tracker;
    }

    public final void reportActivityStart(Activity activity) {
        if (!this.zze) {
            zza(activity);
        }
    }

    public final void reportActivityStop(Activity activity) {
        if (!this.zze) {
            zzb(activity);
        }
    }

    public final void setAppOptOut(boolean z) {
        this.zzg = z;
        if (this.zzg) {
            zza().zzh().zzc();
        }
    }

    public final void setDryRun(boolean z) {
        this.zzf = z;
    }

    public final void setLocalDispatchPeriod(int i) {
        zza().zzh().zza(i);
    }

    @Deprecated
    public final void setLogger(Logger logger) {
        zzatc.zza(logger);
        if (!this.zzh) {
            String str = (String) zzast.zzb.zza();
            String str2 = (String) zzast.zzb.zza();
            StringBuilder stringBuilder = new StringBuilder(String.valueOf(str2).length() + 112);
            stringBuilder.append("GoogleAnalytics.setLogger() is deprecated. To enable debug logging, please run:\nadb shell setprop log.tag.");
            stringBuilder.append(str2);
            stringBuilder.append(" DEBUG");
            Log.i(str, stringBuilder.toString());
            this.zzh = true;
        }
    }

    final void zza(Activity activity) {
        for (GoogleAnalytics$zza zza : this.zzd) {
            zza.zza(activity);
        }
    }

    final void zza(GoogleAnalytics$zza googleAnalytics$zza) {
        this.zzd.add(googleAnalytics$zza);
        Context zza = zza().zza();
        if (zza instanceof Application) {
            enableAutoActivityReports((Application) zza);
        }
    }

    final void zzb(Activity activity) {
        for (GoogleAnalytics$zza zzb : this.zzd) {
            zzb.zzb(activity);
        }
    }

    final void zzb(GoogleAnalytics$zza googleAnalytics$zza) {
        this.zzd.remove(googleAnalytics$zza);
    }

    @Hide
    public final void zzc() {
        zzatu zzk = zza().zzk();
        zzk.zzd();
        if (zzk.zze()) {
            setDryRun(zzk.zzf());
        }
        zzk.zzd();
        this.zzc = true;
    }

    @Hide
    public final boolean zzd() {
        return this.zzc;
    }
}
