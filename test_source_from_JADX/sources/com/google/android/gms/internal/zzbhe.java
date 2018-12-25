package com.google.android.gms.internal;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public final class zzbhe extends zzev implements zzbhd {
    zzbhe(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.common.internal.service.ICommonService");
    }

    public final void zza(zzbhb zzbhb) throws RemoteException {
        Parcel a_ = a_();
        zzex.zza(a_, (IInterface) zzbhb);
        zzc(1, a_);
    }
}
