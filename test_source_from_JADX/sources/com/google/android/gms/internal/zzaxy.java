package com.google.android.gms.internal;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public final class zzaxy extends zzev implements zzaxx {
    zzaxy(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.auth.api.phone.internal.ISmsRetrieverApiService");
    }

    public final void zza(zzaxz zzaxz) throws RemoteException {
        Parcel a_ = a_();
        zzex.zza(a_, (IInterface) zzaxz);
        zzb(1, a_);
    }
}
