package com.google.android.gms.cast;

import com.google.android.gms.cast.RemoteMediaPlayer.MediaChannelResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.zzbdf;
import com.google.android.gms.internal.zzbdp;
import com.google.android.gms.internal.zzben;

abstract class RemoteMediaPlayer$zzb extends zzbdf<MediaChannelResult> {
    zzben zza = new zzbj(this);

    RemoteMediaPlayer$zzb(GoogleApiClient googleApiClient) {
        super(googleApiClient);
    }

    public final /* synthetic */ Result zza(Status status) {
        return new zzbk(this, status);
    }

    protected void zza(zzbdp zzbdp) {
    }
}
