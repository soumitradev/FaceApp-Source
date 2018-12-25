package com.google.android.gms.dynamite;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.dynamic.IObjectWrapper.zza;
import com.google.android.gms.internal.zzev;
import com.google.android.gms.internal.zzex;

public final class zzl extends zzev implements zzk {
    zzl(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.dynamite.IDynamiteLoader");
    }

    public final int zza(IObjectWrapper iObjectWrapper, String str, boolean z) throws RemoteException {
        Parcel a_ = a_();
        zzex.zza(a_, (IInterface) iObjectWrapper);
        a_.writeString(str);
        zzex.zza(a_, z);
        Parcel zza = zza(3, a_);
        int readInt = zza.readInt();
        zza.recycle();
        return readInt;
    }

    public final IObjectWrapper zza(IObjectWrapper iObjectWrapper, String str, int i) throws RemoteException {
        Parcel a_ = a_();
        zzex.zza(a_, (IInterface) iObjectWrapper);
        a_.writeString(str);
        a_.writeInt(i);
        Parcel zza = zza(2, a_);
        IObjectWrapper zza2 = zza.zza(zza.readStrongBinder());
        zza.recycle();
        return zza2;
    }
}
