package com.google.android.gms.cast;

import com.google.android.gms.cast.RemoteMediaPlayer.MediaChannelResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.zzbdp;
import org.json.JSONObject;

final class zzau extends RemoteMediaPlayer$zzb {
    private /* synthetic */ GoogleApiClient zzb;
    private /* synthetic */ JSONObject zzd;
    private /* synthetic */ RemoteMediaPlayer zze;

    zzau(RemoteMediaPlayer remoteMediaPlayer, GoogleApiClient googleApiClient, GoogleApiClient googleApiClient2, JSONObject jSONObject) {
        this.zze = remoteMediaPlayer;
        this.zzb = googleApiClient2;
        this.zzd = jSONObject;
        super(googleApiClient);
    }

    protected final void zza(zzbdp zzbdp) {
        synchronized (RemoteMediaPlayer.zze(this.zze)) {
            RemoteMediaPlayer$zza zzf;
            RemoteMediaPlayer.zzf(this.zze).zza(this.zzb);
            try {
                RemoteMediaPlayer.zzg(this.zze).zza(this.zza, 0, -1, null, -1, null, this.zzd);
                zzf = RemoteMediaPlayer.zzf(this.zze);
            } catch (IllegalStateException e) {
                try {
                    zza((MediaChannelResult) zza(new Status(2100)));
                    zzf = RemoteMediaPlayer.zzf(this.zze);
                } catch (Throwable th) {
                    RemoteMediaPlayer.zzf(this.zze).zza(null);
                }
            }
            zzf.zza(null);
        }
    }
}
