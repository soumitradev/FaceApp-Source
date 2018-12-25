package com.google.android.gms.cast;

import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import com.google.android.gms.cast.CastRemoteDisplay.CastRemoteDisplayOptions;
import com.google.android.gms.common.api.Api.zza;
import com.google.android.gms.common.api.Api.zze;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.internal.zzr;
import com.google.android.gms.internal.zzbfa;

final class zzo extends zza<zzbfa, CastRemoteDisplayOptions> {
    zzo() {
    }

    public final /* synthetic */ zze zza(Context context, Looper looper, zzr zzr, Object obj, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener) {
        CastRemoteDisplayOptions castRemoteDisplayOptions = (CastRemoteDisplayOptions) obj;
        Bundle bundle = new Bundle();
        bundle.putInt("configuration", castRemoteDisplayOptions.zzc);
        return new zzbfa(context, looper, zzr, castRemoteDisplayOptions.zza, bundle, castRemoteDisplayOptions.zzb, connectionCallbacks, onConnectionFailedListener);
    }
}
