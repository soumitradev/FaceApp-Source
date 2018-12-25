package com.google.android.gms.common.api.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.tasks.TaskCompletionSource;

public abstract class zzdo<A extends zzb, L> {
    private final zzck<L> zza;

    protected zzdo(zzck<L> zzck) {
        this.zza = zzck;
    }

    @Hide
    public final zzck<L> zza() {
        return this.zza;
    }

    @Hide
    protected abstract void zza(A a, TaskCompletionSource<Boolean> taskCompletionSource) throws RemoteException;
}
