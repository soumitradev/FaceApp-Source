package com.google.android.gms.cast;

import com.google.android.gms.cast.MediaLoadOptions.Builder;
import com.google.android.gms.cast.RemoteMediaPlayer.MediaChannelResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.zzbdp;
import org.json.JSONObject;

final class zzaw extends RemoteMediaPlayer$zzb {
    private /* synthetic */ GoogleApiClient zzb;
    private /* synthetic */ boolean zzd;
    private /* synthetic */ long zze;
    private /* synthetic */ long[] zzf;
    private /* synthetic */ JSONObject zzg;
    private /* synthetic */ MediaInfo zzh;
    private /* synthetic */ RemoteMediaPlayer zzi;

    zzaw(RemoteMediaPlayer remoteMediaPlayer, GoogleApiClient googleApiClient, GoogleApiClient googleApiClient2, boolean z, long j, long[] jArr, JSONObject jSONObject, MediaInfo mediaInfo) {
        this.zzi = remoteMediaPlayer;
        this.zzb = googleApiClient2;
        this.zzd = z;
        this.zze = j;
        this.zzf = jArr;
        this.zzg = jSONObject;
        this.zzh = mediaInfo;
        super(googleApiClient);
    }

    protected final void zza(zzbdp zzbdp) {
        synchronized (RemoteMediaPlayer.zze(this.zzi)) {
            RemoteMediaPlayer$zza zzf;
            RemoteMediaPlayer.zzf(this.zzi).zza(this.zzb);
            try {
                RemoteMediaPlayer.zzg(this.zzi).zza(this.zza, this.zzh, new Builder().setAutoplay(this.zzd).setPlayPosition(this.zze).setActiveTrackIds(this.zzf).setCustomData(this.zzg).build());
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
