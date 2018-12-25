package com.google.android.gms.cast;

import android.util.Log;
import com.google.android.gms.cast.RemoteMediaPlayer.MediaChannelResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.zzben;
import org.json.JSONObject;

final class zzbj implements zzben {
    private /* synthetic */ RemoteMediaPlayer$zzb zza;

    zzbj(RemoteMediaPlayer$zzb remoteMediaPlayer$zzb) {
        this.zza = remoteMediaPlayer$zzb;
    }

    public final void zza(long j) {
        try {
            this.zza.zza((MediaChannelResult) this.zza.zza(new Status(2103)));
        } catch (Throwable e) {
            Log.e("RemoteMediaPlayer", "Result already set when calling onRequestReplaced", e);
        }
    }

    public final void zza(long j, int i, Object obj) {
        try {
            this.zza.zza(new RemoteMediaPlayer$zzc(new Status(i), obj instanceof JSONObject ? (JSONObject) obj : null));
        } catch (Throwable e) {
            Log.e("RemoteMediaPlayer", "Result already set when calling onRequestCompleted", e);
        }
    }
}
