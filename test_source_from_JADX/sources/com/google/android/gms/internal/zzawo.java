package com.google.android.gms.internal;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

public final class zzawo extends zzev implements zzawn {
    zzawo(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.auth.api.accounttransfer.internal.IAccountTransferService");
    }

    public final void zza(zzawl zzawl, zzawj zzawj) throws RemoteException {
        Parcel a_ = a_();
        zzex.zza(a_, (IInterface) zzawl);
        zzex.zza(a_, (Parcelable) zzawj);
        zzb(7, a_);
    }

    public final void zza(zzawl zzawl, zzawp zzawp) throws RemoteException {
        Parcel a_ = a_();
        zzex.zza(a_, (IInterface) zzawl);
        zzex.zza(a_, (Parcelable) zzawp);
        zzb(9, a_);
    }

    public final void zza(zzawl zzawl, zzawr zzawr) throws RemoteException {
        Parcel a_ = a_();
        zzex.zza(a_, (IInterface) zzawl);
        zzex.zza(a_, (Parcelable) zzawr);
        zzb(6, a_);
    }

    public final void zza(zzawl zzawl, zzawt zzawt) throws RemoteException {
        Parcel a_ = a_();
        zzex.zza(a_, (IInterface) zzawl);
        zzex.zza(a_, (Parcelable) zzawt);
        zzb(5, a_);
    }

    public final void zza(zzawl zzawl, zzawv zzawv) throws RemoteException {
        Parcel a_ = a_();
        zzex.zza(a_, (IInterface) zzawl);
        zzex.zza(a_, (Parcelable) zzawv);
        zzb(8, a_);
    }
}
