package com.google.android.gms.internal;

import com.google.android.gms.cast.games.GameManagerState;
import com.google.android.gms.cast.games.PlayerInfo;
import com.google.android.gms.common.util.zzq;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;

public final class zzbda implements GameManagerState {
    private final int zza;
    private final int zzb;
    private final String zzc;
    private final JSONObject zzd;
    private final String zze;
    private final int zzf;
    private final Map<String, PlayerInfo> zzg;

    public zzbda(int i, int i2, String str, JSONObject jSONObject, Collection<PlayerInfo> collection, String str2, int i3) {
        this.zza = i;
        this.zzb = i2;
        this.zzc = str;
        this.zzd = jSONObject;
        this.zze = str2;
        this.zzf = i3;
        this.zzg = new HashMap(collection.size());
        for (PlayerInfo playerInfo : collection) {
            this.zzg.put(playerInfo.getPlayerId(), playerInfo);
        }
    }

    public final boolean equals(Object obj) {
        if (obj == null || !(obj instanceof GameManagerState)) {
            return false;
        }
        GameManagerState gameManagerState = (GameManagerState) obj;
        if (getPlayers().size() != gameManagerState.getPlayers().size()) {
            return false;
        }
        for (PlayerInfo playerInfo : getPlayers()) {
            Object obj2 = null;
            for (PlayerInfo playerInfo2 : gameManagerState.getPlayers()) {
                if (zzbdw.zza(playerInfo.getPlayerId(), playerInfo2.getPlayerId())) {
                    if (!zzbdw.zza(playerInfo, playerInfo2)) {
                        return false;
                    }
                    obj2 = 1;
                }
            }
            if (obj2 == null) {
                return false;
            }
        }
        return this.zza == gameManagerState.getLobbyState() && this.zzb == gameManagerState.getGameplayState() && this.zzf == gameManagerState.getMaxPlayers() && zzbdw.zza(this.zze, gameManagerState.getApplicationName()) && zzbdw.zza(this.zzc, gameManagerState.getGameStatusText()) && zzq.zza(this.zzd, gameManagerState.getGameData());
    }

    public final CharSequence getApplicationName() {
        return this.zze;
    }

    public final List<PlayerInfo> getConnectedControllablePlayers() {
        List arrayList = new ArrayList();
        for (PlayerInfo playerInfo : getPlayers()) {
            if (playerInfo.isConnected() && playerInfo.isControllable()) {
                arrayList.add(playerInfo);
            }
        }
        return arrayList;
    }

    public final List<PlayerInfo> getConnectedPlayers() {
        List arrayList = new ArrayList();
        for (PlayerInfo playerInfo : getPlayers()) {
            if (playerInfo.isConnected()) {
                arrayList.add(playerInfo);
            }
        }
        return arrayList;
    }

    public final List<PlayerInfo> getControllablePlayers() {
        List arrayList = new ArrayList();
        for (PlayerInfo playerInfo : getPlayers()) {
            if (playerInfo.isControllable()) {
                arrayList.add(playerInfo);
            }
        }
        return arrayList;
    }

    public final JSONObject getGameData() {
        return this.zzd;
    }

    public final CharSequence getGameStatusText() {
        return this.zzc;
    }

    public final int getGameplayState() {
        return this.zzb;
    }

    public final Collection<String> getListOfChangedPlayers(GameManagerState gameManagerState) {
        Collection hashSet = new HashSet();
        for (PlayerInfo playerInfo : getPlayers()) {
            PlayerInfo player = gameManagerState.getPlayer(playerInfo.getPlayerId());
            if (player == null || !playerInfo.equals(player)) {
                hashSet.add(playerInfo.getPlayerId());
            }
        }
        for (PlayerInfo playerInfo2 : gameManagerState.getPlayers()) {
            if (getPlayer(playerInfo2.getPlayerId()) == null) {
                hashSet.add(playerInfo2.getPlayerId());
            }
        }
        return hashSet;
    }

