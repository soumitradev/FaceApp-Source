package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.zzde;
import com.google.android.gms.common.api.internal.zzdf;
import com.google.android.gms.tasks.TaskCompletionSource;

abstract class zzayf extends zzde<zzayb, Void> {
    private TaskCompletionSource<Void> zza;

    private zzayf() {
    }

    protected final /* synthetic */ void zza(zzb zzb, TaskCompletionSource taskCompletionSource) throws RemoteException {
        zzayb zzayb = (zzayb) zzb;
        this.zza = taskCompletionSource;
        zza((zzaxx) zzayb.zzaf());
    }

    protected final void zza(Status status) {
        zzdf.zza(status, null, this.zza);
    }

    protected abstract void zza(zzaxx zzaxx) throws RemoteException;
}
