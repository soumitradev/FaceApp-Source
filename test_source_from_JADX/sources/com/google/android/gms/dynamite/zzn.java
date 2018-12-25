package com.google.android.gms.dynamite;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.dynamic.IObjectWrapper.zza;
import com.google.android.gms.internal.zzev;
import com.google.android.gms.internal.zzex;

public final class zzn extends zzev implements zzm {
    zzn(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.dynamite.IDynamiteLoaderV2");
    }

    public final IObjectWrapper zza(IObjectWrapper iObjectWrapper, String str, int i, IObjectWrapper iObjectWrapper2) throws RemoteException {
        Parcel a_ = a_();
        zzex.zza(a_, (IInterface) iObjectWrapper);
        a_.writeString(str);
        a_.writeInt(i);
        zzex.zza(a_, (IInterface) iObjectWrapper2);
        Parcel zza = zza(2, a_);
        IObjectWrapper zza2 = zza.zza(zza.readStrongBinder());
        zza.recycle();
        return zza2;
    }
}
