package com.google.android.gms.common.api.internal;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.android.gms.common.ConnectionResult;

final class zzx implements zzcd {
    private /* synthetic */ zzv zza;

    private zzx(zzv zzv) {
        this.zza = zzv;
    }

    public final void zza(int i, boolean z) {
        this.zza.zzm.lock();
        try {
            if (!(this.zza.zzl || this.zza.zzk == null)) {
                if (this.zza.zzk.isSuccess()) {
                    this.zza.zzl = true;
                    this.zza.zze.onConnectionSuspended(i);
                    this.zza.zzm.unlock();
                }
            }
            this.zza.zzl = false;
            this.zza.zza(i, z);
            this.zza.zzm.unlock();
        } catch (Throwable th) {
            this.zza.zzm.unlock();
        }
    }

    public final void zza(@Nullable Bundle bundle) {
        this.zza.zzm.lock();
        try {
            this.zza.zza(bundle);
            this.zza.zzj = ConnectionResult.zza;
            this.zza.zzh();
        } finally {
            this.zza.zzm.unlock();
        }
    }

    public final void zza(@NonNull ConnectionResult connectionResult) {
        this.zza.zzm.lock();
        try {
            this.zza.zzj = connectionResult;
            this.zza.zzh();
        } finally {
            this.zza.zzm.unlock();
        }
    }
}
