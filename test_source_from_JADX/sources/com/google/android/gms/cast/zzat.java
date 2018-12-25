package com.google.android.gms.cast;

import com.google.android.gms.cast.RemoteMediaPlayer.MediaChannelResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.zzbdp;
import org.json.JSONObject;

final class zzat extends RemoteMediaPlayer$zzb {
    private /* synthetic */ GoogleApiClient zzb;
    private /* synthetic */ int[] zzd;
    private /* synthetic */ int zze;
    private /* synthetic */ JSONObject zzf;
    private /* synthetic */ RemoteMediaPlayer zzg;

    zzat(RemoteMediaPlayer remoteMediaPlayer, GoogleApiClient googleApiClient, GoogleApiClient googleApiClient2, int[] iArr, int i, JSONObject jSONObject) {
        this.zzg = remoteMediaPlayer;
        this.zzb = googleApiClient2;
        this.zzd = iArr;
        this.zze = i;
        this.zzf = jSONObject;
        super(googleApiClient);
    }

    protected final void zza(zzbdp zzbdp) {
        synchronized (RemoteMediaPlayer.zze(this.zzg)) {
            RemoteMediaPlayer$zza zzf;
            RemoteMediaPlayer.zzf(this.zzg).zza(this.zzb);
            try {
                RemoteMediaPlayer.zzg(this.zzg).zza(this.zza, this.zzd, this.zze, this.zzf);
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
