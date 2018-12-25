package com.google.android.gms.cast;

import android.content.Context;
import android.os.Looper;
import com.google.android.gms.common.api.Api.zza;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.internal.zzr;
import com.google.android.gms.internal.zzbdp;

final class zze extends zza<zzbdp, Cast$CastOptions> {
    zze() {
    }

    public final /* synthetic */ com.google.android.gms.common.api.Api.zze zza(Context context, Looper looper, zzr zzr, Object obj, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener) {
        Cast$CastOptions cast$CastOptions = (Cast$CastOptions) obj;
        zzbq.zza(cast$CastOptions, "Setting the API options is required.");
        return new zzbdp(context, looper, zzr, cast$CastOptions.zza, (long) Cast$CastOptions.zza(cast$CastOptions), cast$CastOptions.zzb, cast$CastOptions.zzc, connectionCallbacks, onConnectionFailedListener);
    }
}
