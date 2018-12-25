package com.google.android.gms.common.api.internal;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.google.android.gms.common.internal.zzbq;

final class zzcj extends Handler {
    private /* synthetic */ zzci zza;

    public zzcj(zzci zzci, Looper looper) {
        this.zza = zzci;
        super(looper);
    }

    public final void handleMessage(Message message) {
        boolean z = true;
        if (message.what != 1) {
            z = false;
        }
        zzbq.zzb(z);
        this.zza.zzb((zzcl) message.obj);
    }
}
