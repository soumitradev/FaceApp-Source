package com.google.android.gms.common.api.internal;

import android.os.Bundle;
import android.support.annotation.NonNull;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;

final class zzax implements ConnectionCallbacks, OnConnectionFailedListener {
    private /* synthetic */ zzao zza;

    private zzax(zzao zzao) {
        this.zza = zzao;
    }

    public final void onConnected(Bundle bundle) {
        this.zza.zzk.zza(new zzav(this.zza));
    }

    public final void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        this.zza.zzb.lock();
        try {
            if (this.zza.zza(connectionResult)) {
                this.zza.zzg();
                this.zza.zze();
            } else {
                this.zza.zzb(connectionResult);
            }
            this.zza.zzb.unlock();
        } catch (Throwable th) {
            this.zza.zzb.unlock();
        }
    }

    public final void onConnectionSuspended(int i) {
    }
}
