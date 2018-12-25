package com.google.android.gms.internal;

import com.google.android.gms.cast.games.GameManagerClient;
import com.google.android.gms.cast.games.GameManagerClient.GameManagerInstanceResult;
import com.google.android.gms.common.api.Status;

final class zzbcw implements GameManagerInstanceResult {
    private final Status zza;
    private final GameManagerClient zzb;

    zzbcw(Status status, GameManagerClient gameManagerClient) {
        this.zza = status;
        this.zzb = gameManagerClient;
    }

    public final GameManagerClient getGameManagerClient() {
        return this.zzb;
    }

    public final Status getStatus() {
        return this.zza;
    }
}
