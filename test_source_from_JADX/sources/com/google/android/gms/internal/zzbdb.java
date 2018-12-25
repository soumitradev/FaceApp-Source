package com.google.android.gms.internal;

import com.google.android.gms.cast.games.PlayerInfo;
import com.google.android.gms.common.util.zzq;
import java.util.Arrays;
import org.json.JSONObject;

public final class zzbdb implements PlayerInfo {
    private final String zza;
    private final int zzb;
    private final JSONObject zzc;
    private final boolean zzd;

    public zzbdb(String str, int i, JSONObject jSONObject, boolean z) {
        this.zza = str;
        this.zzb = i;
        this.zzc = jSONObject;
        this.zzd = z;
    }

    public final boolean equals(Object obj) {
        if (obj == null || !(obj instanceof PlayerInfo)) {
            return false;
        }
        PlayerInfo playerInfo = (PlayerInfo) obj;
        if (this.zzd == playerInfo.isControllable() && this.zzb == playerInfo.getPlayerState() && zzbdw.zza(this.zza, playerInfo.getPlayerId()) && zzq.zza(this.zzc, playerInfo.getPlayerData())) {
            return true;
        }
        return false;
    }

    public final JSONObject getPlayerData() {
        return this.zzc;
    }

    public final String getPlayerId() {
        return this.zza;
    }

    public final int getPlayerState() {
        return this.zzb;
    }

    public final int hashCode() {
        return Arrays.hashCode(new Object[]{this.zza, Integer.valueOf(this.zzb), this.zzc, Boolean.valueOf(this.zzd)});
    }

    public final boolean isConnected() {
        switch (this.zzb) {
            case 3:
            case 4:
            case 5:
            case 6:
                return true;
            default:
                return false;
        }
    }

    public final boolean isControllable() {
        return this.zzd;
    }
}
