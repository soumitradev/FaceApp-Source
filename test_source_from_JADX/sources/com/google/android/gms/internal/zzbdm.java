package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.zzcb;
import com.google.android.gms.common.api.internal.zzdf;
import com.google.android.gms.tasks.TaskCompletionSource;

final class zzbdm extends zzcb {
    private /* synthetic */ TaskCompletionSource zza;

    zzbdm(zzbdl zzbdl, TaskCompletionSource taskCompletionSource) {
        this.zza = taskCompletionSource;
    }

    public final void zza(Status status) throws RemoteException {
        zzdf.zza(status, null, this.zza);
    }
}
