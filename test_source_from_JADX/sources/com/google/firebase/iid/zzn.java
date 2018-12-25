package com.google.firebase.iid;

import android.os.Handler.Callback;
import android.os.Message;

final /* synthetic */ class zzn implements Callback {
    private final zzm zza;

    zzn(zzm zzm) {
        this.zza = zzm;
    }

    public final boolean handleMessage(Message message) {
        return this.zza.zza(message);
    }
}
