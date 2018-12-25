package com.google.android.gms.cast;

import android.os.RemoteException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.zzdf;
import com.google.android.gms.tasks.TaskCompletionSource;

final class zzt extends CastRemoteDisplayClient$zza {
    private /* synthetic */ TaskCompletionSource zza;
    private /* synthetic */ zzs zzb;

    zzt(zzs zzs, TaskCompletionSource taskCompletionSource) {
        this.zzb = zzs;
        this.zza = taskCompletionSource;
        super();
    }

    public final void zza(int i) throws RemoteException {
        CastRemoteDisplayClient.zza(this.zzb.zza).zza("onError: %d", new Object[]{Integer.valueOf(i)});
        CastRemoteDisplayClient.zzb(this.zzb.zza);
        zzdf.zza(Status.zzc, null, this.zza);
    }

    public final void zzb() throws RemoteException {
        CastRemoteDisplayClient.zza(this.zzb.zza).zza("onDisconnected", new Object[0]);
        CastRemoteDisplayClient.zzb(this.zzb.zza);
        zzdf.zza(Status.zza, null, this.zza);
    }
}
