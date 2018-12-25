package com.google.android.gms.common.internal;

import android.app.Activity;
import android.content.Intent;

final class zzw extends zzv {
    private /* synthetic */ Intent zza;
    private /* synthetic */ Activity zzb;
    private /* synthetic */ int zzc;

    zzw(Intent intent, Activity activity, int i) {
        this.zza = intent;
        this.zzb = activity;
        this.zzc = i;
    }

    public final void zza() {
        if (this.zza != null) {
            this.zzb.startActivityForResult(this.zza, this.zzc);
        }
    }
}
