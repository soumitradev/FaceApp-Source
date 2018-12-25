package com.google.android.gms.internal;

import com.google.android.gms.cast.games.GameManagerClient;
import com.google.android.gms.cast.games.GameManagerClient.GameManagerInstanceResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;

public abstract class zzbcu extends zzbct<GameManagerInstanceResult> {
    final /* synthetic */ zzbcl zza;
    private GameManagerClient zzd;

    public zzbcu(zzbcl zzbcl, GameManagerClient gameManagerClient) {
        this.zza = zzbcl;
        super(zzbcl);
        this.zzd = gameManagerClient;
        this.zzb = new zzbcv(this, zzbcl);
    }

    public static GameManagerInstanceResult zzb(Status status) {
        return new zzbcw(status, null);
    }

    public final /* synthetic */ Result zza(Status status) {
        return zzb(status);
    }
}
