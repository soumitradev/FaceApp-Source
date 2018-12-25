package com.google.android.gms.common.api.internal;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

final class zzbk extends Handler {
    private /* synthetic */ zzbi zza;

    zzbk(zzbi zzbi, Looper looper) {
        this.zza = zzbi;
        super(looper);
    }

    public final void handleMessage(Message message) {
        switch (message.what) {
            case 1:
                ((zzbj) message.obj).zza(this.zza);
                return;
            case 2:
                throw ((RuntimeException) message.obj);
            default:
                int i = message.what;
                StringBuilder stringBuilder = new StringBuilder(31);
                stringBuilder.append("Unknown message id: ");
                stringBuilder.append(i);
                Log.w("GACStateManager", stringBuilder.toString());
                return;
        }
    }
}
