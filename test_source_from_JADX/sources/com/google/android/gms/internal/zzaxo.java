package com.google.android.gms.internal;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.auth.api.credentials.CredentialRequest;

public final class zzaxo extends zzev implements zzaxn {
    zzaxo(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.auth.api.credentials.internal.ICredentialsService");
    }

    public final void zza(zzaxl zzaxl) throws RemoteException {
        Parcel a_ = a_();
        zzex.zza(a_, (IInterface) zzaxl);
        zzb(4, a_);
    }

    public final void zza(zzaxl zzaxl, CredentialRequest credentialRequest) throws RemoteException {
        Parcel a_ = a_();
        zzex.zza(a_, (IInterface) zzaxl);
        zzex.zza(a_, (Parcelable) credentialRequest);
        zzb(1, a_);
    }

    public final void zza(zzaxl zzaxl, zzaxj zzaxj) throws RemoteException {
        Parcel a_ = a_();
        zzex.zza(a_, (IInterface) zzaxl);
        zzex.zza(a_, (Parcelable) zzaxj);
        zzb(3, a_);
    }

    public final void zza(zzaxl zzaxl, zzaxp zzaxp) throws RemoteException {
        Parcel a_ = a_();
        zzex.zza(a_, (IInterface) zzaxl);
        zzex.zza(a_, (Parcelable) zzaxp);
        zzb(2, a_);
    }
}
