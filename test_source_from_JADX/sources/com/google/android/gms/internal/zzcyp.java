package com.google.android.gms.internal;

import android.os.IInterface;
import android.os.RemoteException;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Status;

public interface zzcyp extends IInterface {
    void zza(ConnectionResult connectionResult, zzcym zzcym) throws RemoteException;

    void zza(Status status) throws RemoteException;

    void zza(Status status, GoogleSignInAccount googleSignInAccount) throws RemoteException;

    void zza(zzcyw zzcyw) throws RemoteException;

    void zzb(Status status) throws RemoteException;
}
