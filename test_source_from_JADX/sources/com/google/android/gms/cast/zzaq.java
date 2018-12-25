package com.google.android.gms.cast;

import com.google.android.gms.cast.RemoteMediaPlayer.MediaChannelResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.zzbdp;
import org.json.JSONObject;

final class zzaq extends RemoteMediaPlayer$zzb {
    private /* synthetic */ GoogleApiClient zzb;
    private /* synthetic */ MediaQueueItem zzd;
    private /* synthetic */ int zze;
    private /* synthetic */ long zzf;
    private /* synthetic */ JSONObject zzg;
    private /* synthetic */ RemoteMediaPlayer zzh;

    zzaq(RemoteMediaPlayer remoteMediaPlayer, GoogleApiClient googleApiClient, GoogleApiClient googleApiClient2, MediaQueueItem mediaQueueItem, int i, long j, JSONObject jSONObject) {
        this.zzh = remoteMediaPlayer;
        this.zzb = googleApiClient2;
        this.zzd = mediaQueueItem;
        this.zze = i;
        this.zzf = j;
        this.zzg = jSONObject;
        super(googleApiClient);
    }

    protected final void zza(zzbdp zzbdp) {
        synchronized (RemoteMediaPlayer.zze(this.zzh)) {
            RemoteMediaPlayer$zza zzf;
            RemoteMediaPlayer.zzf(this.zzh).zza(this.zzb);
            try {
                RemoteMediaPlayer.zzg(this.zzh).zza(this.zza, new MediaQueueItem[]{this.zzd}, this.zze, 0, 0, this.zzf, this.zzg);
                zzf = RemoteMediaPlayer.zzf(this.zzh);
            } catch (IllegalStateException e) {
                try {
                    zza((MediaChannelResult) zza(new Status(2100)));
                    zzf = RemoteMediaPlayer.zzf(this.zzh);
                } catch (Throwable th) {
                    RemoteMediaPlayer.zzf(this.zzh).zza(null);
                }
            }
            zzf.zza(null);
        }
    }
}
