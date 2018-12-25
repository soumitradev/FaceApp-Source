package com.google.android.gms.common.api.internal;

import android.os.Bundle;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import java.util.concurrent.atomic.AtomicReference;

final class zzbc implements ConnectionCallbacks {
    private /* synthetic */ AtomicReference zza;
    private /* synthetic */ zzdb zzb;
    private /* synthetic */ zzba zzc;

    zzbc(zzba zzba, AtomicReference atomicReference, zzdb zzdb) {
        this.zzc = zzba;
        this.zza = atomicReference;
        this.zzb = zzdb;
    }

    public final void onConnected(Bundle bundle) {
        this.zzc.zza((GoogleApiClient) this.zza.get(), this.zzb, true);
    }

    public final void onConnectionSuspended(int i) {
    }
}
