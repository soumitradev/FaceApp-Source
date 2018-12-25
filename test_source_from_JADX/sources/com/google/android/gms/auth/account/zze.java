package com.google.android.gms.auth.account;

import android.accounts.Account;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.internal.zzev;
import com.google.android.gms.internal.zzex;

public final class zze extends zzev implements zzc {
    zze(IBinder iBinder) {
        super(iBinder, "com.google.android.gms.auth.account.IWorkAccountService");
    }

    public final void zza(zza zza, Account account) throws RemoteException {
        Parcel a_ = a_();
        zzex.zza(a_, (IInterface) zza);
        zzex.zza(a_, (Parcelable) account);
        zzb(3, a_);
    }

    public final void zza(zza zza, String str) throws RemoteException {
        Parcel a_ = a_();
        zzex.zza(a_, (IInterface) zza);
        a_.writeString(str);
        zzb(2, a_);
    }

    public final void zza(boolean z) throws RemoteException {
        Parcel a_ = a_();
        zzex.zza(a_, z);
        zzb(1, a_);
    }
}
