package com.google.android.gms.common.internal;

import android.content.Intent;
import com.google.android.gms.common.api.internal.zzcf;

final class zzy extends zzv {
    private /* synthetic */ Intent zza;
    private /* synthetic */ zzcf zzb;
    private /* synthetic */ int zzc;

    zzy(Intent intent, zzcf zzcf, int i) {
        this.zza = intent;
        this.zzb = zzcf;
        this.zzc = i;
    }

    public final void zza() {
        if (this.zza != null) {
            this.zzb.startActivityForResult(this.zza, this.zzc);
        }
    }
}
