package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.auth.api.accounttransfer.DeviceMetaData;
import com.google.android.gms.auth.api.accounttransfer.zzl;
import com.google.android.gms.auth.api.accounttransfer.zzt;
import com.google.android.gms.common.api.Status;

public abstract class zzawm extends zzew implements zzawl {
    public zzawm() {
        attachInterface(this, "com.google.android.gms.auth.api.accounttransfer.internal.IAccountTransferCallbacks");
    }

    public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
        if (zza(i, parcel, parcel2, i2)) {
            return true;
        }
        switch (i) {
            case 1:
                zzb((Status) zzex.zza(parcel, Status.CREATOR));
                return true;
            case 2:
                zza((Status) zzex.zza(parcel, Status.CREATOR), (zzt) zzex.zza(parcel, zzt.CREATOR));
                return true;
            case 3:
                zza((Status) zzex.zza(parcel, Status.CREATOR), (zzl) zzex.zza(parcel, zzl.CREATOR));
                return true;
            case 4:
                zza();
                return true;
            case 5:
                zza((Status) zzex.zza(parcel, Status.CREATOR));
                return true;
            case 6:
                zza(parcel.createByteArray());
                return true;
            case 7:
                zza((DeviceMetaData) zzex.zza(parcel, DeviceMetaData.CREATOR));
                return true;
            default:
                return false;
        }
    }
}
