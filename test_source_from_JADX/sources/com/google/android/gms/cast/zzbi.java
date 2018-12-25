package com.google.android.gms.cast;

import android.support.annotation.NonNull;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

final class zzbi implements ResultCallback<Status> {
    private final long zza;
    private /* synthetic */ RemoteMediaPlayer$zza zzb;

    zzbi(RemoteMediaPlayer$zza remoteMediaPlayer$zza, long j) {
        this.zzb = remoteMediaPlayer$zza;
        this.zza = j;
    }

    public final /* synthetic */ void onResult(@NonNull Result result) {
        Status status = (Status) result;
        if (!status.isSuccess()) {
            RemoteMediaPlayer.zzg(this.zzb.zza).zza(this.zza, status.getStatusCode());
        }
    }
}
