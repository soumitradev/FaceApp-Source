package com.google.android.gms.tagmanager;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.google.android.gms.tagmanager.ContainerHolder.ContainerAvailableListener;

final class zzx extends Handler {
    private final ContainerAvailableListener zza;
    private /* synthetic */ zzv zzb;

    public zzx(zzv zzv, ContainerAvailableListener containerAvailableListener, Looper looper) {
        this.zzb = zzv;
        super(looper);
        this.zza = containerAvailableListener;
    }

    public final void handleMessage(Message message) {
        if (message.what != 1) {
            zzdj.zza("Don't know how to handle this message.");
            return;
        }
        this.zza.onContainerAvailable(this.zzb, (String) message.obj);
    }
}
