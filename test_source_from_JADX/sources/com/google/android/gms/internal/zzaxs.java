package com.google.android.gms.internal;

import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.text.TextUtils;
import com.google.android.gms.auth.api.zzd;
import com.google.android.gms.auth.api.zzf;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.internal.zzab;
import com.google.android.gms.common.internal.zzr;

public final class zzaxs extends zzab<zzaxv> {
    private final Bundle zzd;

    public zzaxs(Context context, Looper looper, zzr zzr, zzf zzf, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener) {
        super(context, looper, 16, zzr, connectionCallbacks, onConnectionFailedListener);
        if (zzf == null) {
            this.zzd = new Bundle();
            return;
        }
        throw new NoSuchMethodError();
    }

    public final boolean l_() {
        zzr zzai = zzai();
        return (TextUtils.isEmpty(zzai.zza()) || zzai.zza(zzd.zza).isEmpty()) ? false : true;
    }

    protected final /* synthetic */ IInterface zza(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.auth.api.internal.IAuthService");
        return queryLocalInterface instanceof zzaxv ? (zzaxv) queryLocalInterface : new zzaxw(iBinder);
    }

    protected final String zza() {
        return "com.google.android.gms.auth.service.START";
    }

    protected final String zzb() {
        return "com.google.android.gms.auth.api.internal.IAuthService";
    }

    protected final Bundle zzc() {
        return this.zzd;
    }
}
