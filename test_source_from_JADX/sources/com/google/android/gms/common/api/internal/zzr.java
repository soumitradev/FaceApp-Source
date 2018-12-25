package com.google.android.gms.common.api.internal;

import android.app.Dialog;

final class zzr extends zzby {
    private /* synthetic */ Dialog zza;
    private /* synthetic */ zzq zzb;

    zzr(zzq zzq, Dialog dialog) {
        this.zzb = zzq;
        this.zza = dialog;
    }

    public final void zza() {
        this.zzb.zza.zzd();
        if (this.zza.isShowing()) {
            this.zza.dismiss();
        }
    }
}
