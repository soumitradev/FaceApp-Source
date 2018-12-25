package com.google.android.gms.common.api.internal;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public final class zzbx extends BroadcastReceiver {
    private Context zza;
    private final zzby zzb;

    public zzbx(zzby zzby) {
        this.zzb = zzby;
    }

    public final void onReceive(Context context, Intent intent) {
        Uri data = intent.getData();
        if ("com.google.android.gms".equals(data != null ? data.getSchemeSpecificPart() : null)) {
            this.zzb.zza();
            zza();
        }
    }

    public final synchronized void zza() {
        if (this.zza != null) {
            this.zza.unregisterReceiver(this);
        }
        this.zza = null;
    }

    public final void zza(Context context) {
        this.zza = context;
    }
}
