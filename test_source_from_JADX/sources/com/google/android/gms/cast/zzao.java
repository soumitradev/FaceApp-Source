package com.google.android.gms.cast;

import com.google.android.gms.cast.RemoteMediaPlayer.MediaChannelResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.zzbdp;
import org.json.JSONObject;

final class zzao extends RemoteMediaPlayer$zzb {
    private /* synthetic */ GoogleApiClient zzb;
    private /* synthetic */ MediaQueueItem[] zzd;
    private /* synthetic */ int zze;
    private /* synthetic */ int zzf;
    private /* synthetic */ long zzg;
    private /* synthetic */ JSONObject zzh;
    private /* synthetic */ RemoteMediaPlayer zzi;

    zzao(RemoteMediaPlayer remoteMediaPlayer, GoogleApiClient googleApiClient, GoogleApiClient googleApiClient2, MediaQueueItem[] mediaQueueItemArr, int i, int i2, long j, JSONObject jSONObject) {
        this.zzi = remoteMediaPlayer;
        this.zzb = googleApiClient2;
        this.zzd = mediaQueueItemArr;
        this.zze = i;
        this.zzf = i2;
        this.zzg = j;
        this.zzh = jSONObject;
        super(googleApiClient);
    }

    protected final void zza(zzbdp zzbdp) {
        synchronized (RemoteMediaPlayer.zze(this.zzi)) {
            RemoteMediaPlayer$zza zzf;
            RemoteMediaPlayer.zzf(this.zzi).zza(this.zzb);
            try {
                RemoteMediaPlayer.zzg(this.zzi).zza(this.zza, this.zzd, this.zze, this.zzf, this.zzg, this.zzh);
                zzf = RemoteMediaPlayer.zzf(this.zzi);
            } catch (IllegalStateException e) {
                try {
                    zza((MediaChannelResult) zza(new Status(2100)));
                    zzf = RemoteMediaPlayer.zzf(this.zzi);
                } catch (Throwable th) {
                    RemoteMediaPlayer.zzf(this.zzi).zza(null);
                }
            }
            zzf.zza(null);
        }
    }
}
