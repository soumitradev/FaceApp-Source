package com.google.android.gms.auth.api.signin.internal;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.internal.zzev;
import com.google.android.gms.internal.zzex;

public final class zzw extends zzev implements zzv {
    zzw(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.auth.api.signin.internal.ISignInService");
    }

    public final void zza(zzt zzt, GoogleSignInOptions googleSignInOptions) throws RemoteException {
        Parcel a_ = a_();
        zzex.zza(a_, (IInterface) zzt);
        zzex.zza(a_, (Parcelable) googleSignInOptions);
        zzb(101, a_);
    }

    public final void zzb(zzt zzt, GoogleSignInOptions googleSignInOptions) throws RemoteException {
        Parcel a_ = a_();
        zzex.zza(a_, (IInterface) zzt);
        zzex.zza(a_, (Parcelable) googleSignInOptions);
        zzb(102, a_);
    }

    public final void zzc(zzt zzt, GoogleSignInOptions googleSignInOptions) throws RemoteException {
        Parcel a_ = a_();
        zzex.zza(a_, (IInterface) zzt);
        zzex.zza(a_, (Parcelable) googleSignInOptions);
        zzb(103, a_);
    }
}
