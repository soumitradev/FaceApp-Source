package com.google.android.gms.internal;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public final class zzbcz {
    private static final zzbei zzn = new zzbei("GameManagerMessage");
    protected final int zza;
    protected final int zzb;
    protected final String zzc;
    protected final JSONObject zzd;
    protected final int zze;
    protected final int zzf;
    protected final List<zzbdc> zzg;
    protected final JSONObject zzh;
    protected final String zzi;
    protected final String zzj;
    protected final long zzk;
    protected final String zzl;
    protected final zzbcy zzm;

    private zzbcz(int i, int i2, String str, JSONObject jSONObject, int i3, int i4, List<zzbdc> list, JSONObject jSONObject2, String str2, String str3, long j, String str4, zzbcy zzbcy) {
        this.zza = i;
        this.zzb = i2;
        this.zzc = str;
        this.zzd = jSONObject;
        this.zze = i3;
        this.zzf = i4;
        this.zzg = list;
        this.zzh = jSONObject2;
        this.zzi = str2;
        this.zzj = str3;
        this.zzk = j;
        this.zzl = str4;
        this.zzm = zzbcy;
    }

    protected static zzbcz zza(JSONObject jSONObject) {
        JSONObject jSONObject2 = jSONObject;
        int optInt = jSONObject2.optInt("type", -1);
        switch (optInt) {
            case 1:
                JSONObject optJSONObject = jSONObject2.optJSONObject("gameManagerConfig");
                return new zzbcz(optInt, jSONObject2.optInt("statusCode"), jSONObject2.optString("errorDescription"), jSONObject2.optJSONObject("extraMessageData"), jSONObject2.optInt("gameplayState"), jSONObject2.optInt("lobbyState"), zza(jSONObject2.optJSONArray("players")), jSONObject2.optJSONObject("gameData"), jSONObject2.optString("gameStatusText"), jSONObject2.optString("playerId"), jSONObject2.optLong("requestId"), jSONObject2.optString("playerToken"), optJSONObject != null ? new zzbcy(optJSONObject) : null);
            case 2:
                return new zzbcz(optInt, jSONObject2.optInt("statusCode"), jSONObject2.optString("errorDescription"), jSONObject2.optJSONObject("extraMessageData"), jSONObject2.optInt("gameplayState"), jSONObject2.optInt("lobbyState"), zza(jSONObject2.optJSONArray("players")), jSONObject2.optJSONObject("gameData"), jSONObject2.optString("gameStatusText"), jSONObject2.optString("playerId"), -1, null, null);
            default:
                try {
                    zzn.zzc("Unrecognized Game Message type %d", new Object[]{Integer.valueOf(optInt)});
                    return null;
                } catch (Throwable e) {
                    zzn.zzb(e, "Exception while parsing GameManagerMessage from json", new Object[0]);
                    return null;
                }
        }
    }

    private static List<zzbdc> zza(JSONArray jSONArray) {
        List<zzbdc> arrayList = new ArrayList();
        if (jSONArray == null) {
            return arrayList;
        }
        for (int i = 0; i < jSONArray.length(); i++) {
            JSONObject optJSONObject = jSONArray.optJSONObject(i);
            if (optJSONObject != null) {
                Object obj = null;
                try {
                    obj = new zzbdc(optJSONObject);
                } catch (Throwable e) {
                    zzn.zzb(e, "Exception when attempting to parse PlayerInfoMessageComponent at index %d", new Object[]{Integer.valueOf(i)});
                }
                if (obj != null) {
                    arrayList.add(obj);
                }
            }
        }
        return arrayList;
    }
}
