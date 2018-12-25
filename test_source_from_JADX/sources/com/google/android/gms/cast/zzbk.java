package com.google.android.gms.cast;

import com.google.android.gms.cast.RemoteMediaPlayer.MediaChannelResult;
import com.google.android.gms.common.api.Status;
import org.json.JSONObject;

final class zzbk implements MediaChannelResult {
    private /* synthetic */ Status zza;

    zzbk(RemoteMediaPlayer$zzb remoteMediaPlayer$zzb, Status status) {
        this.zza = status;
    }

    public final JSONObject getCustomData() {
        return null;
    }

    public final Status getStatus() {
        return this.zza;
    }
}
