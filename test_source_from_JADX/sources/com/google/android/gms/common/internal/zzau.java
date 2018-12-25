package com.google.android.gms.common.internal;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.internal.zzew;
import com.google.android.gms.internal.zzex;

public abstract class zzau extends zzew implements zzat {
    public zzau() {
        attachInterface(this, "com.google.android.gms.common.internal.ICertData");
    }

    public static zzat zza(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.common.internal.ICertData");
        return queryLocalInterface instanceof zzat ? (zzat) queryLocalInterface : new zzav(iBinder);
    }

    public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
        if (zza(i, parcel, parcel2, i2)) {
            return true;
        }
        switch (i) {
            case 1:
                IInterface zzb = zzb();
                parcel2.writeNoException();
                zzex.zza(parcel2, zzb);
                return true;
            case 2:
                i = zzc();
                parcel2.writeNoException();
                parcel2.writeInt(i);
                return true;
            default:
                return false;
        }
    }
}
