package com.google.android.gms.internal;

import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.support.annotation.Nullable;
import com.google.android.gms.auth.api.Auth$AuthCredentialsOptions;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.internal.zzab;
import com.google.android.gms.common.internal.zzr;

public final class zzaxi extends zzab<zzaxn> {
    @Nullable
    private final Auth$AuthCredentialsOptions zzd;

    public zzaxi(Context context, Looper looper, zzr zzr, Auth$AuthCredentialsOptions auth$AuthCredentialsOptions, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener) {
        super(context, looper, 68, zzr, connectionCallbacks, onConnectionFailedListener);
        this.zzd = auth$AuthCredentialsOptions;
    }

    protected final /* synthetic */ IInterface zza(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.auth.api.credentials.internal.ICredentialsService");
        return queryLocalInterface instanceof zzaxn ? (zzaxn) queryLocalInterface : new zzaxo(iBinder);
    }

    protected final String zza() {
        return "com.google.android.gms.auth.api.credentials.service.START";
    }

    protected final String zzb() {
        return "com.google.android.gms.auth.api.credentials.internal.ICredentialsService";
    }

    protected final Bundle zzc() {
        return this.zzd == null ? new Bundle() : this.zzd.zzb();
    }

    final Auth$AuthCredentialsOptions zzd() {
        return this.zzd;
    }
}
