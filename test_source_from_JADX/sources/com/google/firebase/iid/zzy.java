package com.google.firebase.iid;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

final class zzy extends Handler {
    private /* synthetic */ zzx zza;

    zzy(zzx zzx, Looper looper) {
        this.zza = zzx;
        super(looper);
    }

    public final void handleMessage(Message message) {
        this.zza.zza(message);
    }
}
