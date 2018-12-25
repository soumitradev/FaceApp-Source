package com.google.android.gms.tagmanager;

import android.os.Handler.Callback;
import android.os.Message;

final class zzft implements Callback {
    private /* synthetic */ zzfs zza;

    zzft(zzfs zzfs) {
        this.zza = zzfs;
    }

    public final boolean handleMessage(Message message) {
        if (1 == message.what && zzfo.zza.equals(message.obj)) {
            this.zza.zza.zza();
            if (!this.zza.zza.zzf()) {
                this.zza.zza((long) this.zza.zza.zze);
            }
        }
        return true;
    }
}