    public final int getLobbyState() {
        return this.zza;
    }

    public final int getMaxPlayers() {
        return this.zzf;
    }

    public final PlayerInfo getPlayer(String str) {
        return str == null ? null : (PlayerInfo) this.zzg.get(str);
    }

    public final Collection<PlayerInfo> getPlayers() {
        return Collections.unmodifiableCollection(this.zzg.values());
    }

    public final List<PlayerInfo> getPlayersInState(int i) {
        List arrayList = new ArrayList();
        for (PlayerInfo playerInfo : getPlayers()) {
            if (playerInfo.getPlayerState() == i) {
                arrayList.add(playerInfo);
            }
        }
        return arrayList;
    }

    public final boolean hasGameDataChanged(GameManagerState gameManagerState) {
        return !zzq.zza(this.zzd, gameManagerState.getGameData());
    }

    public final boolean hasGameStatusTextChanged(GameManagerState gameManagerState) {
        return !zzbdw.zza(this.zzc, gameManagerState.getGameStatusText());
    }

    public final boolean hasGameplayStateChanged(GameManagerState gameManagerState) {
        return this.zzb != gameManagerState.getGameplayState();
    }

    public final boolean hasLobbyStateChanged(GameManagerState gameManagerState) {
        return this.zza != gameManagerState.getLobbyState();
    }

    public final boolean hasPlayerChanged(String str, GameManagerState gameManagerState) {
        return !zzbdw.zza(getPlayer(str), gameManagerState.getPlayer(str));
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean hasPlayerDataChanged(java.lang.String r3, com.google.android.gms.cast.games.GameManagerState r4) {
        /*
        r2 = this;
        r0 = r2.getPlayer(r3);
        r3 = r4.getPlayer(r3);
        r4 = 0;
        if (r0 != 0) goto L_0x000e;
    L_0x000b:
        if (r3 != 0) goto L_0x000e;
    L_0x000d:
        return r4;
    L_0x000e:
        r1 = 1;
        if (r0 == 0) goto L_0x0023;
    L_0x0011:
        if (r3 == 0) goto L_0x0023;
    L_0x0013:
        r0 = r0.getPlayerData();
        r3 = r3.getPlayerData();
        r3 = com.google.android.gms.common.util.zzq.zza(r0, r3);
        if (r3 != 0) goto L_0x0022;
    L_0x0021:
        return r1;
    L_0x0022:
        return r4;
    L_0x0023:
        return r1;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzbda.hasPlayerDataChanged(java.lang.String, com.google.android.gms.cast.games.GameManagerState):boolean");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean hasPlayerStateChanged(java.lang.String r3, com.google.android.gms.cast.games.GameManagerState r4) {
        /*
        r2 = this;
        r0 = r2.getPlayer(r3);
        r3 = r4.getPlayer(r3);
        r4 = 0;
        if (r0 != 0) goto L_0x000e;
    L_0x000b:
        if (r3 != 0) goto L_0x000e;
    L_0x000d:
        return r4;
    L_0x000e:
        r1 = 1;
        if (r0 == 0) goto L_0x001f;
    L_0x0011:
        if (r3 == 0) goto L_0x001f;
    L_0x0013:
        r0 = r0.getPlayerState();
        r3 = r3.getPlayerState();
        if (r0 == r3) goto L_0x001e;
    L_0x001d:
        return r1;
    L_0x001e:
        return r4;
    L_0x001f:
        return r1;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzbda.hasPlayerStateChanged(java.lang.String, com.google.android.gms.cast.games.GameManagerState):boolean");
    }

    public final int hashCode() {
        return Arrays.hashCode(new Object[]{Integer.valueOf(this.zza), Integer.valueOf(this.zzb), this.zzg, this.zzc, this.zzd, this.zze, Integer.valueOf(this.zzf)});
    }
}
