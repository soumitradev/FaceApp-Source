package com.google.android.gms.cast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.internal.zzbem;

class RemoteMediaPlayer$zza implements zzbem {
    final /* synthetic */ RemoteMediaPlayer zza;
    private GoogleApiClient zzb;
    private long zzc = 0;

    public RemoteMediaPlayer$zza(RemoteMediaPlayer remoteMediaPlayer) {
        this.zza = remoteMediaPlayer;
    }

    public final long zza() {
        long j = this.zzc + 1;
        this.zzc = j;
        return j;
    }

    public final void zza(GoogleApiClient googleApiClient) {
        this.zzb = googleApiClient;
    }

    public final void zza(String str, String str2, long j, String str3) {
        if (this.zzb == null) {
            throw new IllegalStateException("No GoogleApiClient available");
        }
        Cast.CastApi.sendMessage(this.zzb, str, str2).setResultCallback(new zzbi(this, j));
    }
}
