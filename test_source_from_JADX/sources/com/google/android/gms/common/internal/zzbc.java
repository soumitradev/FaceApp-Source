package com.google.android.gms.common.internal;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.common.zzn;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.internal.zzev;
import com.google.android.gms.internal.zzex;

public final class zzbc extends zzev implements zzba {
    zzbc(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.common.internal.IGoogleCertificatesApi");
    }

    public final boolean zza(zzn zzn, IObjectWrapper iObjectWrapper) throws RemoteException {
        Parcel a_ = a_();
        zzex.zza(a_, (Parcelable) zzn);
        zzex.zza(a_, (IInterface) iObjectWrapper);
        Parcel zza = zza(5, a_);
        boolean zza2 = zzex.zza(zza);
        zza.recycle();
        return zza2;
    }
}
