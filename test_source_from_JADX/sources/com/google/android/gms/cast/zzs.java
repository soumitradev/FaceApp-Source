package com.google.android.gms.cast;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.internal.zzde;
import com.google.android.gms.internal.zzbez;
import com.google.android.gms.internal.zzbfe;
import com.google.android.gms.tasks.TaskCompletionSource;

final class zzs extends zzde<zzbez, Void> {
    final /* synthetic */ CastRemoteDisplayClient zza;

    zzs(CastRemoteDisplayClient castRemoteDisplayClient) {
        this.zza = castRemoteDisplayClient;
    }

    protected final /* synthetic */ void zza(zzb zzb, TaskCompletionSource taskCompletionSource) throws RemoteException {
        zzbez zzbez = (zzbez) zzb;
        ((zzbfe) zzbez.zzaf()).zza(new zzt(this, taskCompletionSource));
    }
}
