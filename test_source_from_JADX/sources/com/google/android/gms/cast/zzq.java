package com.google.android.gms.cast;

import android.app.PendingIntent;
import android.os.Bundle;
import android.os.RemoteException;
import android.view.Display;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.internal.zzde;
import com.google.android.gms.internal.zzbez;
import com.google.android.gms.internal.zzbfe;
import com.google.android.gms.tasks.TaskCompletionSource;

final class zzq extends zzde<zzbez, Display> {
    final /* synthetic */ CastRemoteDisplayClient zza;
    private /* synthetic */ int zzb;
    private /* synthetic */ PendingIntent zzc;
    private /* synthetic */ CastDevice zzd;
    private /* synthetic */ String zze;

    zzq(CastRemoteDisplayClient castRemoteDisplayClient, int i, PendingIntent pendingIntent, CastDevice castDevice, String str) {
        this.zza = castRemoteDisplayClient;
        this.zzb = i;
        this.zzc = pendingIntent;
        this.zzd = castDevice;
        this.zze = str;
    }

    protected final /* synthetic */ void zza(zzb zzb, TaskCompletionSource taskCompletionSource) throws RemoteException {
        zzbez zzbez = (zzbez) zzb;
        Bundle bundle = new Bundle();
        bundle.putInt("configuration", this.zzb);
        ((zzbfe) zzbez.zzaf()).zza(new zzr(this, taskCompletionSource, zzbez), this.zzc, this.zzd.getDeviceId(), this.zze, bundle);
    }
}
