package com.google.android.gms.internal;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.clearcut.zze;

public final class zzbfs extends zzev implements zzbfr {
    zzbfs(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.clearcut.internal.IClearcutLoggerService");
    }

    public final void zza(zzbfp zzbfp, zze zze) throws RemoteException {
        Parcel a_ = a_();
        zzex.zza(a_, (IInterface) zzbfp);
        zzex.zza(a_, (Parcelable) zze);
        zzc(1, a_);
    }
}
