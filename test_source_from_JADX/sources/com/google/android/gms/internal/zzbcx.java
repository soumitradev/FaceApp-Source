package com.google.android.gms.internal;

import com.google.android.gms.cast.games.GameManagerClient.GameManagerResult;
import com.google.android.gms.common.api.Status;
import org.json.JSONObject;

final class zzbcx implements GameManagerResult {
    private final Status zza;
    private final String zzb;
    private final long zzc;
    private final JSONObject zzd;

    zzbcx(Status status, String str, long j, JSONObject jSONObject) {
        this.zza = status;
        this.zzb = str;
        this.zzc = j;
        this.zzd = jSONObject;
    }

    public final JSONObject getExtraMessageData() {
        return this.zzd;
    }

    public final String getPlayerId() {
        return this.zzb;
    }

    public final long getRequestId() {
        return this.zzc;
    }

    public final Status getStatus() {
        return this.zza;
    }
}
