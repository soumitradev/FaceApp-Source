package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.cast.ApplicationMetadata;

public abstract class zzbee extends zzew implements zzbed {
    public zzbee() {
        attachInterface(this, "com.google.android.gms.cast.internal.ICastDeviceControllerListener");
    }

    public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
        if (zza(i, parcel, parcel2, i2)) {
            return true;
        }
        switch (i) {
            case 1:
                zza(parcel.readInt());
                return true;
            case 2:
                zza((ApplicationMetadata) zzex.zza(parcel, ApplicationMetadata.CREATOR), parcel.readString(), parcel.readString(), zzex.zza(parcel));
                return true;
            case 3:
                zzb(parcel.readInt());
                return true;
            case 4:
                zza(parcel.readString(), parcel.readDouble(), zzex.zza(parcel));
                return true;
            case 5:
                zza(parcel.readString(), parcel.readString());
                return true;
            case 6:
                zza(parcel.readString(), parcel.createByteArray());
                return true;
            case 7:
                zzd(parcel.readInt());
                return true;
            case 8:
                zzc(parcel.readInt());
                return true;
            case 9:
                zze(parcel.readInt());
                return true;
            case 10:
                zza(parcel.readString(), parcel.readLong(), parcel.readInt());
                return true;
            case 11:
                zza(parcel.readString(), parcel.readLong());
                return true;
            case 12:
                zza((zzbdd) zzex.zza(parcel, zzbdd.CREATOR));
                return true;
            case 13:
                zza((zzbdx) zzex.zza(parcel, zzbdx.CREATOR));
                return true;
            default:
                return false;
        }
    }
}
