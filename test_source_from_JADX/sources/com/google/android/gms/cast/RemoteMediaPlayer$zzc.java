package com.google.android.gms.cast;

import com.google.android.gms.cast.RemoteMediaPlayer.MediaChannelResult;
import com.google.android.gms.common.api.Status;
import org.json.JSONObject;

final class RemoteMediaPlayer$zzc implements MediaChannelResult {
    private final Status zza;
    private final JSONObject zzb;

    RemoteMediaPlayer$zzc(Status status, JSONObject jSONObject) {
        this.zza = status;
        this.zzb = jSONObject;
    }

    public final JSONObject getCustomData() {
        return this.zzb;
    }

    public final Status getStatus() {
        return this.zza;
    }
}
