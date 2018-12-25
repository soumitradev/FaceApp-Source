package com.google.android.gms.dynamic;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

final class zzf implements OnClickListener {
    private /* synthetic */ Context zza;
    private /* synthetic */ Intent zzb;

    zzf(Context context, Intent intent) {
        this.zza = context;
        this.zzb = intent;
    }

    public final void onClick(View view) {
        try {
            this.zza.startActivity(this.zzb);
        } catch (Throwable e) {
            Log.e("DeferredLifecycleHelper", "Failed to start resolution intent", e);
        }
    }
}
