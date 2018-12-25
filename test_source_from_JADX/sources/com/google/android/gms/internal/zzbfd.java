package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.RemoteException;
import android.view.Surface;

public abstract class zzbfd extends zzew implements zzbfc {
    public zzbfd() {
        attachInterface(this, "com.google.android.gms.cast.remote_display.ICastRemoteDisplayCallbacks");
    }

    public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
        if (zza(i, parcel, parcel2, i2)) {
            return true;
        }
        switch (i) {
            case 1:
                zza(parcel.readInt(), parcel.readInt(), (Surface) zzex.zza(parcel, Surface.CREATOR));
                break;
            case 2:
                zza(parcel.readInt());
                break;
            case 3:
                zzb();
                break;
            case 4:
                zza();
                break;
            default:
                return false;
        }
        parcel2.writeNoException();
        return true;
    }
}
