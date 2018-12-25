package com.google.android.gms.cast;

import com.google.android.gms.cast.RemoteMediaPlayer.MediaChannelResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.zzbdp;
import org.json.JSONObject;

final class zzay extends RemoteMediaPlayer$zzb {
    private /* synthetic */ int zzb;
    private /* synthetic */ GoogleApiClient zzd;
    private /* synthetic */ JSONObject zze;
    private /* synthetic */ RemoteMediaPlayer zzf;

    zzay(RemoteMediaPlayer remoteMediaPlayer, GoogleApiClient googleApiClient, int i, GoogleApiClient googleApiClient2, JSONObject jSONObject) {
        this.zzf = remoteMediaPlayer;
        this.zzb = i;
        this.zzd = googleApiClient2;
        this.zze = jSONObject;
        super(googleApiClient);
    }

    protected final void zza(zzbdp zzbdp) {
        synchronized (RemoteMediaPlayer.zze(this.zzf)) {
            if (RemoteMediaPlayer.zza(this.zzf, this.zzb) == -1) {
                zza((MediaChannelResult) zza(new Status(0)));
                return;
            }
            RemoteMediaPlayer$zza zzf;
            RemoteMediaPlayer.zzf(this.zzf).zza(this.zzd);
            try {
                RemoteMediaPlayer.zzg(this.zzf).zza(this.zza, new int[]{this.zzb}, this.zze);
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
