package com.google.android.gms.cast;

import com.google.android.gms.cast.RemoteMediaPlayer.MediaChannelResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.zzbdp;
import org.json.JSONObject;

final class zzap extends RemoteMediaPlayer$zzb {
    private /* synthetic */ GoogleApiClient zzb;
    private /* synthetic */ MediaQueueItem[] zzd;
    private /* synthetic */ int zze;
    private /* synthetic */ JSONObject zzf;
    private /* synthetic */ RemoteMediaPlayer zzg;

    zzap(RemoteMediaPlayer remoteMediaPlayer, GoogleApiClient googleApiClient, GoogleApiClient googleApiClient2, MediaQueueItem[] mediaQueueItemArr, int i, JSONObject jSONObject) {
        this.zzg = remoteMediaPlayer;
        this.zzb = googleApiClient2;
        this.zzd = mediaQueueItemArr;
        this.zze = i;
        this.zzf = jSONObject;
        super(googleApiClient);
    }

    protected final void zza(zzbdp zzbdp) {
        synchronized (RemoteMediaPlayer.zze(this.zzg)) {
            RemoteMediaPlayer$zza zzf;
            RemoteMediaPlayer.zzf(this.zzg).zza(this.zzb);
            try {
                RemoteMediaPlayer.zzg(this.zzg).zza(this.zza, this.zzd, this.zze, 0, -1, -1, this.zzf);
                zzf = RemoteMediaPlayer.zzf(this.zzg);
            } catch (IllegalStateException e) {
                try {
                    zza((MediaChannelResult) zza(new Status(2100)));
                    zzf = RemoteMediaPlayer.zzf(this.zzg);
                } catch (Throwable th) {
                    RemoteMediaPlayer.zzf(this.zzg).zza(null);
                }
            }
            zzf.zza(null);
        }
    }
}
