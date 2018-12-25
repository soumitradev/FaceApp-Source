package com.google.android.gms.internal;

import com.google.android.gms.cast.games.GameManagerClient.GameManagerResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;

public abstract class zzbcr extends zzbct<GameManagerResult> {
    final /* synthetic */ zzbcl zza;

    public zzbcr(zzbcl zzbcl) {
        this.zza = zzbcl;
        super(zzbcl);
        this.zzb = new zzbcs(this, zzbcl);
    }

    public static GameManagerResult zzb(Status status) {
        return new zzbcx(status, null, -1, null);
    }

    public final /* synthetic */ Result zza(Status status) {
        return zzb(status);
    }
}
