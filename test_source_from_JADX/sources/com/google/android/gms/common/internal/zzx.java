package com.google.android.gms.common.internal;

import android.content.Intent;
import android.support.v4.app.Fragment;

final class zzx extends zzv {
    private /* synthetic */ Intent zza;
    private /* synthetic */ Fragment zzb;
    private /* synthetic */ int zzc;

    zzx(Intent intent, Fragment fragment, int i) {
        this.zza = intent;
        this.zzb = fragment;
        this.zzc = i;
    }

    public final void zza() {
        if (this.zza != null) {
            this.zzb.startActivityForResult(this.zza, this.zzc);
        }
    }
}
