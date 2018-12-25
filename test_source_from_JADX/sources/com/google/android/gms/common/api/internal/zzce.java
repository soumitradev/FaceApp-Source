package com.google.android.gms.common.api.internal;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import com.google.android.gms.common.internal.zzbq;

public final class zzce {
    private final Object zza;

    public zzce(Activity activity) {
        zzbq.zza(activity, "Activity must not be null");
        this.zza = activity;
    }

    public final boolean zza() {
        return this.zza instanceof FragmentActivity;
    }

    public final boolean zzb() {
        return this.zza instanceof Activity;
    }

    public final Activity zzc() {
        return (Activity) this.zza;
    }

    public final FragmentActivity zzd() {
        return (FragmentActivity) this.zza;
    }
}
