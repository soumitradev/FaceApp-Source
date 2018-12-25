package com.google.android.gms.common.internal;

import android.os.Bundle;
import android.support.annotation.BinderThread;
import android.support.annotation.Nullable;
import com.google.android.gms.common.ConnectionResult;

@Hide
public final class zzo extends zze {
    private /* synthetic */ zzd zza;

    @BinderThread
    public zzo(zzd zzd, @Nullable int i, Bundle bundle) {
        this.zza = zzd;
        super(zzd, i, null);
    }

    protected final void zza(ConnectionResult connectionResult) {
        this.zza.zzb.zza(connectionResult);
        this.zza.zza(connectionResult);
    }

    protected final boolean zza() {
        this.zza.zzb.zza(ConnectionResult.zza);
        return true;
    }
}
