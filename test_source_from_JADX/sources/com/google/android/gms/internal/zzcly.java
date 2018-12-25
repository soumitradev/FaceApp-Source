package com.google.android.gms.internal;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application.ActivityLifecycleCallbacks;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.MainThread;
import android.text.TextUtils;
import com.facebook.share.internal.ShareConstants;

@TargetApi(14)
@MainThread
final class zzcly implements ActivityLifecycleCallbacks {
    private /* synthetic */ zzclk zza;

    private zzcly(zzclk zzclk) {
        this.zza = zzclk;
    }

    public final void onActivityCreated(Activity activity, Bundle bundle) {
        try {
            this.zza.zzt().zzae().zza("onActivityCreated");
            Intent intent = activity.getIntent();
            if (intent != null) {
                Uri data = intent.getData();
                if (data != null && data.isHierarchical()) {
                    if (bundle == null) {
                        Bundle zza = this.zza.zzp().zza(data);
                        this.zza.zzp();
                        String str = zzcno.zza(intent) ? "gs" : "auto";
                        if (zza != null) {
                            this.zza.zza(str, "_cmp", zza);
                        }
                    }
                    Object queryParameter = data.getQueryParameter("referrer");
                    if (!TextUtils.isEmpty(queryParameter)) {
                        Object obj = (queryParameter.contains("gclid") && (queryParameter.contains("utm_campaign") || queryParameter.contains("utm_source") || queryParameter.contains("utm_medium") || queryParameter.contains("utm_term") || queryParameter.contains("utm_content"))) ? 1 : null;
                        if (obj == null) {
                            this.zza.zzt().zzad().zza("Activity created with data 'referrer' param without gclid and at least one utm field");
                            return;
                        }
                        this.zza.zzt().zzad().zza("Activity created with referrer", queryParameter);
                        if (!TextUtils.isEmpty(queryParameter)) {
                            this.zza.zza("auto", "_ldl", queryParameter);
                        }
                    } else {
                        return;
                    }
                }
            }
        } catch (Throwable th) {
            this.zza.zzt().zzy().zza("Throwable caught in onActivityCreated", th);
        }
        zzcma zzj = this.zza.zzj();
        if (bundle != null) {
            bundle = bundle.getBundle("com.google.firebase.analytics.screen_service");
            if (bundle != null) {
                zzcmd zza2 = zzj.zza(activity);
                zza2.zzc = bundle.getLong(ShareConstants.WEB_DIALOG_PARAM_ID);
                zza2.zza = bundle.getString("name");
                zza2.zzb = bundle.getString("referrer_name");
            }
        }
    }

    public final void onActivityDestroyed(Activity activity) {
        this.zza.zzj().zzd(activity);
    }

    @MainThread
    public final void onActivityPaused(Activity activity) {
        this.zza.zzj().zzc(activity);
        zzclh zzr = this.zza.zzr();
        zzr.zzs().zza(new zzcnh(zzr, zzr.zzk().zzb()));
    }

    @MainThread
    public final void onActivityResumed(Activity activity) {
        this.zza.zzj().zzb(activity);
        zzclh zzr = this.zza.zzr();
        zzr.zzs().zza(new zzcng(zzr, zzr.zzk().zzb()));
    }

    public final void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
        this.zza.zzj().zza(activity, bundle);
    }

    public final void onActivityStarted(Activity activity) {
    }

    public final void onActivityStopped(Activity activity) {
    }
}
