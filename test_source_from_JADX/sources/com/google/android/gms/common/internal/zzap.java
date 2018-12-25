package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.internal.zzev;
import com.google.android.gms.internal.zzex;

public final class zzap extends zzev implements zzan {
    zzap(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.common.internal.IAccountAccessor");
    }

    public final Account zza() throws RemoteException {
        Parcel zza = zza(2, a_());
        Account account = (Account) zzex.zza(zza, Account.CREATOR);
        zza.recycle();
        return account;
    }
}
