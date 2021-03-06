package com.google.android.gms.auth.api.signin.internal;

import android.os.IInterface;
import android.os.RemoteException;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.Status;

public interface zzt extends IInterface {
    void zza(GoogleSignInAccount googleSignInAccount, Status status) throws RemoteException;

    void zza(Status status) throws RemoteException;

    void zzb(Status status) throws RemoteException;
}
