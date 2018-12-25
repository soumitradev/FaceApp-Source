package com.google.android.gms.cast;

import com.google.android.gms.cast.RemoteMediaPlayer.MediaChannelResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.zzbdp;
import java.util.Locale;
import org.json.JSONObject;

final class zzba extends RemoteMediaPlayer$zzb {
    private /* synthetic */ int zzb;
    private /* synthetic */ int zzd;
    private /* synthetic */ GoogleApiClient zze;
    private /* synthetic */ JSONObject zzf;
    private /* synthetic */ RemoteMediaPlayer zzg;

    zzba(RemoteMediaPlayer remoteMediaPlayer, GoogleApiClient googleApiClient, int i, int i2, GoogleApiClient googleApiClient2, JSONObject jSONObject) {
        this.zzg = remoteMediaPlayer;
        this.zzb = i;
        this.zzd = i2;
        this.zze = googleApiClient2;
        this.zzf = jSONObject;
        super(googleApiClient);
    }

    protected final void zza(zzbdp zzbdp) {
        synchronized (RemoteMediaPlayer.zze(this.zzg)) {
            int zza = RemoteMediaPlayer.zza(this.zzg, this.zzb);
            if (zza == -1) {
                zza((MediaChannelResult) zza(new Status(0)));
            } else if (this.zzd < 0) {
                zza((MediaChannelResult) zza(new Status(CastStatusCodes.INVALID_REQUEST, String.format(Locale.ROOT, "Invalid request: Invalid newIndex %d.", new Object[]{Integer.valueOf(this.zzd)}))));
            } else if (zza == this.zzd) {
                zza((MediaChannelResult) zza(new Status(0)));
            } else {
                RemoteMediaPlayer$zza zzf;
                MediaQueueItem queueItem = this.zzg.getMediaStatus().getQueueItem(this.zzd > zza ? this.zzd + 1 : this.zzd);
                zza = queueItem != null ? queueItem.getItemId() : 0;
                RemoteMediaPlayer.zzf(this.zzg).zza(this.zze);
                try {
                    RemoteMediaPlayer.zzg(this.zzg).zza(this.zza, new int[]{this.zzb}, zza, this.zzf);
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
}
