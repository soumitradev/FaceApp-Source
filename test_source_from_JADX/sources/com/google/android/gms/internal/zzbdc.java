package com.google.android.gms.internal;

import com.google.android.gms.common.util.zzq;
import org.json.JSONException;
import org.json.JSONObject;

public final class zzbdc {
    private final String zza;
    private final int zzb;
    private final JSONObject zzc;

    private zzbdc(String str, int i, JSONObject jSONObject) {
        this.zza = str;
        this.zzb = i;
        this.zzc = jSONObject;
    }

    public zzbdc(JSONObject jSONObject) throws JSONException {
        this(jSONObject.optString("playerId"), jSONObject.optInt("playerState"), jSONObject.optJSONObject("playerData"));
    }

    public final boolean equals(Object obj) {
        if (obj == null || !(obj instanceof zzbdc)) {
            return false;
        }
        zzbdc zzbdc = (zzbdc) obj;
        if (this.zzb == zzbdc.zzb && zzbdw.zza(this.zza, zzbdc.zza) && zzq.zza(this.zzc, zzbdc.zzc)) {
            return true;
        }
        return false;
    }

    public final int zza() {
        return this.zzb;
    }

    public final JSONObject zzb() {
        return this.zzc;
    }

    public final String zzc() {
        return this.zza;
    }
}
