package com.google.android.gms.common.api.internal;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.internal.zzbq;

public final class zzt implements ConnectionCallbacks, OnConnectionFailedListener {
    public final Api<?> zza;
    private final boolean zzb;
    private zzu zzc;

    public zzt(Api<?> api, boolean z) {
        this.zza = api;
        this.zzb = z;
    }

    private final void zza() {
        zzbq.zza(this.zzc, "Callbacks must be attached to a ClientConnectionHelper instance before connecting the client.");
    }

    public final void onConnected(@Nullable Bundle bundle) {
        zza();
        this.zzc.onConnected(bundle);
    }

    public final void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        zza();
        this.zzc.zza(connectionResult, this.zza, this.zzb);
    }

    public final void onConnectionSuspended(int i) {
        zza();
        this.zzc.onConnectionSuspended(i);
    }

    public final void zza(zzu zzu) {
        this.zzc = zzu;
    }
}
