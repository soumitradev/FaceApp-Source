package com.google.android.gms.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import io.fabric.sdk.android.services.common.AdvertisingInfoServiceStrategy.AdvertisingInterface;

public final class zzfr extends zzev implements zzfp {
    zzfr(IBinder iBinder) {
        super(iBinder, AdvertisingInterface.ADVERTISING_ID_SERVICE_INTERFACE_TOKEN);
    }

    public final String zza() throws RemoteException {
        Parcel zza = zza(1, a_());
        String readString = zza.readString();
        zza.recycle();
        return readString;
    }

    public final boolean zza(boolean z) throws RemoteException {
        Parcel a_ = a_();
        zzex.zza(a_, true);
        a_ = zza(2, a_);
        boolean zza = zzex.zza(a_);
        a_.recycle();
        return zza;
    }

    public final boolean zzb() throws RemoteException {
        Parcel zza = zza(6, a_());
        boolean zza2 = zzex.zza(zza);
        zza.recycle();
        return zza2;
    }
}
