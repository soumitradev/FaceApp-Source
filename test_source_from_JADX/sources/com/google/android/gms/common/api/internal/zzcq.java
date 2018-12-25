package com.google.android.gms.common.api.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.tasks.TaskCompletionSource;

public abstract class zzcq<A extends zzb, L> {
    private final zzci<L> zza;

    protected zzcq(zzci<L> zzci) {
        this.zza = zzci;
    }

    @Hide
    public final zzck<L> zza() {
        return this.zza.zzc();
    }

    @Hide
    protected abstract void zza(A a, TaskCompletionSource<Void> taskCompletionSource) throws RemoteException;

    public final void zzb() {
        this.zza.zzb();
    }
}
