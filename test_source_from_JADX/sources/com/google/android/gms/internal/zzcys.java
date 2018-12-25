package com.google.android.gms.internal;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.common.internal.zzan;

public final class zzcys extends zzev implements zzcyr {
    zzcys(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.signin.internal.ISignInService");
    }

    public final void zza(int i) throws RemoteException {
        Parcel a_ = a_();
        a_.writeInt(i);
        zzb(7, a_);
    }

    public final void zza(zzan zzan, int i, boolean z) throws RemoteException {
        Parcel a_ = a_();
        zzex.zza(a_, (IInterface) zzan);
        a_.writeInt(i);
        zzex.zza(a_, z);
        zzb(9, a_);
    }

    public final void zza(zzcyu zzcyu, zzcyp zzcyp) throws RemoteException {
        Parcel a_ = a_();
        zzex.zza(a_, (Parcelable) zzcyu);
        zzex.zza(a_, (IInterface) zzcyp);
        zzb(12, a_);
    }
}
