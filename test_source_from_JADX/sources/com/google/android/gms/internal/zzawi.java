package com.google.android.gms.internal;

import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import com.google.android.gms.auth.api.accounttransfer.zzn;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzab;
import com.google.android.gms.common.internal.zzr;

@Hide
public final class zzawi extends zzab<zzawn> {
    private final Bundle zzd;

    public zzawi(Context context, Looper looper, zzr zzr, zzn zzn, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener) {
        super(context, looper, 128, zzr, connectionCallbacks, onConnectionFailedListener);
        if (zzn == null) {
            this.zzd = new Bundle();
            return;
        }
        throw new NoSuchMethodError();
    }

    protected final /* synthetic */ IInterface zza(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.auth.api.accounttransfer.internal.IAccountTransferService");
        return queryLocalInterface instanceof zzawn ? (zzawn) queryLocalInterface : new zzawo(iBinder);
    }

    protected final String zza() {
        return "com.google.android.gms.auth.api.accounttransfer.service.START";
    }

    protected final String zzb() {
        return "com.google.android.gms.auth.api.accounttransfer.internal.IAccountTransferService";
    }

    protected final Bundle zzc() {
        return this.zzd;
    }
}
