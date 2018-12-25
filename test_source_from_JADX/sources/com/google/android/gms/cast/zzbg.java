package com.google.android.gms.cast;

import com.google.android.gms.cast.RemoteMediaPlayer.MediaChannelResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.zzbdp;
import org.json.JSONObject;

final class zzbg extends RemoteMediaPlayer$zzb {
    private /* synthetic */ GoogleApiClient zzb;
    private /* synthetic */ boolean zzd;
    private /* synthetic */ JSONObject zze;
    private /* synthetic */ RemoteMediaPlayer zzf;

    zzbg(RemoteMediaPlayer remoteMediaPlayer, GoogleApiClient googleApiClient, GoogleApiClient googleApiClient2, boolean z, JSONObject jSONObject) {
        this.zzf = remoteMediaPlayer;
        this.zzb = googleApiClient2;
        this.zzd = z;
        this.zze = jSONObject;
        super(googleApiClient);
    }

    protected final void zza(zzbdp zzbdp) {
        synchronized (RemoteMediaPlayer.zze(this.zzf)) {
            RemoteMediaPlayer$zza zzf;
            RemoteMediaPlayer.zzf(this.zzf).zza(this.zzb);
            try {
                RemoteMediaPlayer.zzg(this.zzf).zza(this.zza, this.zzd, this.zze);
                zzf = RemoteMediaPlayer.zzf(this.zzf);
            } catch (IllegalStateException e) {
                try {
                    zza((MediaChannelResult) zza(new Status(2100)));
                    zzf = RemoteMediaPlayer.zzf(this.zzf);
                } catch (Throwable th) {
                    RemoteMediaPlayer.zzf(this.zzf).zza(null);
                }
            }
            zzf.zza(null);
        }
    }
}
