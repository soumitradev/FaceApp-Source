package com.google.android.gms.common.internal;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.dynamic.IObjectWrapper.zza;
import com.google.android.gms.internal.zzev;
import com.google.android.gms.internal.zzex;

public final class zzbe extends zzev implements zzbd {
    zzbe(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.common.internal.ISignInButtonCreator");
    }

    public final IObjectWrapper zza(IObjectWrapper iObjectWrapper, zzbv zzbv) throws RemoteException {
        Parcel a_ = a_();
        zzex.zza(a_, (IInterface) iObjectWrapper);
        zzex.zza(a_, (Parcelable) zzbv);
        Parcel zza = zza(2, a_);
        IObjectWrapper zza2 = zza.zza(zza.readStrongBinder());
        zza.recycle();
        return zza2;
    }
}
