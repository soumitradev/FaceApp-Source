package com.google.android.gms.internal;

import android.accounts.Account;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.google.android.gms.auth.AccountChangeEventsRequest;
import com.google.android.gms.auth.AccountChangeEventsResponse;

public final class zzfa extends zzev implements zzey {
    zzfa(IBinder iBinder) {
        super(iBinder, "com.google.android.auth.IAuthManagerService");
    }

    public final Bundle zza(Account account) throws RemoteException {
        Parcel a_ = a_();
        zzex.zza(a_, (Parcelable) account);
        Parcel zza = zza(7, a_);
        Bundle bundle = (Bundle) zzex.zza(zza, Bundle.CREATOR);
        zza.recycle();
        return bundle;
    }

    public final Bundle zza(Account account, String str, Bundle bundle) throws RemoteException {
        Parcel a_ = a_();
        zzex.zza(a_, (Parcelable) account);
        a_.writeString(str);
        zzex.zza(a_, (Parcelable) bundle);
        Parcel zza = zza(5, a_);
        Bundle bundle2 = (Bundle) zzex.zza(zza, Bundle.CREATOR);
        zza.recycle();
        return bundle2;
    }

    public final Bundle zza(String str) throws RemoteException {
        Parcel a_ = a_();
        a_.writeString(str);
        Parcel zza = zza(8, a_);
        Bundle bundle = (Bundle) zzex.zza(zza, Bundle.CREATOR);
        zza.recycle();
        return bundle;
    }

    public final Bundle zza(String str, Bundle bundle) throws RemoteException {
        Parcel a_ = a_();
        a_.writeString(str);
        zzex.zza(a_, (Parcelable) bundle);
        Parcel zza = zza(2, a_);
        bundle = (Bundle) zzex.zza(zza, Bundle.CREATOR);
        zza.recycle();
        return bundle;
    }

    public final AccountChangeEventsResponse zza(AccountChangeEventsRequest accountChangeEventsRequest) throws RemoteException {
        Parcel a_ = a_();
        zzex.zza(a_, (Parcelable) accountChangeEventsRequest);
        Parcel zza = zza(3, a_);
        AccountChangeEventsResponse accountChangeEventsResponse = (AccountChangeEventsResponse) zzex.zza(zza, AccountChangeEventsResponse.CREATOR);
        zza.recycle();
        return accountChangeEventsResponse;
    }
}
