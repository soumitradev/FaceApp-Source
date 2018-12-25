package com.google.android.gms.internal;

import android.content.Context;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzab;
import com.google.android.gms.common.internal.zzr;

@Hide
public final class zzbez extends zzab<zzbfe> {
    public zzbez(Context context, Looper looper, zzr zzr, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener) {
        super(context, looper, 83, zzr, connectionCallbacks, onConnectionFailedListener);
    }

    public final /* synthetic */ IInterface zza(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.cast.remote_display.ICastRemoteDisplayService");
        return queryLocalInterface instanceof zzbfe ? (zzbfe) queryLocalInterface : new zzbff(iBinder);
    }

    protected final String zza() {
        return "com.google.android.gms.cast.remote_display.service.START";
    }

    protected final String zzb() {
        return "com.google.android.gms.cast.remote_display.ICastRemoteDisplayService";
    }
}
