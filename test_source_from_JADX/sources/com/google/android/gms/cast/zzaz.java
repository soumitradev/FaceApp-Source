package com.google.android.gms.cast;

import com.google.android.gms.cast.RemoteMediaPlayer.MediaChannelResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.zzbdp;
import org.json.JSONObject;

final class zzaz extends RemoteMediaPlayer$zzb {
    private /* synthetic */ int zzb;
    private /* synthetic */ GoogleApiClient zzd;
    private /* synthetic */ long zze;
    private /* synthetic */ JSONObject zzf;
    private /* synthetic */ RemoteMediaPlayer zzg;

    zzaz(RemoteMediaPlayer remoteMediaPlayer, GoogleApiClient googleApiClient, int i, GoogleApiClient googleApiClient2, long j, JSONObject jSONObject) {
        this.zzg = remoteMediaPlayer;
        this.zzb = i;
        this.zzd = googleApiClient2;
        this.zze = j;
        this.zzf = jSONObject;
        super(googleApiClient);
    }

    protected final void zza(zzbdp zzbdp) {
        synchronized (RemoteMediaPlayer.zze(this.zzg)) {
            if (RemoteMediaPlayer.zza(this.zzg, this.zzb) == -1) {
                zza((MediaChannelResult) zza(new Status(0)));
                return;
            }
            RemoteMediaPlayer$zza zzf;
            RemoteMediaPlayer.zzf(this.zzg).zza(this.zzd);
            try {
                RemoteMediaPlayer.zzg(this.zzg).zza(this.zza, this.zzb, this.zze, null, 0, null, this.zzf);
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
