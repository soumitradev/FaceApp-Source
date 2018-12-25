package com.google.android.gms.common.api.internal;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.android.gms.common.ConnectionResult;

final class zzy implements zzcd {
    private /* synthetic */ zzv zza;

    private zzy(zzv zzv) {
        this.zza = zzv;
    }

    public final void zza(int i, boolean z) {
        this.zza.zzm.lock();
        try {
            if (this.zza.zzl) {
                this.zza.zzl = false;
                this.zza.zza(i, z);
            } else {
                this.zza.zzl = true;
                this.zza.zzd.onConnectionSuspended(i);
            }
            this.zza.zzm.unlock();
        } catch (Throwable th) {
            this.zza.zzm.unlock();
        }
    }

    public final void zza(@Nullable Bundle bundle) {
        this.zza.zzm.lock();
        try {
            this.zza.zzk = ConnectionResult.zza;
            this.zza.zzh();
        } finally {
            this.zza.zzm.unlock();
        }
    }

    public final void zza(@NonNull ConnectionResult connectionResult) {
        this.zza.zzm.lock();
        try {
            this.zza.zzk = connectionResult;
            this.zza.zzh();
        } finally {
            this.zza.zzm.unlock();
        }
    }
}
