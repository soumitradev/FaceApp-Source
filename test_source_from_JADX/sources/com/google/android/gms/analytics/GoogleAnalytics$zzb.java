package com.google.android.gms.analytics;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application.ActivityLifecycleCallbacks;
import android.os.Bundle;

@TargetApi(14)
class GoogleAnalytics$zzb implements ActivityLifecycleCallbacks {
    private /* synthetic */ GoogleAnalytics zza;

    GoogleAnalytics$zzb(GoogleAnalytics googleAnalytics) {
        this.zza = googleAnalytics;
    }

    public final void onActivityCreated(Activity activity, Bundle bundle) {
    }

    public final void onActivityDestroyed(Activity activity) {
    }

    public final void onActivityPaused(Activity activity) {
    }

    public final void onActivityResumed(Activity activity) {
    }

    public final void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
    }

    public final void onActivityStarted(Activity activity) {
        this.zza.zza(activity);
    }

    public final void onActivityStopped(Activity activity) {
        this.zza.zzb(activity);
    }
}
