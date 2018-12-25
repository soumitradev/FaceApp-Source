package com.google.android.gms.internal;

import com.facebook.internal.ServerProtocol;
import org.json.JSONException;
import org.json.JSONObject;

public final class zzbcy {
    private final String zza;
    private final int zzb;
    private final String zzc;

    private zzbcy(String str, int i, String str2) {
        this.zza = str;
        this.zzb = i;
        this.zzc = str2;
    }

    public zzbcy(JSONObject jSONObject) throws JSONException {
        this(jSONObject.optString("applicationName"), jSONObject.optInt("maxPlayers"), jSONObject.optString(ServerProtocol.FALLBACK_DIALOG_PARAM_VERSION));
    }

    public final String zza() {
        return this.zza;
    }

    public final int zzb() {
        return this.zzb;
    }

    public final String zzc() {
        return this.zzc;
    }
}
