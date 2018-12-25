package com.google.android.gms.common.api.internal;

import android.support.annotation.NonNull;
import com.google.android.gms.auth.api.signin.internal.zzaa;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

final class zzbe implements ResultCallback<Status> {
    private /* synthetic */ zzdb zza;
    private /* synthetic */ boolean zzb;
    private /* synthetic */ GoogleApiClient zzc;
    private /* synthetic */ zzba zzd;

    zzbe(zzba zzba, zzdb zzdb, boolean z, GoogleApiClient googleApiClient) {
        this.zzd = zzba;
        this.zza = zzdb;
        this.zzb = z;
        this.zzc = googleApiClient;
    }

    public final /* synthetic */ void onResult(@NonNull Result result) {
        Status status = (Status) result;
        zzaa.zza(this.zzd.zzk).zzc();
        if (status.isSuccess() && this.zzd.isConnected()) {
            this.zzd.reconnect();
        }
        this.zza.zza(status);
        if (this.zzb) {
            this.zzc.disconnect();
        }
    }
}
